package org.lightfw.util.io.common;

import org.lightfw.util.lang.ExceptionUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 序列化工具
 */
public class SerializableUtil {

    /**
     * 反序列化
     *
     * @param s
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Serializable deserialize(byte[] s) {
        try {
            Serializable serializable = null;
            ObjectInput ois = new ObjectInputStream(new ByteArrayInputStream(s));
            serializable = (Serializable) ois.readObject();
            return serializable;
        } catch (IOException | ClassNotFoundException e) {
            throw ExceptionUtil.transform(e);
        }
    }

    /**
     * 序列化
     *
     * @param input
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static byte[] serialize(Serializable input) {
        ByteArrayOutputStream bos = null;
        ObjectOutputStream out = null;
        try {
            bos = new ByteArrayOutputStream();
            out = new ObjectOutputStream(bos);
            out.writeObject(input);
            out.flush();
        } catch (IOException e) {
            throw ExceptionUtil.transform(e);
        } finally {
            StreamUtil.close(out);
        }

        byte[] bytes = bos.toByteArray();
        return bytes;
    }
}
