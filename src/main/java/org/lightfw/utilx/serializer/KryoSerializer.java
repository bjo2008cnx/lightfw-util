package org.lightfw.utilx.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 使用 Kryo 实现序列化
 */
public class KryoSerializer implements ISerializer {

    private final static Kryo kryo = new Kryo();


    public String name() {
        return "kryo";
    }


    public byte[] serialize(Object obj) throws IOException {
        Output output = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            output = new Output(baos);
            kryo.writeClassAndObject(output, obj);
            output.flush();
            return baos.toByteArray();
        } finally {
            if (output != null)
                output.close();
        }
    }


    public Object deserialize(byte[] bits) throws IOException {
        if (bits == null || bits.length == 0)
            return null;
        Input ois = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bits);
            ois = new Input(bais);
            return kryo.readClassAndObject(ois);
        } finally {
            if (ois != null)
                ois.close();
        }
    }

}
