package org.lightfw.util.ext.structure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 轻量级KV结构
 *
 * @author Michael.Wang
 * @date 2016/12/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KVPair {
    private String key;
    private String value;

    public static KVPair of(String key, String value) {
        return new KVPair(key, value);
    }
}
