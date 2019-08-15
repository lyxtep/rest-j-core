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
@Target({ElementType.TYPE})
public @interface MetaTable {

    String name() default "";
    String resourceName() default "";
    String defaultSecurityClass() default "";
    String logColumns() default "";
    MetaState state() default @MetaState(tableName = "", joinColumn = ""); 
}
