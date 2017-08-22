package org.lightfw.util.lang;

import org.junit.Assert;
import org.junit.Test;
import org.lightfw.util.validate.PreconditionUtil;

public class PreconditionUtilTest {

    @Test
    public void testCheckNotNullTStringObjectArray() {
        Object reference = null;

        String errorMessageTemplate = "is usnull %s in %s x %s";
        try {
            reference = PreconditionUtil.checkNotNull(reference, errorMessageTemplate, "hello", "test", "sd");
        } catch (Exception e) {
            Assert.assertEquals("is usnull hello in test x sd", e.getMessage());
        }
    }
}
