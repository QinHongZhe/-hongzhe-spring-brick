package com.gitee.starblues.utils;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * 类工具类
 * @author starBlues
 * @version 2.4.0
 */
public class ClassUtils {

    private ClassUtils(){

    }

    /**
     * 通过反射获取字段
     * @param o 对象
     * @param fieldName 字段名称
     * @param <T> 字段类型
     * @return 字段值
     * @throws IllegalAccessException 异常信息
     */
    public static <T> T getReflectionField(Object o, String fieldName) throws IllegalAccessException {
        if(o == null){
            return null;
        }
        Field templateResolversField = ReflectionUtils.findField(o.getClass(),
                fieldName);
        return getReflectionField(templateResolversField, o);
    }

    /**
     * 通过反射获取字段
     * @param o 对象
     * @param fieldName 字段名称
     * @param <T> 字段类型
     * @return 字段值
     * @throws IllegalAccessException 异常信息
     */
    public static <T> T getReflectionField(Object o, String fieldName, Class<?> fieldClassType) throws IllegalAccessException {
        if(o == null){
            return null;
        }
        Field templateResolversField = ReflectionUtils.findField(o.getClass(),
                fieldName, fieldClassType);
        return getReflectionField(templateResolversField, o);
    }

    /**
     * 通过反射Field获取字段
     * @param field Field字段
     * @param o 当前对象
     * @param <T> 字段类型
     * @return 字段值
     * @throws IllegalAccessException 异常信息
     */
    public static <T> T getReflectionField(Field field, Object o) throws IllegalAccessException {
        if (field == null) {
            return null;
        }
        if(!field.isAccessible()){
            field.setAccessible(true);
        }
        Object fieldObject = field.get(o);
        return (T) fieldObject;
    }

}
