package sjj.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
    public static Map<String, Object> mapperMap = new HashMap<>();
    private static final Log logger = LogFactory.getLog(ContextRefreshedListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 根容器为Spring容器
        if (event.getApplicationContext().getParent() == null) {
            Map<String, Object> map = new HashMap<>();
            Map<String, Mapper> beans = event.getApplicationContext().getBeansOfType(Mapper.class);
            for (Object bean : beans.values()) {
                Class<?> proxyMapperClass = bean.getClass();//例：实现UserMapper接口的代理类
                Type type = proxyMapperClass.getGenericInterfaces()[0];//例：UserMapper接口描述
                try {
                    Class<?> mapperInterface = Class.forName(type.getTypeName());//例：UserMapper接口
                    Type mapperType = mapperInterface.getGenericInterfaces()[0];//Mapper接口描述
                    Type type1 = ((ParameterizedType) mapperType).getActualTypeArguments()[0];//获得Mapper接口的泛型参数实际类型描述
                    map.put(type1.getTypeName(), bean);//例：{"rmjk.bean.User", bean}
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    logger.info("mappers collect failed: " + mapperMap);
                }
            }
            mapperMap = Collections.unmodifiableMap(map);
            logger.info("mappers collected：" + mapperMap.size());
        }
    }
}