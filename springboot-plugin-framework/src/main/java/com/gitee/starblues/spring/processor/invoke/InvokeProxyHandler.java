package com.gitee.starblues.spring.processor.invoke;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitee.starblues.annotation.Caller;
import com.gitee.starblues.annotation.Supplier;
import org.pf4j.util.StringUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author starBlues
 * @version 1.0
 */
public class InvokeProxyHandler implements InvocationHandler {

    private final Caller callerAnnotation;

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public InvokeProxyHandler(Caller callerAnnotation) {
        this.callerAnnotation = callerAnnotation;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String pluginId = callerAnnotation.pluginId();
        Object supplierObject = InvokeSupperCache.getSupperBean(pluginId, callerAnnotation.value());
        if (supplierObject == null) {
            if (StringUtils.isNullOrEmpty(pluginId)) {
                throw new Exception("Not found '" + callerAnnotation.value() + "' supplier object");
            } else {
                throw new Exception("Not found '" + callerAnnotation.value() + "' supplier object in plugin '" +
                        pluginId + "'");
            }
        }
        Caller.Method callerMethod = method.getAnnotation(Caller.Method.class);
        if (args == null) {
            args = new Object[]{};
        }
        if (callerMethod == null) {
            return notAnnotationInvoke(method, supplierObject, args);
        } else {
            return annotationInvoke(method, callerMethod, supplierObject, args);
        }
    }



    /**
     * 有注解的调用
     * @param method 调用接口的方法
     * @param callerMethod 调用者方法注解
     * @param supplierObject 调用者对象
     * @param args 传入参数
     * @return 返回值
     * @throws Throwable 异常
     */
    private Object annotationInvoke(Method method, Caller.Method callerMethod,
                                    Object supplierObject, Object[] args) throws Throwable{

        String callerMethodName = callerMethod.value();
        Class<?> supplierClass = supplierObject.getClass();
        Method[] methods = supplierClass.getMethods();
        Method supplierMethod = null;
        for (Method m : methods) {
            Supplier.Method supplierMethodAnnotation = m.getAnnotation(Supplier.Method.class);
            if(supplierMethodAnnotation == null){
                continue;
            }
            if(Objects.equals(supplierMethodAnnotation.value(), callerMethodName)){
                supplierMethod = m;
                break;
            }
        }
        if(supplierMethod == null){
            // 如果为空, 说明没有找到被调用者的注解, 则走没有注解的代理调用。
            return notAnnotationInvoke(method, supplierObject, args);
        }
        Class<?>[] parameterTypes = supplierMethod.getParameterTypes();
        if(parameterTypes.length != args.length){
            // 参数不匹配
            return notAnnotationInvoke(method, supplierObject, args);
        }
        Object[] supplierArgs = new Object[args.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            Object arg = args[i];
            if(parameterType == arg.getClass()){
                supplierArgs[i] = arg;
            } else {
                // 类型不匹配, 尝试使用json序列化
                String json = OBJECT_MAPPER.writeValueAsString(arg);
                Object serializeObject = OBJECT_MAPPER.readValue(json, parameterType);
                supplierArgs[i] = serializeObject;
            }
        }
        Object invokeReturn = supplierMethod.invoke(supplierObject, supplierArgs);
        return getReturnObject(invokeReturn, method);
    }

    /**
     * 没有注解调用
     * @param method 调用接口的方法
     * @param supplierObject 提供者对象
     * @param args 传入参数
     * @return 返回值
     * @throws Throwable 异常
     */
    private Object notAnnotationInvoke(Method method, Object supplierObject, Object[] args) throws Throwable{
        String name = method.getName();
        Class<?>[] argClasses = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argClasses[i] = args[i].getClass();
        }
        Class<?> supplierClass = supplierObject.getClass();
        Method supplierMethod = supplierClass.getMethod(name, argClasses);
        Object invokeReturn = supplierMethod.invoke(supplierObject, args);
        return getReturnObject(invokeReturn, method);
    }


    /**
     * 得到返回值对象
     * @param invokeReturn 反射调用后返回的对象
     * @param method 调用接口的方法
     * @return 返回值对象
     * @throws Throwable Throwable
     */
    private Object getReturnObject(Object invokeReturn, Method method) throws Throwable{
        if(invokeReturn == null){
            return null;
        }
        Class<?> returnType = method.getReturnType();
        if(ClassUtils.isAssignable(invokeReturn.getClass(),returnType)){
            return invokeReturn;
        } else {
            String json = OBJECT_MAPPER.writeValueAsString(invokeReturn);
            return OBJECT_MAPPER.readValue(json, OBJECT_MAPPER.getTypeFactory().constructType(method.getGenericReturnType()) );
        }
    }
}