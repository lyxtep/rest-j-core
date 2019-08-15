package com.lx.core.repository.generic;

import com.lx.core.repository.common.DbConnectionFactory;
import com.lx.core.repository.entity.LXElement;
import com.lx.core.repository.entity.LXFile;
import com.lx.core.repository.sql.Column;
import com.lx.core.repository.sql.LXSecuritySelectQuery;
import com.lx.core.repository.sql.LXSelectQuery;
import com.lx.core.request.SearchRequest;
import com.lx.core.security.IUserIdentity;
import com.lx.core.service.FileService;
import com.lx.core.service.generic.MetadataService;
import com.lx.core.utils.LxCacheUtils;
import com.lx.core.utils.ObjectUtils;
import com.lx.core.utils.StringUtils;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.sql2o.Connection;
import org.sql2o.Query;

/**
 *
 * @author lx.ds
 */
public class RepositoryTransactions {

    // Singleton pattern
    private static RepositoryTransactions instance = null;

    // Singleton pattern
    private RepositoryTransactions() {
        // Constructor
    }

    // Singleton pattern
    public static synchronized RepositoryTransactions getInstance() {
        if (instance == null) {
            instance = new RepositoryTransactions();
        }

        return instance;
    }

    public synchronized <TEntity extends Object> String create(Class<TEntity> clazz, TEntity entity, IUserIdentity user, Connection cn) throws Exception {
        return _create(entity, user, cn);
    }
        
    
    public synchronized <TEntity extends Object> List<TEntity> read(Class<TEntity> clazz, SearchRequest params, boolean readJoin, IUserIdentity user, Connection cn) {
        return _read(clazz, params, readJoin, user, cn);
    }

    
    public synchronized <TEntity extends Object> void update(Class<TEntity> clazz, TEntity entity, IUserIdentity user, Connection cn) throws Exception {
        _update(entity, user, cn);
    }
    
