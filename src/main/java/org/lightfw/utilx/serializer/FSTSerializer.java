package org.lightfw.utilx.serializer;

import de.ruedigermoeller.serialization.FSTObjectInput;
import de.ruedigermoeller.serialization.FSTObjectOutput;
import org.lightfw.util.io.common.StreamUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 使用 FST 实现序列化
 */
public class FSTSerializer implements ISerializer {

    public String name() {
        return "fst";
    }

    public byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = null;
        FSTObjectOutput fout = null;
        try {
            out = new ByteArrayOutputStream();
            fout = new FSTObjectOutput(out);
            fout.writeObject(obj);
            return out.toByteArray();
        } finally {
            StreamUtil.close(fout);
        }
    }


    public Object deserialize(byte[] bytes) throws IOException {
        if (bytes == null || bytes.length == 0) return null;
        FSTObjectInput in = null;
        try {
            in = new FSTObjectInput(new ByteArrayInputStream(bytes));
            return in.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            StreamUtil.close(in);
        }
    }

}
