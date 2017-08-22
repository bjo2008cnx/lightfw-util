package org.lightfw.util.text;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by lenovo on 2017/8/22.
 */
public class StringExtUtilTest {
    @Test
    public void camelToUnderLineSimple() throws Exception {
        Assert.assertEquals("f_parent_no_leader", StringExtUtil.camelToUnderLineSimple("fParentNoLeader"));
    }

    @Test
    public void camelToUnderLine() throws Exception {
        Assert.assertEquals("f_parent_no_leader", StringExtUtil.camelToUnderLine("fParentNoLeader"));
    }

    @Test
    public void underLineToCamel() throws Exception {
        String lineToHump = StringExtUtil.underLineToCamel("f_parent_no_leader");
        Assert.assertEquals("fParentNoLeader", lineToHump);
    }
}