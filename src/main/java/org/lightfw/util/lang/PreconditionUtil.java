package org.lightfw.util.lang;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
public class PreconditionUtil {

    private PreconditionUtil() {
    }

    /**
     * 校验参数,如果为null，则抛出异常
     * 如果是字符串，不能为""0
     * 如果是long或int,不能为
     *
     * @param objects
     */
    public static void checkArgumentEmpty(Object... objects) {
        for (int i = 0; i < objects.length; i++) {
            Object obj = objects[i];
            if (obj == null) {
                throw new IllegalArgumentException("Argument is null.");
            } else if (obj instanceof String) {
                if (StringUtil.isEmpty((String) obj)) {
                    throw new IllegalArgumentException("Argument is empty.");
                }
            } else if (obj instanceof Long) {
                if (((Long) obj).longValue() == 0l) {
                    throw new IllegalArgumentException("Argument value is 0.");
                }
            } else if (obj instanceof Integer) {
                if (((Integer) obj).intValue() == 0) {
                    throw new IllegalArgumentException("Argument value is 0.");
                }
            }
        }
    }

    /**
     * 校验数组中的对象是否为空
     *
     * @param objects
     */
    public static void checkNotNull(Object... objects) {
        if (objects == null) {
            throw new IllegalArgumentException("Argument is null.");
        }
        for (Object object : objects) {
            if (object == null) {
                throw new IllegalArgumentException("Argument is null.");
            }
        }
    }

    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkArgument(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    public static void checkArgument(boolean expression, String errorMessageTemplate, Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalArgumentException(format(errorMessageTemplate, errorMessageArgs));
        }
    }

    public static void checkState(boolean expression) {
        if (!expression) {
            throw new IllegalStateException();
        }
    }

    public static void checkState(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new IllegalStateException(String.valueOf(errorMessage));
        }
    }

    public static void checkState(boolean expression, String errorMessageTemplate, Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalStateException(format(errorMessageTemplate, errorMessageArgs));
        }
    }

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    public static <T> T checkNotNull(T reference, Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    public static <T> T checkNotNull(T reference, String errorMessageTemplate, Object... errorMessageArgs) {
        if (reference == null) {
            // If either of these parameters is null, the right thing happens
            // anyway
            throw new NullPointerException(format(errorMessageTemplate, errorMessageArgs));
        }
        return reference;
    }

    public static int checkElementIndex(int index, int size) {
        return checkElementIndex(index, size, "index");
    }

    public static int checkElementIndex(int index, int size, String desc) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(badElementIndex(index, size, desc));
        }
        return index;
    }

    private static String badElementIndex(int index, int size, String desc) {
        if (index < 0) {
            return format("%s (%s) must not be negative", desc, index);
        } else if (size < 0) {
            throw new IllegalArgumentException("negative size: " + size);
        } else { // index >= size
            return format("%s (%s) must be less than size (%s)", desc, index, size);
        }
    }

    public static int checkPositionIndex(int index, int size) {
        return checkPositionIndex(index, size, "index");
    }

    public static int checkPositionIndex(int index, int size, String desc) {
        // Carefully optimized for execution by hotspot (explanatory comment
        // above)
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(badPositionIndex(index, size, desc));
        }
        return index;
    }

    private static String badPositionIndex(int index, int size, String desc) {
        if (index < 0) {
            return format("%s (%s) must not be negative", desc, index);
        } else if (size < 0) {
            throw new IllegalArgumentException("negative size: " + size);
        } else { // index > size
            return format("%s (%s) must not be greater than size (%s)", desc, index, size);
        }
    }

    public static void checkPositionIndexes(int start, int end, int size) {
        // Carefully optimized for execution by hotspot (explanatory comment
        // above)
        if (start < 0 || end < start || end > size) {
            throw new IndexOutOfBoundsException(badPositionIndexes(start, end, size));
        }
    }

    private static String badPositionIndexes(int start, int end, int size) {
        if (start < 0 || start > size) {
            return badPositionIndex(start, size, "start index");
        }
        if (end < 0 || end > size) {
            return badPositionIndex(end, size, "end index");
        }
        // end < start
        return format("end index (%s) must not be less than start index (%s)", end, start);
    }

    private static String format(String template, Object... args) {
        return StringUtil.format(template, args);
    }


}
