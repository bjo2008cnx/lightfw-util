package org.lightfw.utilx.web;

import junit.framework.TestCase;
import org.junit.Test;
import org.lightfw.util.text.CharsetUtil;

import java.util.Map;


public class UrlUtilTest extends TestCase {

    @Test
    public void testQueryParam() {
        String http_request = "\n" +
                "org.apache.struts.taglib.html.TOKEN=8e1c374155eaf63ef485547e78638ad9\n" +
                "&cmd=commit\n" +
                "&functionType=201\n" +
                "&operateType=01\n" +
                "&flowID=f1\n" +
                "&parentId=\n" +
                "&mainSheet.serviceType=02\n" +
                "&mainSheet.sendTimeLimit=4\n" +
                "&mainSheet.answerTimeLimit=2\n" +
                "&isHightG=0\n" +
                "&acceptRecord.beginTime=2014-12-29+10%3A34%3A45\n" +
                "&mainSheet.isPrePlan=\n" +
                "&prePlan_code=\n" +
                "&mainSheet.isImmed=0\n" +
                "&remedyProductInfo=\n" +
                "&isOnline=\n" +
                "&oldSheetCategory=1\n" +
                "&rdFlag=null\n" +
                "&mainSheet.rdtemplateid=\n" +
                "&mainSheet.categoryID=1\n" +
                "&mainSheet.callinTeleNO=13099753058\n" +
                "&acceptRecord.customerId=19267\n" +
                "&mainSheet.mobileTeleNO=13099753058\n" +
                "&mainSheet.customerId=\n" +
                "&customerInfo.customerName=%D5%C5%D3%F1%B7%E1\n" +
                "&mainSheet.vestAreaID=%CE%F7%C4%FE\n" +
                "&customerInfo.customerLevel=303\n" +
                "&customerInfo.subType=3000\n" +
                "&customerTypeGuwang=11\n" +
                "&customerInfo.networkTime=154\n" +
                "&customerInfo.mobileStatus=1\n" +
                "&customerInfo.lineType=%C8%CE%CE%D2%D0%D0\n" +
                "&acceptRecord.contactMan=%D5%C5%D3%F1%B7%E1\n" +
                "&acceptRecord.contactInfo=%C7%E0%BA%A3%CA%A1%B8%D5%B2%EC%CF%D8%C8%C8%CB%AE%C3%BA%BF%F3%D4%CB%CF%FA%BF%C6%28%D7%CA%C1%CF%D2%D1%B8%FC%D0%C2%29\n" +
                "&acceptRecord.touchFlowNo=13099753058\n" +
                "&acceptRecord.answerType=801\n" +
                "&acceptRecord.recordType=\n" +
                "&previousProblemTypeID=\n" +
                "&mainSheet.problemTypeID=NH1_01_010101\n" +
                "&mainSheet.sheetFlowNO=TS2014122952380\n" +
                "&mainSheet.endPrmReasons=NH1_01_010101\n" +
                "&mainSheet.productTypeID=8a7b88af2c05225a012c055d8ceb051c\n" +
                "&mainSheet.emergencyType=404\n" +
                "&mainSheet.timeLimit=24\n" +
                "&acceptRecord.accessType=701\n" +
                "&mainSheet.appealLevel=601\n" +
                "&customerInfo.specialIdentity=\n" +
                "&mainSheet.relationServerWarnid=\n" +
                "&mainSheet.spareContentField1=\n" +
                "&mainSheet.spareContentField2=\n" +
                "&mainSheet.returnfeeFlag=\n" +
                "&customerInfo.manufacName=%CE%F7%C4%FE\n" +
                "&get_content=3\n" +
                "&acceptRecord.acceptContent=%D3%C3%BB%A7%D2%AA%C7%F3%B0%B2%D7%B0%CE%D2%B9%AB%CB%BE%BF%ED%B4%F8%A3%A8%B9%CC%BB%B0%A3%A9%A3%AC%C7%EB%B9%F3%B2%BF%BA%CB%CA%B5%D3%D0%CE%DE%CF%DF%C2%B7%D7%CA%D4%B4%B2%A2%D4%DA%CA%B1%CF%DE%C4%DA%BB%D8%B8%B4%A1%A3\n" +
                "&mainSheet.dealContent=1\n" +
                "&textfield23=4004\n" +
                "&textfield23=%CD%B6%CB%DF%B4%A6%C0%ED%D6%D0%D0%C4%D7%E9";
        System.out.println(UrlUtil.decode(http_request, CharsetUtil.GBK));
    }

    @Test
    public void testParseQuery() {
        String query = "id=111&name=test&password=p0ssw0rd";
        Map<String, String> queryMap = UrlUtil.parseQuery(query, '&', '=', null);
        System.out.println(queryMap);
        Map<String, String> httpQueryMap = UrlUtil.parseHttpQuery(query);
        System.out.println(httpQueryMap);

        //数组解析
        String query2 = "id=111&name=test&password[]=p0ssw0rd&password[]=123456";
        Map<String, String> queryMap1 = UrlUtil.parseQuery(query2, '&', '=', ",");
        System.out.println(queryMap1);
        Map<String, String> httpQueryMap1 = UrlUtil.parseHttpQuery(query2);
        System.out.println(httpQueryMap1);

        //"id=111&name=test&password=p0ssw0rd,=123456"
        String query3 = "id=111&name=test&password=p0ssw0rd,123456";
        Map<String, String> queryMap2 = UrlUtil.parseQuery(query3, '&', '=', ",");
        System.out.println(queryMap2);
        Map<String, String> httpQueryMap2 = UrlUtil.parseHttpQuery(query3);
        System.out.println(httpQueryMap2);
    }

}