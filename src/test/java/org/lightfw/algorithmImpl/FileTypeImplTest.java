package org.lightfw.algorithmImpl;

import junit.framework.TestCase;
import org.junit.Test;
import org.lightfw.util.ext.io.FileTypeImpl;
import test.TestUtil;

import java.io.File;

public class FileTypeImplTest extends TestCase {

    @Test
    public void testFileType() {
        TestCase.assertEquals("gif", FileTypeImpl.getFileType(new File(TestUtil.path + "ali.gif")));
        assertEquals("png", FileTypeImpl.getFileType(new File(TestUtil.path + "tgepng")));
    }

}