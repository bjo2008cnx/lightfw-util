package org.lightfw.tool;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by lenovo on 2017/9/28.
 */
public class CsvReaderToolTest {

    @Test
    public void testRead() {
        CsvReaderTool tool = new CsvReaderTool();
        List<BidAidRelation> list = tool.read("E:\\codes\\o2o-integration2\\input\\bid_aid_relation.csv",BidAidRelation.class);
        System.out.println(list.size());

        Assert.assertTrue(list.size() > 0);
    }
}