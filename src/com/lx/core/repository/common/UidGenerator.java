package com.lx.core.repository.common;

import java.util.Calendar;
import java.util.Random;

/**
 *
 * @author lx.ds
 */
public class UidGenerator {
    
    // Singleton pattern
    private static UidGenerator instance = null;

    private final char[] URL_SAFE_ENCODE_TABLE = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '_'
    };
    
    private final Random rn;
    
    private int inc = 0;
    
    // Singleton pattern
    private UidGenerator() {
        // Constructor
        rn = new Random();
    }

    // Singleton pattern
    public static synchronized UidGenerator getInstance() {
        if (instance == null) {
            instance = new UidGenerator();
        }

        return instance;
    }
    
    public synchronized String getUid() {
        Calendar cal = Calendar.getInstance();
        
        StringBuilder uid = new StringBuilder();
        
        uid.append(URL_SAFE_ENCODE_TABLE[rn.nextInt(63)]);
        uid.append(URL_SAFE_ENCODE_TABLE[cal.get(Calendar.SECOND)]);
        uid.append(URL_SAFE_ENCODE_TABLE[cal.get(Calendar.MILLISECOND) / 63]);
        uid.append(URL_SAFE_ENCODE_TABLE[cal.get(Calendar.MONTH)]);
        uid.append(URL_SAFE_ENCODE_TABLE[cal.get(Calendar.MINUTE)]);
        uid.append(URL_SAFE_ENCODE_TABLE[cal.get(Calendar.DAY_OF_MONTH)]);
        uid.append(URL_SAFE_ENCODE_TABLE[cal.get(Calendar.YEAR) / 63]);
        uid.append(URL_SAFE_ENCODE_TABLE[cal.get(Calendar.HOUR_OF_DAY)]);
        //uid.append(URL_SAFE_ENCODE_TABLE[cal.get(Calendar.YEAR) % 63]);
        uid.append(URL_SAFE_ENCODE_TABLE[inc]);
        uid.append(URL_SAFE_ENCODE_TABLE[rn.nextInt(63)]);
        //uid.append(URL_SAFE_ENCODE_TABLE[cal.get(Calendar.MILLISECOND) % 63]);
        
        if (inc > 61) {
            inc = 0;
        } else {
            inc++;
        }
        
        return uid.toString();
    }
            
    public synchronized void sleep() {
        final long INTERVAL = 1000100;
        long start = System.nanoTime();
        long end=0;
        do{
            end = System.nanoTime();
        } while(start + INTERVAL >= end);
    }
}
