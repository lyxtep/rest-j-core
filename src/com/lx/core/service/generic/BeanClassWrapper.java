package com.lx.core.service.generic;

import java.lang.reflect.Field;
import java.util.List;

/**
 *
 * @author lx.ds
 */
public class BeanClassWrapper {

    private Class beanClass;
    private List<Field> beanDataFields;
    
    public BeanClassWrapper(Class clazz) {
        initialize(clazz);
    }
    
    private void initialize(Class clazz) {
        this.beanClass = clazz;
        this.beanDataFields = MetadataService.getInstance().getMetaDataFields(clazz);
    }

    /**
     * @return the beanClass
     */
    public Class getBeanClass() {
        return beanClass;
    }

    /**
     * @return the beanDataFields
     */
    public List<Field> getBeanDataFields() {
        return beanDataFields;
    }
}
