package com.vodafone.utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by ktmle on 29/10/2018.
 */
public class Utils {

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
