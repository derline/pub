package sjj.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import sjj.common.ClassUtil;

import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 仅用于数据库查询操作的DAO。当Controller中的方法逻辑比较简单的情况下可以不写service方法，直接在Controller中使用此类进行数据查询。
 */
@Repository
public class BaseQuery {
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final static String COUNT_START = "/*start*/";
    private static final String COUNT_END = "/*end*/";

    public int count(String table, String whereKeys, Object... whereValues) {
        List<Object> params = new ArrayList<>();
        StringBuilder sb = makeQuerySQLAndParams(params, table, "count(*)", whereKeys, whereValues);
        return queryForInt(sb, params.toArray());
    }

    /**
     * 根据id获取对象
     * @param class1 对象类型
     * @param id     id值
     */
    public <T> T getById(Class<T> class1, int id) {
        return selectEntity(ClassUtil.underscoreName(class1.getSimpleName()), class1, "*", ClassUtil.underscoreName(ClassUtil.getIdKey(class1)), id);
    }

    public <T> List<T> selectList(String table, String selectKeys, Class<T> class1, String whereKeys, Object... whereValues) {
        List<Object> params = new ArrayList<>();
        StringBuilder sb = makeQuerySQLAndParams(params, table, selectKeys, whereKeys, whereValues);
        return queryForEntityList(sb, class1, params);
    }

    public <T> List<T> selectList(String table, Class<T> class1, String selectKeys, String whereKeys, Object... whereValues) {
        return selectList(table, "", class1, selectKeys, whereKeys, whereValues);
    }

    public <T> List<T> selectList(String table, String orderby, Class<T> class1, String selectKeys, String whereKeys, Object... whereValues) {
        List<Object> params = new ArrayList<>();
        StringBuilder sb = makeQuerySQLAndParams(params, table, selectKeys, whereKeys, whereValues);
        sb.append(orderby);
        return queryForEntityList(sb, class1, params);
    }

    public List<String> selectStringList(String table, String selectKey, String whereKeys, Object... whereValues) {
        return selectObjectList(table, String.class, selectKey, whereKeys, whereValues);
    }

    public List<Integer> selectIntList(String table, String selectKey, String whereKeys, Object... whereValues) {
        return selectObjectList(table, Integer.class, selectKey, whereKeys, whereValues);
    }

    public <T> T selectObject(String table, Class<T> class1, String selectKey, String whereKeys, Object... whereValues) throws DataAccessException {
        List<Object> params = new ArrayList<>();
        StringBuilder sb = makeQuerySQLAndParams(params, table, selectKey, whereKeys, whereValues);
        return queryForObject(sb, class1, params);
    }

    public <T> T selectEntity(String table, Class<T> class1, String selectKeys, String whereKeys, Object... whereValues) {
        List<Object> params = new ArrayList<>();
        StringBuilder sb = makeQuerySQLAndParams(params, table, selectKeys, whereKeys, whereValues);
        return queryForEntity(sb, class1, params);
    }

    private <T> List<T> selectObjectList(String table, Class<T> cls, String selectKeys, String whereKeys, Object... whereValues) {
        List<Object> params = new ArrayList<>();
        StringBuilder sb = makeQuerySQLAndParams(params, table, selectKeys, whereKeys, whereValues);
        return queryForSimpleObjectList(sb.toString(), cls, params.toArray());
    }

    public List<Map<String, Object>> selectMapList(String table, String selectKeys) {
        return selectMapList(table, selectKeys, "");
    }

    public List<Map<String, Object>> selectMapList(String table, String selectKeys, String whereKeys, Object... whereValues) {
        List<Object> params = new ArrayList<>();
        StringBuilder sb = makeQuerySQLAndParams(params, table, selectKeys, whereKeys, whereValues);
        return queryForList(sb, params);
    }

    public int selectInt(String table, String selectKey, String whereKeys, Object... whereValues) {
        List<Object> params = new ArrayList<>();
        StringBuilder sb = makeQuerySQLAndParams(params, table, selectKey, whereKeys, whereValues);
        return queryForInt(sb, params.toArray());
    }

