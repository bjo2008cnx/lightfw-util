package org.lightfw.util.text;

import org.lightfw.util.lang.ExceptionUtil;

import java.util.zip.Deflater;
import java.util.zip.Inflater;


public class TextCompressUtil {

    private static final int bytelength = 1000000;


    /**
     * 解压缩
     *
     * @param input byte[]
     * @return byte[]
     * @
     */
    public static byte[] unzip(byte[] input) {
        Inflater decompresser = new Inflater();
        decompresser.setInput(input);
        byte output[] = new byte[bytelength];
        int resultLength = 0;

        try {
            resultLength = decompresser.inflate(output);
            decompresser.end();
            return cutNullByte(output, resultLength);
        } catch (Exception e) {
            throw ExceptionUtil.transform(e);
        }
    }

    /**
     * 压缩
     *
     * @param input byte[] 压缩内容
     * @return byte[]
     */
    public static byte[] zip(byte[] input) {
        // Compress the bytes
        byte[] output = new byte[bytelength];
        Deflater compresser = new Deflater();
        try {
            compresser.setInput(input);
            compresser.finish();
            int compressedDataLength = compresser.deflate(output);
            return cutNullByte(output, compressedDataLength);
        } catch (Exception e) {
            throw ExceptionUtil.transform(e);
        }
    }

    /**
     * 去掉空的byte
     *
     * @param input      byte[]
     * @param datalength int
     * @return byte[]
     */
    private static byte[] cutNullByte(byte[] input, int datalength) {
        byte[] output = new byte[datalength];
        for (int i = 0; i < datalength; i++) {
            output[i] = input[i];
        }
        return output;
    }

    public static void main(String[] args) {
        byte[] input;
        try {
            input = TextCompressUtil.zip("AFSDFAD#ASDFADFADFASFADFADFASFADFADF".getBytes());
            byte[] result = TextCompressUtil.unzip(input);
            System.out.println(new String(result));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
