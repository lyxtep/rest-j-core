package com.lx.core.repository.sql;

import com.lx.core.repository.common.DbConnectionFactory;
import com.lx.core.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lx.ds
 */
public class SelectQuery {

    private boolean distinct;
    private List<String> columns;
    private List<String> from;
    private List<String> where;
    private List<String> orderBy;

    @Override
    public String toString() {
        return DbConnectionFactory.getInstance().getSqlProvider().addPagingToSql(getString(), 0, 0);
    }
    
    public String toString(int start, int limit) {
        return DbConnectionFactory.getInstance().getSqlProvider().addPagingToSql(getString(), start, limit);
    }
    
    public String toCountString() {
        return getString(true);
    }
    
    private String getString() {
        return getString(false);
    }
    private String getString(boolean doCount) {
        
        StringBuilder sbQuery = new StringBuilder();

        // SELECT CLAUSE
        if (doCount) {
            sbQuery.append("SELECT COUNT(*)");
        } else {
            sbQuery.append(String.format("SELECT %s %s", distinct ? "DISTINCT" : "", StringUtils.join(",", getColumns())));
        }
        
        sbQuery.append(String.format(" FROM %s", StringUtils.join(" ", getFrom())));

        // WHERE CLAUSE
        if (getWhere().size() > 0) {
            sbQuery.append(String.format(" WHERE %s", StringUtils.join(" ", getWhere())));
        }

        // ORDER BY CLAUSE
        if (getOrderBy().size() > 0) {
            sbQuery.append(String.format(" ORDER BY %s", StringUtils.join(",", getOrderBy())));
        }
        
        return sbQuery.toString();
    }

    /**
     * Add all columns in the SELECT clause of the query
     *
     * @param table
     * @return query
     */
    public SelectQuery addSelectTableColumns(String table) {
        getColumns().add(table + ".*");
        return this;
    }

    /**
     * Add column in the SELECT clause of the query
     *
     * @param column
     * @return query
     */
    public SelectQuery addSelectColumn(String column) {
        getColumns().add(column);
        return this;
    }

    /**
     * Add array of columns in the SELECT clause of the query
     *
     * @param columns
     * @return query
     */
    public SelectQuery addSelectColumns(List<String> columns) {
        getColumns().addAll(columns);
        return this;
    }

    /**
     * Add table in the FROM clause of the query
     * @param table
     * @return query
     */
    public SelectQuery addFromTable(String table) {
        getFrom().add(table);
        return this;
    }    
    /**
     * Add Inner Join in the FROM clause of the query
     *
     * @param table
     * @param rule
     * @return query
     */
    public SelectQuery addFromInnerJoin(String table, String rule) {
        _addFrom("INNER JOIN", table, rule);
        return this;
    }

    /**
     * Add Left Join in the FROM clause of the query
     *
     * @param table
     * @param rule
     * @return query
     */
    public SelectQuery addFromLeftJoin(String table, String rule) {
        _addFrom("LEFT JOIN", table, rule);
        return this;
    }

    /**
     * Add Right Join in the FROM clause of the query
     *
     * @param table
     * @param rule
     * @return query
     */
    public SelectQuery addFromRightJoin(String table, String rule) {
        _addFrom("RIGHT JOIN", table, rule);
        return this;
    }

    /**
     * Add a rule with AND operand to WHERE clause of the query
     *
     * @param criterion
     * @return query
     */
    public SelectQuery addAndCriterion(String criterion) {
        _addWhere("AND", criterion);
        return this;
    }

    /**
     * Add a rule with OR operand to WHERE clause of the query
     *
     * @param criterion
     * @return query
     */
    public SelectQuery addOrCriterion(String criterion) {
        _addWhere("OR", criterion);
        return this;
    }

    /**
     * Add a rule without any operand to WHERE clause of the query
     *
     * @param criterion
     * @return query
     */
    public SelectQuery addWhereClause(String criterion) {
        getWhere().add(criterion);
        return this;
    }

    /**
     * Add a column to ORDER BY ASC clause of the query
     *
     * @param column
     * @return query
     */
    public SelectQuery addAscOrderBy(String column) {
        _addOrderBy(column, "ASC");
        return this;
    }

    /**
     * Add a column to ORDER BY DESC clause of the query
     *
     * @param column
     * @return query
     */
    public SelectQuery addDescOrderBy(String column) {
        _addOrderBy(column, "DESC");
        return this;
    }

    /**
     * PRIVATES
     */
    private void _addFrom(String innerType, String table, String rule) {
        getFrom().add(String.format("%s %s ON %s", innerType, table, rule).trim());
    }

    private void _addWhere(String type, String criterion) {
        if (getWhere().isEmpty()) {
            getWhere().add(criterion.trim());
        } else {
            getWhere().add(String.format("%s %s", type, criterion).trim());
        }
    }

    private void _addOrderBy(String column, String type) {
        getOrderBy().add(String.format("%s %s", column, type).trim());
    }

    /**
     * @return the distinct
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * @param distinct the distinct to set
     * @return 
     */
    public SelectQuery setDistinct(boolean distinct) {
        this.distinct = distinct;
        return this;
    }

    /**
     * @return the columns
     */
    public List<String> getColumns() {
        if (columns == null) {
            columns = new ArrayList<String>();
        }
        return columns;
    }

    /**
     * @return the from
     */
    public List<String> getFrom() {
        if (from == null) {
            from = new ArrayList<String>();
        }
        return from;
    }

    /**
     * @return the where
     */
    public List<String> getWhere() {
        if (where == null) {
            where = new ArrayList<String>();
        }
        return where;
    }

    /**
     * @return the orderBy
     */
    public List<String> getOrderBy() {
        if (orderBy == null) {
            orderBy = new ArrayList<String>();
        }
        return orderBy;
    }

}
