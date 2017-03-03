package org.lightfw.utilx.dynamic;

import javassist.*;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * 用于打印方法的调用栈（Javassist实现）
 */
public class JsstStackXformer implements ClassFileTransformer {
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
        if (method.isEmpty() == false) {
            String src = String.format(" System.out.println(\"calling:%s:%s()\");", method.getDeclaringClass().getName(), method.getName());
            try {
                method.insertBefore(src);
            } catch (CannotCompileException e) {
                e.printStackTrace();
            }
        }
    }
}
