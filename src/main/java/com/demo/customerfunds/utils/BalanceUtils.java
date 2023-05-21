package com.demo.customerfunds.utils;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class BalanceUtils {

    public static BigDecimal stringToBigDecimal(String str) {
        return new BigDecimal(str);
    }

    public static String bigDecimalToString(BigDecimal decimal) {
        return decimal.toString();
    }

}
