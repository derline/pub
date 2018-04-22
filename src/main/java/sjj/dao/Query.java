package sjj.dao;

import org.springframework.stereotype.Repository;
import sjj.common.ContextRefreshedListener;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.util.List;

@Repository
public class Query {

    protected static <T> Mapper<T> getMapper(Class<T> entityClass) {
        return (Mapper<T>) ContextRefreshedListener.mapperMap.get(entityClass.getName());
    }

    public <T> T getById(Class<T> entityClass, Integer id) {
        return getMapper(entityClass).selectByPrimaryKey(id);
    }

    public <T> T getById(Class<T> entityClass, Integer id, String keys) {
        Example example = new Example(entityClass);
        example.selectProperties(keys.split(","));
        example.createCriteria().andEqualTo(EntityHelper.getPKColumns(entityClass).iterator().next().getEntityField().getName(), id);
        return getMapper(entityClass).selectOneByExample(example);
    }

    public <T> List<T> getListById(Class<T> entityClass, Integer id, String keys) {
        Example example = new Example(entityClass);
        example.selectProperties(keys.split(","));
        return getMapper(entityClass).selectByExample(example);
    }

}
