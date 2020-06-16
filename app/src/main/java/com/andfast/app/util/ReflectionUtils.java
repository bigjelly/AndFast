package com.andfast.app.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 反射工具类
 *
 * @author: boyuma
 * @datetime: 2020/6/16
 */
public class ReflectionUtils {
    private static final String TAG = "ReflectionUtils";

    /**
     * 根据ClassName判断此类是否存在
     *
     * @param className 类名（包含包名）
     * @param classLoader 当前的ClassLoader
     * @return 是否存在
     */
    public static boolean isClassExist(String className, ClassLoader classLoader) {
        boolean result = false;
        try {
            // 不创建实例，不执行static代码块
            Class<?> clazz = Class.forName(className, false, classLoader);
            if (clazz != null) {
                result = true;
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "isClassExist class " + className + " is not exist!!!");
            e.printStackTrace(System.out);
        }
        return result;
    }

    /**
     * 根据Class类型，获取对应的实例（要求必须有无参的构造器）
     *
     * @param className 要创建的实例的类名（包含包名）
     * @return 对应的实例（Object类型）
     */
    public static Object getNewInstance(String className) {
        Object object = null;
        try {
            Class<?> clazz = Class.forName(className);
            object = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return object;
    }

    public static Object getNewInstance(String className, Class<?>[] paramsTypes, Object[] params) {
        try {
            Constructor<?> constructor = getConstructor(className, paramsTypes);
            return getNewInstance(constructor, params);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }


    /**
     * 根据传入的构造方法对象，以及，获取对应的实例
     *
     * @param constructor 构造方法对象
     * @param initargs 传入构造方法的实参【可以不写】
     * @return 对应的实例【Object类型】
     */
    public static Object getNewInstance(Constructor<?> constructor, Object... initargs) {
        try {
            constructor.setAccessible(true);
            return constructor.newInstance(initargs);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    /**
     * 根据Class类型，获取对应的实例（单例无参类型）
     *
     * @param className 要创建的实例的类名（包含包名）
     * @param singleInstaceMethodName 获取单例的方法名
     * @return 对应的实例（Object类型）
     */
    public static Object getSingleInstance(String className, String singleInstaceMethodName) {
        Object object = null;
        try {
            Class<?> clazz = Class.forName(className);
            Method method = clazz.getDeclaredMethod(singleInstaceMethodName, new Class[0]);
            object = method.invoke(null, new Object[]{});
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return object;
    }

    /**
     * 根据传入的类的Class对象，以及构造方法的形参的Class对象，获取对应的构造方法对象
     *
     * @param className 要创建的实例的类名
     * @param parameterTypes 构造方法的形参的Class对象【可以不写】
     * @return 构造方法对象【Constructor类型】
     */
    public static Constructor<?> getConstructor(String className, Class<?>... parameterTypes) {
        try {
            Class<?> clazz = Class.forName(className);
            return clazz.getDeclaredConstructor(parameterTypes);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    /**
     * 根据传入的方法对象、名称、参数（只支持一个参数），调用对应的方法
     *
     * @param classObject 调用方法的对象
     * @param className 调用方法的对象的类名
     * @param methodName 要调用的方法名
     * @param parameter 调用方法的参数对象
     * @param parameterClassName 调用方法参数对象的类名
     * @return 方法执行返回结果（没有返回值，则返回null）
     */
    public static Object invokeMethod(Object classObject, String className, String methodName,
                                      Object parameter, String parameterClassName) {
        Object object = null;
        try {
            Class<?> clazz = Class.forName(className);
            Class<?> parameterClazz = Class.forName(parameterClassName);
            Method method = clazz.getDeclaredMethod(methodName, parameterClazz);
            object = method.invoke(classObject, parameter);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return object;
    }

    /**
     * 根据传入的方法对象、名称、参数（只支持一个参数），调用对应的方法
     *
     * @param classObject 调用方法的对象
     * @param className 调用方法的对象的类名
     * @param methodName 要调用的方法名
     * @param parameter 调用方法的参数对象
     * @param parameterClass 调用方法参数对象的类型
     * @return 方法执行返回结果（没有返回值，则返回null）
     */
    public static Object invokeMethod(Object classObject, String className, String methodName, Object parameter,
                                      Class<?> parameterClass) {
        Object object = null;
        try {
            Class<?> clazz = Class.forName(className);
            Method method = clazz.getDeclaredMethod(methodName, parameterClass);
            object = method.invoke(classObject, parameter);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return object;
    }
}
