package org.lightfw.utilt;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * HttpServletRequest的简单实现类，可以创建其实例，同时增加了addParameter方法，可以手动添加参数
 *
 * @author Michael.Wang
 */
public class HttpServletRequestMock extends HttpServletRequestDefaultAdapter {
    private Map attributeMap = new HashMap();

    private Map parameterMap = new HashMap();

    /**
     * 自定义的方法，原接口不提供此方法
     *
     * @param name
     */
    public void addParameter(String name, Object o) {
        parameterMap.put(name, o);
    }

    @Override
    public String getParameter(String name) {
        return (String) parameterMap.get(name);
    }

    @Override
    public void setAttribute(String name, Object o) {
        this.attributeMap.put(name, o);
    }

    @Override
    public void removeAttribute(String name) {
        this.attributeMap.remove(name);
    }

    @Override
    public Object getAttribute(String name) {
        return this.attributeMap.get(name);
    }

    @Override
    public String[] getParameterValues(String name) {
        return (String[]) this.parameterMap.get(name);
    }

    @Override
    public Map getParameterMap() {
        return this.parameterMap;
    }


    @Override
    public Enumeration getAttributeNames() {
        return new Enumeration() {
            private Iterator<String> names = attributeMap.keySet().iterator();

            @Override
            public boolean hasMoreElements() {
                return names.hasNext();
            }

            @Override
            public Object nextElement() {
                return names.next(); //TODO 未测试
            }
        };
    }

    @Override
    public Enumeration getParameterNames() {
        return new Enumeration() {
            private Iterator<String> names = parameterMap.keySet().iterator();

            @Override
            public boolean hasMoreElements() {
                return names.hasNext();
            }

            @Override
            public Object nextElement() {
                return names.next(); //TODO 未测试
            }
        };
    }
}


