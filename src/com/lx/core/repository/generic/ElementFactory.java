package com.lx.core.repository.generic;

import com.lx.core.configuration.LXConfig;
import com.lx.core.repository.common.UidGenerator;
import com.lx.core.repository.entity.LXElement;
import com.lx.core.security.IUserIdentity;
import java.util.Calendar;

/**
 *
 * @author lx.ds
 */
public class ElementFactory {
    
    // Singleton pattern
    private static ElementFactory instance = null;

    // Singleton pattern
    private ElementFactory() {
        // Constructor
    }

    // Singleton pattern
    public static synchronized ElementFactory getInstance() {
        if (instance == null) {
            instance = new ElementFactory();
        }

        return instance;
    }
    
    public synchronized LXElement newElement(EntityClassWrapper entityWrapper, IUserIdentity user) {
        return newElement(entityWrapper, null, user);
    }
    public synchronized LXElement newElement(EntityClassWrapper classWrapper, String state, IUserIdentity user) {
        Calendar cal = Calendar.getInstance();

        LXElement theElement = new LXElement();
        theElement.setId(UidGenerator.getInstance().getUid());
        theElement.setSecurityClass(classWrapper.getEntityTable().getDefaultSecurityClass());
        theElement.setClassName(classWrapper.getEntityTable().getName());
        theElement.setCreateDate(cal.getTime());
        theElement.setState(state == null ? LXConfig.getInstance().getElementStateActive() : state);
        theElement.setStateDate(cal.getTime());
        theElement.setLastupdateDate(cal.getTime());
        
        if (user != null) {
            theElement.setCompanyId(user.getCompanyId());
            theElement.setDepartmentId(user.getDepartmentId());
            theElement.setEmployeeId(user.getEmployeeId());
            theElement.setOwner(user.getUsername());
            theElement.setCreateBy(user.getUsername());
            theElement.setStateBy(user.getUsername());
            theElement.setLastupdateBy(user.getUsername());
        }

        theElement.setEffectiveDate(cal.getTime());
        
        return theElement;
    }
    
    public synchronized LXElement updateValues(EntityObjectWrapper objectWrapper, LXElement element, IUserIdentity user) {
        Calendar cal = Calendar.getInstance();
        EntityClassWrapper classWrapper = objectWrapper.getClassWrapper();
        
        if (element == null) {
            element = newElement(classWrapper, user);
        }

        element.setLastupdateDate(cal.getTime());
        if (user != null) element.setLastupdateBy(user.getUsername());

        
        if (objectWrapper.getCompanyId() != null) {
            element.setCompanyId(objectWrapper.getCompanyId());
        }
        
        if (objectWrapper.getDepartmentId() != null) {
            element.setDepartmentId(objectWrapper.getDepartmentId());
        }
        
        if (objectWrapper.getEmployeeId() != null) {
            element.setEmployeeId(objectWrapper.getEmployeeId());
        }
        
        if (objectWrapper.getSecurityClass() != null) {
            element.setSecurityClass(objectWrapper.getSecurityClass());
        }
        
        if (objectWrapper.getState()!= null) {
            element.setState(objectWrapper.getState());
        }
        
        if (objectWrapper.getKeyword() != null) {
            element.setKeyword(objectWrapper.getKeyword());
        }
        
        if (objectWrapper.getTags() != null) {
            element.setTags(objectWrapper.getTags());
        }
        
        if (objectWrapper.getParentId() != null) {
            element.setParentId(objectWrapper.getParentId());
        }
        
        if (objectWrapper.getPriority() != 0) {
            element.setPriority(objectWrapper.getPriority());
        }
        
        return element;
    }
}
