package org.lightfw.util.collection;

/**
 * 数据过期监听器，数据过期时将 执行此处的逻辑
 */
public interface ExpirationListener<E> {
    void expired(E expiredObject);
}

