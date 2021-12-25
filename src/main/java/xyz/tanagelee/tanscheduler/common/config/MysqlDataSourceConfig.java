/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * MysqlDataSourceConfig
 *
 * @author liyunjun
 * @date 2021/12/25 22:21
 */
@Configuration
@MapperScan(basePackages = "xyz.tanagelee.**.mapper", sqlSessionTemplateRef = "mysqlSqlSessionTemplate")
public class MysqlDataSourceConfig {

    /**
     * 创建数据源
     *
     * @return
     */

    @Bean("mysqlDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    //注2：此处spring.datasource.national对应application.properties的数据库对应配置
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().build();
    }


    /**
     * 创建SqlSessionFactory
     *
     * @param dataSource
     * @return
     * @throws Exception
     */

    @Bean(name = "mysqlSqlSessionFactory")
    public SqlSessionFactory mysqlSqlSessionFactory(@Qualifier("mysqlDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(mysqlDataSource());
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*Mapper.xml"));
        factoryBean.setTypeAliasesPackage("xyz.tanagelee.**.**");
        // 关键代码 设置 MyBatis-Plus 分页插件
        Interceptor[] plugins = new Interceptor[1];
        plugins[0] = mybatisPlusInterceptorMysql();
        factoryBean.setPlugins(plugins);
        return factoryBean.getObject();
    }


    /**
     * 创建管理器
     *
     * @param dataSource
     * @return
     */

    @Bean(name = "mysqlTransactionManager")
    @Qualifier("mysqlTransactionManager")
    @Primary
    public DataSourceTransactionManager mysqlTranscationManager(@Qualifier("mysqlDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    /**
     * 创建模板
     *
     * @param sqlSessionFactory
     * @return
     * @throws Exception
     */

    @Bean(name = "mysqlSqlSessionTemplate")
    public SqlSessionTemplate mysqlSqlSessionTemplate(@Qualifier("mysqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptorMysql() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}