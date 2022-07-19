/**
 * Copyright [2019-2022] [starBlues]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.gitee.starblues.loader.launcher;

/**
 * 抽象的启动引导者
 * @author starBlues
 * @version 3.0.2
 */
public abstract class AbstractMainLauncher<R> extends AbstractLauncher<R> {

    @Override
    public R run(String... args) throws Exception {
        ClassLoader classLoader = createClassLoader(args);
        Thread thread = Thread.currentThread();
        ClassLoader oldClassLoader  = thread.getContextClassLoader();
        try {
            thread.setContextClassLoader(classLoader);
            LauncherContext.setMainClassLoader(classLoader);
            return launch(classLoader, args);
        } finally {
            thread.setContextClassLoader(oldClassLoader);
        }
    }

}