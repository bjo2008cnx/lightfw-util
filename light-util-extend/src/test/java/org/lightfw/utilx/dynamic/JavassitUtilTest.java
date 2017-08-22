package org.lightfw.utilx.dynamic;

import org.junit.Test;

public class JavassitUtilTest {

    @Test
    public void testReplaceMethodBody() throws Exception {
        JavassitUtil.replaceMethodBody("org.lightfw.utilx.dynamic.Student", "execute", "System.out.println(\"this method is changed dynamically!\");");
        Student student = new Student();
        student.execute();
    }
}