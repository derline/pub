package sjj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sjj.dao.DAO;
import sjj.dao.Query;

/**
 * query的薄层封装
 * 不对dao进行封装
 */
@Service
public class BaseService {
    @Autowired
    protected Query query;
    @Autowired
    protected DAO dao;

    protected <T> T getById(Class<T> clazz, int i) {
        return query.getById(clazz, i);
    }

}
