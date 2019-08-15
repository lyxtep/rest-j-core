package com.lx.core.service;

import com.lx.core.repository.entity.LXEntryProperty;
import com.lx.core.repository.generic.ITransaction;
import com.lx.core.repository.transaction.ReadSettingsTransaction;
import com.lx.core.repository.transaction.SaveSettingsTransaction;
import com.lx.core.security.IUserIdentity;
import com.lx.core.service.generic.BeanClassWrapper;
import com.lx.core.service.generic.GenericService;
import com.lx.core.utils.ClassUtils;
import com.lx.core.utils.LxCacheUtils;
import com.lx.core.utils.ObjectUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lx.ds
 */
public class EntryPropertyService {

    // Singleton pattern
    private static EntryPropertyService instance = null;

    // Singleton pattern
    private EntryPropertyService() {
        // Constructor
    }

    // Singleton pattern
    public static synchronized EntryPropertyService getInstance() {
        if (instance == null) {
            instance = new EntryPropertyService();
        }

        return instance;
    }
    
    public synchronized <T extends Object> T get(Class<T> clazz, String entryId, IUserIdentity user) throws Exception {
        List<ITransaction> transactions = new ArrayList<>();
        ReadSettingsTransaction<T> readSettingsTransaction;
        
        readSettingsTransaction = new ReadSettingsTransaction<>(clazz, entryId, user);
        transactions.add(readSettingsTransaction);
        
        GenericService.getInstance().executeTransactions(transactions);
        
        return readSettingsTransaction.getSettings();
    }
    
    public synchronized <T extends Object> T update(Class<T> clazz, String entryId, T obj, IUserIdentity user) throws Exception {
        List<ITransaction> transactions = new ArrayList<>();
        SaveSettingsTransaction<T> saveSettingsTransaction;
        
        saveSettingsTransaction = new SaveSettingsTransaction<>(clazz, entryId, obj, user);
        transactions.add(saveSettingsTransaction);
        
        GenericService.getInstance().executeTransactions(transactions);
        
        return saveSettingsTransaction.getSettings();
    }
    
    public synchronized <T extends Object> T mapSettingsToObject(Class<T> clazz, List<LXEntryProperty> entryProperties) {

        T obj = ClassUtils.getInstance().createIntance(clazz);
        
        for (LXEntryProperty aProperty : entryProperties) {
            ObjectUtils.setDeserializedValue(obj, aProperty.getKeyword(), aProperty.getValue());
        }

        return obj;

    }
    
    public synchronized List<LXEntryProperty> mapSettingsFromObject(Object obj) {

        BeanClassWrapper beanClassWrapper = LxCacheUtils.getInstance().getBeanClassWrapper(obj.getClass());
        List<LXEntryProperty> entryProperties = new ArrayList<LXEntryProperty>();
        
        List<Field> fields = beanClassWrapper.getBeanDataFields(); // get all declared fields
        for (Field field : fields) {
            String sValue = ObjectUtils.getSerializedValue(obj, field.getName());
            if (sValue != null) {
                LXEntryProperty aProperty = new LXEntryProperty();
                aProperty.setKeyword(field.getName());
                aProperty.setValue(sValue);
                entryProperties.add(aProperty);
            }
        }

        return entryProperties;
    }
}