    public synchronized <TEntity extends Object> void delete(Class<TEntity> clazz, Object id, IUserIdentity user, Connection cn) throws Exception {
        Column pkElementColumn = MetadataService.getInstance().getPrimaryKeyColumn(LXElement.class);
        Column pkColumn = MetadataService.getInstance().getPrimaryKeyColumn(clazz);

        if (id == null) {
            throw new Exception("The Primary Key value must not be null");
        }
        
        // delete element item
        cn.createQuery(DbConnectionFactory.getInstance().getSqlProvider().getDeleteSql(LXElement.class.getSimpleName(), pkElementColumn.getName()))
                .addParameter(pkElementColumn.getName(), id)
                .executeUpdate();
        // delete class table item
        cn.createQuery(DbConnectionFactory.getInstance().getSqlProvider().getDeleteSql(clazz.getSimpleName(), pkColumn.getName()))
                .addParameter(pkColumn.getName(), id)
                .executeUpdate();
    }
    
    
    private String _create(Object entity, IUserIdentity user, Connection cn) throws Exception {
        EntityObjectWrapper entityWrapper = new EntityObjectWrapper(entity);
        EntityObjectWrapper elementWrapper = new EntityObjectWrapper(ElementFactory.getInstance().updateValues(
                entityWrapper, ElementFactory.getInstance().newElement(entityWrapper.getClassWrapper(), user), user));
        Query query;
        
        if (entityWrapper.getClassWrapper().getPrimaryKeyColumn() != null) {
            ObjectUtils.set(entityWrapper.getEntity(), entityWrapper.getClassWrapper().getPrimaryKeyColumn().getFieldName(), elementWrapper.getId());
        }
        
        // create element item
        query = cn.createQuery(DbConnectionFactory.getInstance().getSqlProvider().getInsertSql(
                elementWrapper.getClassWrapper().getEntityTable().getName(), elementWrapper.getClassWrapper().getEntityColumns()));
        for (Column aColumn : elementWrapper.getClassWrapper().getEntityColumns()) {
            query.addParameter(aColumn.getName(), ObjectUtils.get(elementWrapper.getEntity(), aColumn.getFieldName()));
        }
        query.executeUpdate();
        
        // create class table item
        query = cn.createQuery(DbConnectionFactory.getInstance().getSqlProvider().getInsertSql(
                entityWrapper.getClassWrapper().getEntityTable().getName(), entityWrapper.getClassWrapper().getEntityColumns()));
        for (Column aColumn : entityWrapper.getClassWrapper().getEntityColumns()) {
            query.addParameter(aColumn.getName(), ObjectUtils.get(entityWrapper.getEntity(), aColumn.getFieldName()));
            
            if (!StringUtils.isNullOrEmpty(aColumn.getForeignTable()) && !StringUtils.isNullOrEmpty(aColumn.getForeignProperty())
                    && !ObjectUtils.isDefaultValue(entityWrapper.getEntity(), aColumn.getForeignProperty())) {
                // Create Foreign Entities if exists
                _saveForeignEntity(entityWrapper, aColumn, user, cn);
            }
        }
        query.executeUpdate();
        
        return elementWrapper.getId();
    }
    
    
    private <TEntity extends Object> List<TEntity> _read(Class<TEntity> clazz, SearchRequest params, boolean readJoin, IUserIdentity user, Connection cn) {
        
        if (params == null) params = new SearchRequest(0, 0, null, null, null);
        
        EntityClassWrapper entityClassWrapper = LxCacheUtils.getInstance().getEtityClassWrapper(clazz);
        LXSecuritySelectQuery sql = new LXSecuritySelectQuery(entityClassWrapper, user);
        Map<String, Object> dParams = new HashMap<String, Object>();
        StringBuilder sbSearchByQuery = (!StringUtils.isNullOrEmpty(params.getValue()) ? new StringBuilder() : null);
        StringBuilder sbTagsQuery;

        // SELECT


        // FROM
        for (Column aColumn : sql.getEntityClassWrapper().getEntityColumns()) {
            
            if (readJoin && !StringUtils.isNullOrEmpty(aColumn.getForeignTable()) && !StringUtils.isNullOrEmpty(aColumn.getForeignColumns())) {
                sql.addSelectColumn(String.format("%s.%s AS %s",
                    aColumn.getForeignTable(),
                    aColumn.getForeignSelectColumns(),
                    (StringUtils.isNullOrEmpty(aColumn.getForeignSelectAliases()) ? aColumn.getForeignTable() + aColumn.getForeignSelectColumns() : aColumn.getForeignSelectAliases())));

                sql.addFromLeftJoin(aColumn.getForeignTable(), String.format("%s.%s = %s.%s", LXSelectQuery.ENTITY_ALIAS, aColumn.getName(), aColumn.getForeignTable(), aColumn.getForeignColumns()));
            }

            // WHERE
            if (params.getEntity() != null && !ObjectUtils.isDefaultValue(params.getEntity(), aColumn.getFieldName())) {
                sql.addAndCriterion(String.format("%1$s.%2$s LIKE :%2$s", LXSelectQuery.ENTITY_ALIAS, aColumn.getName()));
                dParams.put(aColumn.getName(), ObjectUtils.get(params.getEntity(), aColumn.getFieldName()));
            }

            if (sbSearchByQuery != null && aColumn.isSearch()) {
                if (sbSearchByQuery.length() > 0) {
                    sbSearchByQuery.append(" OR ");
                }
                sbSearchByQuery.append(LXSelectQuery.ENTITY_ALIAS).append(".").append(aColumn.getName()).append(" LIKE :query");

            }
        }

        if (sbSearchByQuery != null && sbSearchByQuery.length() > 0) {
            sql.addAndCriterion("(" + sbSearchByQuery.toString() + ")");
            dParams.put("query", "%" + params.getValue()+ "%");
        }
        
        if (params.getEntity() != null) {
            
            if (entityClassWrapper.isKeywordExist() && !ObjectUtils.isDefaultValue(params.getEntity(), "keyword")) {
                sql.addAndCriterion(String.format("%1$s.%2$s LIKE :%2$s", LXSelectQuery.ELEMENT_ALIAS, "keyword"));
                dParams.put("keyword", ObjectUtils.get(params.getEntity(), "keyword"));
            }        
            if (entityClassWrapper.isTagsExist()&& !ObjectUtils.isDefaultValue(params.getEntity(), "tags")) {
                sql.addAndCriterion(String.format("%1$s.%2$s LIKE :%2$s", LXSelectQuery.ELEMENT_ALIAS, "tags"));
                dParams.put("tags", ObjectUtils.get(params.getEntity(), "tags"));
            }
        
        }

        // QUERY by TAGS
        if (params.getTags() != null && params.getTags().size() > 0) {
            sbTagsQuery = new StringBuilder();
            
            for (String tag : params.getTags()) {
                if (sbTagsQuery.length() > 0)
                {
                    sbTagsQuery.append(" OR ");
                }
                sbTagsQuery.append(String.format("(%1$s.Tags LIKE '%2$s' OR %1$s.Tags LIKE '%%,%2$s' OR %1$s.Tags LIKE '%2$s,%%' OR %1$s.Tags LIKE '%%,%2$s,%%S')", LXSelectQuery.ELEMENT_ALIAS, tag));
            }

            if (sbTagsQuery.length() > 0) {
                sql.addAndCriterion("(" + sbTagsQuery.toString() + ")");
            }
        }

        if (entityClassWrapper.isOrderByPriority()) {
            sql.addAscOrderBy(LXSelectQuery.ENTITY_ALIAS + ".priority");
        }
        sql.addDescOrderBy(LXSelectQuery.ELEMENT_ALIAS + ".createDate");
        
        
        Query query = cn.createQuery(sql.toString(params.getStart(), params.getLimit()));
        for (Map.Entry<String, Object> entry : dParams.entrySet()) {
            query.addParameter(entry.getKey(), entry.getValue());
        }

        return query.throwOnMappingFailure(false).executeAndFetch(clazz);
    }
    
    
    private void _update(Object entity, IUserIdentity user, Connection cn) throws Exception {
        EntityObjectWrapper entityWrapper = new EntityObjectWrapper(entity);
        EntityObjectWrapper elementWrapper = new EntityObjectWrapper(ElementFactory.getInstance().updateValues(
                entityWrapper, new LXElement(), user));
        Query query;
        
        if (entityWrapper.getId() == null) {
            throw new Exception("The Primary Key value must not be null");
        }
        
        ObjectUtils.set(elementWrapper.getEntity(), elementWrapper.getClassWrapper().getPrimaryKeyColumn().getFieldName(), entityWrapper.getId());
        
        // create element item
        query = cn.createQuery(DbConnectionFactory.getInstance().getSqlProvider().getUpdateSql(
                elementWrapper.getClassWrapper().getEntityTable().getName(), elementWrapper.getDirtyColumns()));
        for (Column aColumn : elementWrapper.getDirtyColumns()) {
            query.addParameter(aColumn.getName(), ObjectUtils.get(elementWrapper.getEntity(), aColumn.getFieldName()));
        }
        query.executeUpdate();
        
        // create class table item
        query = cn.createQuery(DbConnectionFactory.getInstance().getSqlProvider().getUpdateSql(
                entityWrapper.getClassWrapper().getEntityTable().getName(), entityWrapper.getDirtyColumns()));
        for (Column aColumn : entityWrapper.getDirtyColumns()) {
            query.addParameter(aColumn.getName(), ObjectUtils.get(entityWrapper.getEntity(), aColumn.getFieldName()));
            
            if (!StringUtils.isNullOrEmpty(aColumn.getForeignTable()) && !StringUtils.isNullOrEmpty(aColumn.getForeignProperty())
                    && !ObjectUtils.isDefaultValue(entityWrapper.getEntity(), aColumn.getForeignProperty())) {
                // Create Foreign Entities if exists
                _saveForeignEntity(entityWrapper, aColumn, user, cn);
            }
        }
        query.executeUpdate();
    }
    
    
    private String _saveForeignEntity(EntityObjectWrapper entityWrapper, Column column, IUserIdentity user, Connection cn) throws Exception {
        EntityObjectWrapper foreignEntityWrapper = new EntityObjectWrapper(ObjectUtils.get(entityWrapper.getEntity(), column.getForeignProperty()));            

        if (foreignEntityWrapper.getEntity() instanceof LXFile) {
            LXFile fileEntity = (LXFile)foreignEntityWrapper.getEntity();
            /*if (String.IsNullOrEmpty(fileEntity.Id))
            {
                object entityFromDB = _getSimpleEntityById(entityWrapper, cn, t);
            }*/

            File file = FileService.getInstance().moveFileFromTempToRepository(fileEntity.getName());
            fileEntity.setSize(file.length());
        }

        if (StringUtils.isNullOrEmpty(foreignEntityWrapper.getId())) {
            // create foreign entity
            foreignEntityWrapper.setId(_create(foreignEntityWrapper.getEntity(), user, cn));
        } else {
            // update foreign entity
            _update(foreignEntityWrapper.getEntity(), user, cn);
        }

        ObjectUtils.set(entityWrapper.getEntity(), column.getFieldName(), foreignEntityWrapper.getId());

        return foreignEntityWrapper.getId();
    }
}
