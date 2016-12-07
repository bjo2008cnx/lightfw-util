package org.lightfw.util.ext.dynamic.proxy;

/**
 * @author Michael.Wang
 * @date 2016/12/7
 */
public class AccountImpl implements Account {
    @Override
    public void operation(){
        System.out.println("operation in class Account.");
    }
}