    protected int queryForInt(StringBuilder sb, Object... params) {
        return queryForInt(sb.toString(), params);
    }

    protected int queryForInt(String sql, Object... params) {
        Integer num = queryForObject(sql, Integer.class, params);
        return num == null ? 0 : num;
    }

    public long selectLong(String table, String selectKey, String whereKeys, Object... whereValues) {
        List<Object> params = new ArrayList<>();
        StringBuilder sb = makeQuerySQLAndParams(params, table, selectKey, whereKeys, whereValues);
        return queryForLong(sb, params.toArray());
    }

    private StringBuilder makeQuerySQLAndParams(List<Object> params, String table, String selectKeys, String whereKeys, Object... whereValues) {
        StringBuilder sb = new StringBuilder(200);
        sb.append(" select ").append(selectKeys).append(" from ").append(table);
        where(sb, params, whereKeys, whereValues);
        return sb;
    }

    protected long queryForLong(StringBuilder sb, Object... params) {
        Long num = queryForObject(sb, Long.class, params);
        return num == null ? 0 : num;
    }

    public String selectString(String table, String selectKey, String whereKeys, Object... whereValues) {
        List<Object> params = new ArrayList<>();
        StringBuilder sb = makeQuerySQLAndParams(params, table, selectKey, whereKeys, whereValues);
        return queryForString(sb, params.toArray());
    }

    protected String queryForString(StringBuilder sb, Object... params) {
        String str = queryForObject(sb, String.class, params);
        return str == null ? "" : str;
    }

    protected <T> T queryForObject(StringBuilder sb, Class<T> class1, Object... params) {
        return queryForObject(sb.toString(), class1, params);
    }

    protected <T> T queryForObject(String sql, Class<T> class1, Object... params) {
        try {
            return jdbcTemplate.queryForObject(sql, class1, params);
        } catch (DataAccessException e) {
            handleDataAccessException(e);
            return null;
        }
    }

    public <T> T queryForEntity(StringBuilder sb, Class<T> class1, List<Object> paramList) {
        return queryForEntity(sb.toString(), class1, paramList.toArray());
    }

    public <T> T queryForEntity(StringBuilder sb, Class<T> class1, Object... params) {
        return queryForEntity(sb.toString(), class1, params);
    }

    public <T> T queryForEntity(String sql, Class<T> class1, Object... params) {
        List<T> list = queryForEntityList(sql, class1, params);
        return list.size() == 0 ? null : list.get(0);
    }

    protected List<Integer> queryForIntegerListNamed(StringBuilder sb, Map<String, Object> paramMap) {
        return namedQueryForList(sb, paramMap, Integer.class);
    }

    private <T> List<T> namedQueryForList(StringBuilder sb, Map<String, Object> paramMap, Class<T> class1) {
        try {
            return namedParameterJdbcTemplate.queryForList(sb.toString(), paramMap, class1);
        } catch (DataAccessException e) {
            handleDataAccessException(e);
            return new ArrayList<>();
        }
    }

    public <T> List<T> queryForEntityList(StringBuilder sb, Class<T> class1, List<Object> params) {
        return queryForEntityList(sb, class1, params.toArray());
    }

    protected void markCountStart(StringBuilder sb) {
        sb.append(COUNT_START);
    }

    /**
     * 标记count查询在原sql中的结尾位置。此方法不是必须调用，不调用则默认原sql的结尾即是count查询结尾
     */
    protected void markCountEnd(StringBuilder sb) {
        sb.append(COUNT_END);
    }

    public <T> List<T> queryForEntityList(StringBuilder sb, Class<T> class1, Object... params) {
        return jdbcTemplate.query(sb.toString(), new MyBeanPropertyRowMapper<>(class1), params);
    }

    public <T> List<T> queryForEntityListNamed(StringBuilder sb, Map<String, ?> paramMap, Class<T> class1) {
        return namedParameterJdbcTemplate.query(sb.toString(), paramMap, new MyBeanPropertyRowMapper<>(class1));
    }

