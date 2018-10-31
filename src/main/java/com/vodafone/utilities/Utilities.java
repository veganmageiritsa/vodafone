package com.vodafone.utilities;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Map;

/**
 * Created by ktmle on 29/10/2018.
 */
public class Utilities {

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static boolean isEmpty(Object value) {
        if (value == null) {
            return true;
        } else if (value instanceof IsEmpty) {
            return ((IsEmpty) value).isEmpty();
        } else if (value instanceof String) {
            return (((String) value).trim().length() == 0);
        } else if (value instanceof Map) {
            return ((Map) value).isEmpty();
        } else if (value instanceof Collection) {
            return ((Collection) value).isEmpty();
        } else if (value instanceof Map) {
            return ((Map) value).isEmpty();
        } else return value.getClass().isArray() && (Array.getLength(value) == 0);
    }

    public static boolean isNotEmpty(Object value) {
        return !isEmpty(value);
    }
}
