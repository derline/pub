package sjj.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BaseDAO extends BaseQuery {
	protected int update(StringBuilder sb, Object... params) {
		try {
			return jdbcTemplate.update(sb.toString(), params);
		} catch (DataAccessException e) {
			handleDataAccessException(e);
			return 0;
		}
	}

	public int update(String table, String whereKeyWithComma, Object whereValue, String setKeysWithComma, Object... setValues) {
		String[] whereKeys = whereKeyWithComma.split(",");
		Object[] whereValues = null;
		if (whereValue instanceof Object[]) {
			whereValues = (Object[]) whereValue;
		} else {
			whereValues = new Object[] { whereValue };
		}
		// 检查where字段key-vlaue个数是否一致
		if (whereKeys.length != whereValues.length) {
			throw new RuntimeException("where字段的key与value个数不一致，key：" + whereKeys.length + "，value：" + whereValues.length);
		}
		// 检查set字段key-vlaue个数是否一致
		String[] keys = setKeysWithComma.split(",");
		if (keys.length != setValues.length) {
			throw new RuntimeException("set字段的key与value个数不一致，key：" + keys.length + "，value：" + setValues.length);
		}

		List<Object> params = new ArrayList<Object>();

		StringBuilder sb = new StringBuilder(" update ").append(table).append(" set ");
		// key1 = ?,key2 = ?,{value1,value2}
		for (int i = 0; i < keys.length; i++) {
			sb.append((String) keys[i]).append(" = ? ");
			if (i < keys.length - 1) {
				sb.append(" , ");
			}
			params.add(setValues[i]);
		}

		sb.append(" where ");
		// whereKey1 = ? and whereKey2 = ?
		for (int i = 0; i < whereKeys.length; i++) {
			sb.append(whereKeys[i]).append(" = ? ");
			if (i < whereKeys.length - 1) {
				sb.append(" and ");
			}
			params.add(whereValues[i]);
		}

		return jdbcTemplate.update(sb.toString(), params.toArray());
	}

	public int delete(String table, String whereKeyWithComma, Object... whereValues) {
		List<Object> params = new ArrayList<Object>();

		StringBuilder sb = new StringBuilder(" delete from " + table);
		where(sb, params, whereKeyWithComma, whereValues);
		return update(sb, params.toArray());
	}

}
