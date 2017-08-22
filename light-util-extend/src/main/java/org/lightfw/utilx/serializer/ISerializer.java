package org.lightfw.utilx.serializer;

import java.io.IOException;

/**
 * 对象序列化接口
 */
public interface ISerializer {

    public String name();

    public byte[] serialize(Object obj) throws IOException;

    public Object deserialize(byte[] bytes) throws IOException;

}
