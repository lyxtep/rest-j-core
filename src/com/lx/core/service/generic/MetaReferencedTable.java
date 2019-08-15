
package com.lx.core.service.generic;

/**
 *
 * @author lx.ds
 */
public @interface MetaReferencedTable {
    String name() default "";
    String primaryKey() default "";
    String selectColumn() default "";
    String selectAlias() default "";
}
