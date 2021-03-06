package com.mybatis.learn.plugin.dialect;

public class MysqlDialect implements Dialect {
    @Override
    public boolean supportPage() {
        return true;
    }

    /**
     * mysql 分页
     * select * from user limit 10000, 100;
     * <p>
     * 用索引的方式对分页进行优化(子查询+索引，id必须带索引)
     * select * from tmp where id >= ( select id from user limit 10000, 1 ) limit 100;
     */
    @Override
    public String getPageSql(String sql, int offset, int limit) {
        sql = sql.trim();
        boolean hasUpdate = false;
        String forUpdatePart = " for update";
        if (sql.toLowerCase().endsWith(forUpdatePart)) {
            sql = sql.substring(0, sql.length() - forUpdatePart.length());
            hasUpdate = true;
        }
        StringBuffer result = new StringBuffer(sql.length() + 100);
        result.append(sql).append(" limit ");
        if (offset > 0) {
            result.append(offset).append(", ").append(limit);
        } else {
            result.append(limit);
        }
        if (hasUpdate) {
            result.append(forUpdatePart);
        }
        return result.toString();
    }
}
