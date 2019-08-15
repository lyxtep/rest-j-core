package com.lx.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 *
 * @author lx.ds
 */
public class NumberUtils {
    
    public static BigDecimal parseDecimal(String str) {
        return parseDecimal(str, Locale.getDefault());
    }
    public static BigDecimal parseDecimal(String str, Locale locale) {
        BigDecimal value = null;
        
        if (str == null || str.length() == 0) {
            return null;
        }
        
        DecimalFormat formatter = (DecimalFormat) DecimalFormat.getInstance(locale);
        formatter.setParseBigDecimal(true);
        try {
            value = (BigDecimal) formatter.parse(str);
        } catch (ParseException ex) {
            
        }
        
        return value;
    }
    
    public static BigDecimal parseAngle(String str, Locale locale) {
        BigDecimal value;
        BigDecimal bgDegree = new BigDecimal(0);
        BigDecimal bgMinutes = new BigDecimal(0);
        BigDecimal bgSeconds = new BigDecimal(0);
        char degree = '\u00B0';
        char minute = '\'';
        char second = '\"';
        String [] temp;
        int idx;
        
        if (str == null || str.length() == 0) {
            return null;
        }
        
        str = str.replaceAll(" ", "");
        
        idx = str.indexOf(degree);
        if (idx > 0) {
            // cannot start with degree char
            temp = str.split(String.valueOf(degree));
            bgDegree = parseDecimal(temp[0], locale);
            if (temp.length > 1) {
                str = temp[1];
            }
        }
        
        idx = str.indexOf(minute);
        if (idx > 0) {
            temp = str.split(String.valueOf(minute));
            bgMinutes = parseDecimal(temp[0], locale);
            if (temp.length > 1) {
                str = temp[1];
            }
        }
        
        idx = str.indexOf(second);
        if (idx > 0) {
            temp = str.split(String.valueOf(second));
            bgSeconds = parseDecimal(temp[0], locale);
        }
        
        value = bgDegree.add(bgMinutes.divide(new BigDecimal(60), 2, RoundingMode.HALF_UP).add(bgSeconds.divide(new BigDecimal(3600), 2, RoundingMode.HALF_UP)));
        
        return (value == BigDecimal.ZERO ? null : value);
    }
}
