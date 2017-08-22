package org.lightfw.common;

import org.lightfw.util.system.SysInfoUtil;

public class SysInfoUtilTest {

    @org.junit.Test
    public void testGet() throws Exception {
        System.out.println(SysInfoUtil.HOST_IP);
        System.out.println(SysInfoUtil.HOST_NAME);
        System.out.println(SysInfoUtil.OS_NAME);
        System.out.println(SysInfoUtil.OS_VERSION);
        System.out.println(SysInfoUtil.CURRENT_USER);
        System.out.println(SysInfoUtil.CURRENT_USER_HOME);
        System.out.println(SysInfoUtil.FILE_SEPARATOR);
        System.out.println(SysInfoUtil.PATH_SEPARATOR);
        System.out.println(SysInfoUtil.LINE_SEPARATOR);
        System.out.println(SysInfoUtil.TotalMemorySize);
        System.out.println(SysInfoUtil.JVM_VERSION);
        System.out.println(SysInfoUtil.JVM_TEMPDIR);
        System.out.println(SysInfoUtil.OS_ARCH);
        System.out.println(SysInfoUtil.SUN_DESKTOP);
        System.out.println(SysInfoUtil.usedMemory());
        System.out.println(SysInfoUtil.JVMtotalMem());
        System.out.println(SysInfoUtil.JVMfreeMem());
        System.out.println(SysInfoUtil.JVMmaxMem());
    }
}