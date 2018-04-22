package sjj.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "sjj.dao.mapper")
public class MybatisConfig {
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // mapper类和xml在同一目录的话，不用设置MapperLocations
        sessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/*.xml"));

        //// 打印sql插件
        //SqlInterceptor sqlInterceptor = new SqlInterceptor();
        //
        //sessionFactoryBean.setPlugins(new Interceptor[] {  sqlInterceptor });

        return sessionFactoryBean.getObject();
    }
}
