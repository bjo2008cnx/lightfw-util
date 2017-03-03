package org.lightfw.utilx.dynamic;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * 用于打印方法耗时（Javassist实现）
 */
public class JsstPerfXformer implements ClassFileTransformer {

    /**
     * 需要放在原逻辑之前的语句
     */
    public static final String CODE_BEFORE = " long startTime = System.currentTimeMillis();";

    /**
     * 需要放在原逻辑之后的语句的模版（含%s）
     */
    public static final String CODE_AFTER_PATTERN = "  long spendTime = System.currentTimeMillis() - startTime; System.out.println(\"%s.%s():\"+spendTime+\"ms\");";

    /**
     * 最终需要编译的语句， 包含三部分， 原方法之前的部分，原方法逻辑，原方法之后的部分
     */
    public static final String FULL_PATTERN = "{ %s $_= $proceed($$); %s }";

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException {
        if (className.startsWith("java") || className.startsWith("sun")) {
            return null;
        }
        byte[] transformed = null;
        CtClass ctClass = null;
        try {
            ctClass = ClassPool.getDefault().makeClass(new ByteArrayInputStream(classfileBuffer));
            if (!ctClass.isInterface()) {
                for (CtBehavior method : ctClass.getDeclaredBehaviors()) {
                    doMethod(method);
                }
                transformed = ctClass.toBytecode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ctClass != null) {
                ctClass.detach();
            }
        }
        return transformed;
    }

    private void doMethod(CtBehavior method) {
        if (method.isEmpty()) {
            return;
        }
        try {
            method.instrument(new ExprEditor() {
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().startsWith("java")) {
                        return;
                    }

                    String after = String.format(CODE_AFTER_PATTERN, m.getClassName(), m.getMethodName());
                    String toCompile = String.format(FULL_PATTERN, CODE_BEFORE, after);
                    m.replace(toCompile);
                }
            });
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }
}