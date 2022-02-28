package au.gov.qld.fire.util;

import org.springframework.beans.BeansException;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public final class BeanUtils {

    private BeanUtils() {
        super();
    }

    public static <T extends Comparable<T>> int compare(T a, T b) {
        if (a == b) {
            return 0;
        }
        if (a == null || b == null) {
            return a == null ? 1 : -1;
        }
        return a.compareTo(b);
    }

    public static <T> T copyProperties(T source, T target, String... ignoreProperties)
        throws BeansException
    {
        if (source != null && target != null) {
            org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
        }
        return target;
    }

}