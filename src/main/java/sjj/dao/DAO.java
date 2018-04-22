package sjj.dao;

import org.springframework.stereotype.Repository;

@Repository
public class DAO extends Query {

    public <T> void update(T t) {
        Class<T> clazz = null;
        getMapper(clazz).updateByPrimaryKeySelective(t);
    }
}
