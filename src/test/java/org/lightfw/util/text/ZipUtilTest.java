package org.lightfw.util.text;

import org.lightfw.util.io.common.FileUtil;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ZipUtilTest {
    public static void main(String[] args) {
        {
            String path = "e:/tmp/toencrypt.txt";
            List<String> lines = FileUtil.readAllLines(new File(path));
            System.out.println(lines.size());
            for (String line : lines) {
                String zipped = ZipUtil.gzip(line);
                System.out.println(zipped);
                String gunzipped = ZipUtil.gunzip(zipped);
                System.out.println(gunzipped);
                System.out.println(line.length() + "::" + zipped.length());
                assertEquals(line, gunzipped);
            }
        }
        {
            String path = "e:/tmp/toencrypt.txt";
            path = "f:/downloads/test.txt";
            List<String> lines = FileUtil.readAllLines(new File(path));
            System.out.println(lines.size());
            for (String line : lines) {
                String zipped = ZipUtil.gzip(line);
                System.out.println(zipped);
                String gunzipped = ZipUtil.gunzip(zipped);
                System.out.println(gunzipped);
                System.out.println(line.length() + "::" + zipped.length());
                assertEquals(line, gunzipped);
            }
        }
    }
}