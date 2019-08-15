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
public @interface MetaColumn {
    
    String name() default "";
    String dataType() default "";
    int length() default 0;
    int scale() default 0;
    boolean allowNull() default true;
    Class<? extends Object> defaultValue() default void.class;
    boolean primaryKey() default false;
    boolean unique() default false;
    MetaReferencedTable referencedTable() default @MetaReferencedTable();
    String referencedProperty() default "";
    boolean search() default false;
}
