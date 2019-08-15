package com.lx.core.service.generic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author lx.ds
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface MetaField {
    
    String name() default "";
    String label() default "";
    String type() default "";
    boolean mandatory() default false;
    boolean readOnly() default false;
    boolean remoteQuery() default false;
    Class<? extends Object> defaultValue() default void.class;
    String validation() default "";
    int minValue() default 0;
    int maxValue() default 0;
    int length() default 0;
    int decimalPrecision() default 0;
    int priority() default 0;
    String group() default "";
    String data() default "";
    String event() default "";
    int width() default 0;
    double flex() default 0;
    int row() default 0;
}
