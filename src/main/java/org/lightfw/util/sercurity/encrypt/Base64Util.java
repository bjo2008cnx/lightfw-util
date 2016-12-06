package org.lightfw.util.sercurity.encrypt;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util {

    public Base64Util() {
    }

    /**
     * Base64编码
     *
     * @param input byte[]
     * @return byte[]
     * @throws RuntimeException
     * @todo Implement this com.dc.tirip.common.basicservice.ICode method
     */
    public byte[] encodeData(byte[] input) {
        BASE64Encoder base64encoder = new BASE64Encoder();
        try {
            String strEncoder = base64encoder.encode(input);
            return strEncoder.getBytes();
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }

    /**
     * Base64解码
     *
     * @param input byte[]
     * @return byte[]
     * @throws RuntimeException
     * @todo Implement this com.dc.tirip.common.basicservice.ICode method
     */
    public byte[] decodeData(byte[] input) {
        BASE64Decoder base64decoder = new BASE64Decoder();
        try {
            return base64decoder.decodeBuffer(new String(input));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Base64Util Base64Util = new Base64Util();
        String input = "decode_encode";
        byte[] output;
        try {
            //编码
            output = Base64Util.encodeData(input.getBytes());
            System.out.println(new String(output));

            //解码
            output = Base64Util.decodeData(output);
            System.out.println(new String(output));
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }

    }
}