    public <T> List<T> queryForEntityList(String sql, Class<T> class1, Object... params) {
        return jdbcTemplate.query(sql, new MyBeanPropertyRowMapper<>(class1), params);
    }

    protected List<Map<String, Object>> queryForList(StringBuilder sb, List<Object> params) {
        return queryForList(sb, params.toArray());
    }

    public List<Map<String, Object>> queryForList(StringBuilder sb, Object... params) {
        return jdbcTemplate.query(sb.toString(), params, new MyColumnMapRowMapper());
    }

    public List<String> queryForStringList(StringBuilder sb, List<Object> params) {
        return queryForStringList(sb, params.toArray());
    }

    public List<Integer> queryForIntList(StringBuilder sb, List<Object> params) {
        return queryForIntList(sb, params.toArray());
    }

    public List<Integer> queryForIntList(StringBuilder sb, Object... params) {
        return queryForSimpleObjectList(sb.toString(), Integer.class, params);
    }

    public List<String> queryForStringList(StringBuilder sb, Object... params) {
        return queryForSimpleObjectList(sb.toString(), String.class, params);
    }

    private <T> List<T> queryForSimpleObjectList(String sql, Class<T> class1, Object... params) {
        return jdbcTemplate.queryForList(sql, class1, params);
    }

    protected void where(StringBuilder sb, List<Object> params, String keyWithComma, Object value) {
        if (keyWithComma == null || keyWithComma.equals("")) {
            return;
        }
        // 检查where字段key-vlaue个数是否一致
        String[] keys = keyWithComma.split(",");
        Object[] values = null;
        if (value instanceof Object[]) {
            values = (Object[]) value;
        } else {
            values = new Object[]{value};
        }
        if (keys.length != values.length) {
            throw new RuntimeException("where字段的key与value个数不一致，key：" + keys.length + "，value：" + values.length);
        }

        sb.append(" where ");
        for (int i = 0; i < keys.length; i++) {
            if (values[i] instanceof String) {
                String val = ((String) values[i]).trim();
                if (val.startsWith(">") || val.startsWith("<") || val.startsWith("!")) {
                    sb.append(keys[i]).append(val);// TODO SQL注入风险
                } else {
                    sb.append(keys[i]).append(" = ? ");
                    params.add(values[i]);
                }
            } else {
                sb.append(keys[i]).append(" = ? ");
                params.add(values[i]);
            }
            if (i < keys.length - 1) {
                sb.append(" and ");
            }
        }
    }

    protected void handleDataAccessException(DataAccessException e) throws RuntimeException {
        if (e instanceof EmptyResultDataAccessException) {// 数据为空，不处理
        } else {
            throw new RuntimeException(e);
        }
    }

    public Object handleNullValue(Object obj, ResultSet rs, int index) throws SQLException {
        if (obj == null) {
            String columnClassName = rs.getMetaData().getColumnClassName(index);
            if (columnClassName.equals("java.lang.String")) {
                obj = "";
            } else if (columnClassName.equals("java.sql.Timestamp")) {
//				obj = "";
            } else if (columnClassName.equals("java.lang.Integer")) {
                obj = 0;
            } else if (columnClassName.equals("java.lang.Double")) {
                obj = 0;
            } else if (columnClassName.equals("java.lang.Long")) {
                obj = 0L;
            } else if (columnClassName.equals("java.lang.Boolean")) {
                obj = 0;// TODO 0还是false?
            }
        }
        return obj;
    }

    class MyColumnMapRowMapper extends ColumnMapRowMapper {
        @Override
        protected Object getColumnValue(ResultSet rs, int index) throws SQLException {
            Object obj = super.getColumnValue(rs, index);
            return handleNullValue(obj, rs, index);
        }
    }

    class MyBeanPropertyRowMapper<T> extends BeanPropertyRowMapper<T> {
        public MyBeanPropertyRowMapper(Class<T> class1) {
            super(class1);
        }

        @Override
        protected Object getColumnValue(ResultSet rs, int index, PropertyDescriptor pd) throws SQLException {
            Object obj = super.getColumnValue(rs, index, pd);
            return handleNullValue(obj, rs, index);
        }
    }

}
