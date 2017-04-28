package org.lightfw.util.collection;

/**
 * @author Michael.Wang
 * @date 2017/4/28
 */
public class Filters {
    /**
     * <h6>Description:List过滤接口<h6>
     * <p></p>
     *
     * @date 2015-07-23.
     */
    public static interface ListFilter<T> {
        public boolean filter(T t);
    }

    /**
     * <h6>Description:Map过滤接口<h6>
     * <p></p>
     *
     * @date 2015-07-23.
     */
    public static interface MapFilter<T> {
        public boolean filter(T t);
    }

    /**
     * <h6>Description:Set过滤接口<h6>
     * <p></p>
     *
     * @date 2015-07-23.
     */
    public static interface SetFilter<T> {
        public boolean filter(T t);
    }

    /**
     * <h6>Description:Queue过滤接口<h6>
     * <p></p>
     *
     * @date 2015-07-23.
     */
    public static interface QueueFilter<T> {
        public boolean filter(T t);
    }
}
