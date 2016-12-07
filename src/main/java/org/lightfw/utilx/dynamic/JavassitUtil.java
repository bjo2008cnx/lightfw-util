package org.lightfw.utilx.dynamic;

import javassist.*;

/**
 * Javassist 工具类，对Javassit的使用进行了封装
 *
 * @author Michael.Wang
 * @date 2016/12/5
 */
public class JavassitUtil {
    /**
     * 动态创建类
     *
     * @param className
     * @return
     */
    public static CtClass makeClass(String className) {
        return ClassPool.getDefault().makeClass(className);
    }

    /**
     * 动态创建private修饰的field,并生成get set 方法
     *
     * @param type      如：pool.get("java.lang.String")
     * @param fieldName
     * @param clazz
     * @return
     */
    public static CtField newField(CtClass type, String fieldName, CtClass clazz) {
        CtField field = null;
        try {
            field = new CtField(type, fieldName, clazz);
            field.setModifiers(Modifier.PRIVATE);
            clazz.addMethod(CtNewMethod.setter("set" + toUpperFirst(fieldName), field));
            clazz.addMethod(CtNewMethod.getter("get" + toUpperFirst(fieldName), field));
            clazz.addField(field, CtField.Initializer.constant(""));
        } catch (CannotCompileException e) {
            throw new RuntimeException("Cannot compile.", e);
        }
        return field;
    }

    private static String toUpperFirst(String str) {
        if (str == null || "".equals(str)) {
            throw new RuntimeException("String is empty.");
        }
        return str.replace(str.substring(0, 1), str.substring(0, 1).toUpperCase());
    }

    /**
     * 动态创建private修饰的field,并生成get set 方法
     *
     * @param fieldType, field的类型，如："java.lang.String"
     * @param fieldName
     * @param fieldName
     * @return
     */
    public static CtField newField(String fieldType, String fieldName, CtClass clazz) {
        try {
            return newField(ClassPool.getDefault().get(fieldType), fieldName, clazz);
        } catch (NotFoundException e) {
            throw new RuntimeException("Not found field type.", e);
        }
    }

    /**
     * 动态创建private修饰的String field,并生成get set 方法
     *
     * @param fieldName
     * @param fieldName
     * @return
     */
    public static CtField newStringField(String fieldName, CtClass clazz) {
        return newField("java.lang.String", fieldName, clazz);
    }

    /**
     * 动态创建private修饰的Integer field,并生成get set 方法
     *
     * @param fieldName
     * @param fieldName
     * @return
     */
    public static CtField newIntField(String fieldName, CtClass clazz) {
        return newField("java.lang.Integer", fieldName, clazz);
    }

    /**
     * 替换方法体
     * @param className 类名，如：foo.Student
     * @param methodName 方法名
     * @param newMethodBody 新的方法体，如："System.out.println(\"this method is changed dynamically!\");"
     */
    public static void replaceMethodBody(String className, String methodName, String newMethodBody) {
        try {
            CtClass clazz = ClassPool.getDefault().get(className);
            CtMethod method = clazz.getDeclaredMethod(methodName);
            method.setBody(newMethodBody);
            clazz.toClass();
        } catch (NotFoundException | CannotCompileException e) {
            throw new RuntimeException(e);
        }
    }
}
