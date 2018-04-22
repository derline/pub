package sjj.sjj.config;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    @Bean
    public Dao getDao(@Qualifier("dataSource") DataSource dataSource) {
        return new NutDao(dataSource);
    }
}
