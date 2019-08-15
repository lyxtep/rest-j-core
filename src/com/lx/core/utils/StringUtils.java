package com.lx.core.utils;

import java.text.DecimalFormatSymbols;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author lx.ds
 */
public class StringUtils {

    public static String nullHandler(String value) {
        return (value == null ? "" : value);
    }

    public static String nullHandler(Object value) {
        return (value == null ? "" : value.toString());
    }

    public static boolean isNullOrEmpty(String value) {

        return (value == null || value.isEmpty());
    }

    public static String join(String delimiter, List<String> items) {
        if (items == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (String item : items) {
            if (sb.length() > 0) {
                sb.append(delimiter);
            }
            sb.append(item);
        }
        return sb.toString();
    }

    public static String join(String delimiter, Set items) {
        Object item;
        if (items == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        Iterator iter = items.iterator();
        while (iter.hasNext()) {
            item = iter.next();
            if (item == null) {
                continue;
            }

            if (sb.length() > 0) {
                sb.append(delimiter);
            }
            sb.append(item);
        }
        return sb.toString();
    }

    public static String capitalizeFirstLetter(String value) {
        if (value == null || value.length() == 0) {
            return value;
        }
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    public static String firstCharacterToLower(String value) {
        if (value == null || value.length() == 0) {
            return value;
        }
        return value.substring(0, 1).toLowerCase() + value.substring(1);
    }

    public static boolean isNumeric(String str) {
        DecimalFormatSymbols currentLocaleSymbols = DecimalFormatSymbols.getInstance();
        char localeMinusSign = currentLocaleSymbols.getMinusSign();

        if (!Character.isDigit(str.charAt(0)) && str.charAt(0) != localeMinusSign) {
            return false;
        }

        boolean isDecimalSeparatorFound = false;
        char localeDecimalSeparator = currentLocaleSymbols.getDecimalSeparator();

        for (char c : str.substring(1).toCharArray()) {
            if (!Character.isDigit(c)) {
                if (c == localeDecimalSeparator && !isDecimalSeparatorFound) {
                    isDecimalSeparatorFound = true;
                    continue;
                }
                return false;
            }
        }
        return true;
    }
    
    public static boolean equalsIgnoreCase(String value1, String value2){
        
        if (value1 == null || value2 == null) return false;
        
        return value1.equalsIgnoreCase(value2);
    }
    public static boolean equals(String value1, String value2){
        
        if (value1 == null || value2 == null) return false;
        
        return value1.equals(value2);
    }
    
    public static String trimStart(String s, String value) {
        return s.replaceAll(value + "$", "");
    }
    public static String trimEnd(String s, String value) {
        return s.replaceAll("^" + value, "");
    }
    public static String trim(String s, String value) {
        return trimEnd(trimStart(s, value), value);
    }
}
