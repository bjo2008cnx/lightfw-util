package org.lightfw.util.text;

import org.junit.Test;

import static org.junit.Assert.*;

public class CN2SpellUtilTest {

    @Test
    public void testAll() {
        String str = "我是中国人我自豪";
        assertEquals("woshizhongguorenwozihao", CN2SpellUtil.getFullSpell(str));
        assertEquals("w", CN2SpellUtil.getFirstSpell(str));
        assertEquals("wszgrwzh", CN2SpellUtil.getFirstSpells(str));
    }
}