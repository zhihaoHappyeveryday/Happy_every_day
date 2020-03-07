package com.zhihao.service.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @Author: zhihao
 * @Date: 7/3/2020 下午 10:07
 * @Description: 使用配置类方式
 * @Versions 1.0
 **/
@Configuration
@PropertySource("classpath:jdbcConfig.properties") //加载配置文件
@ComponentScan(basePackages = {"com.zhihao.service"}) //包扫描)
@EnableTransactionManagement //启用注解事务
public class SpringAndMybatisConfig  {

    @Value("${jdbc.driverClassName}")
    private String driverClassName;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    /**
     * 创建druid数据源
     *
     * @return javax.sql.DataSource
     * @author: zhihao
     * @date: 7/3/2020
     */
    @Bean(name = "dataSource")
    public DataSource dataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/mall?useUnicode=true&characterEncoding=utf-8");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
        //其他配置使用默认
        return druidDataSource;
    }

    /**
     *  创建spring管理的SqlSessionFactoryBean
     *
     * @return org.mybatis.spring.SqlSessionFactoryBean
     * @author: zhihao
     * @date: 7/3/2020
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        //包扫描
        sqlSessionFactoryBean.setTypeAliasesPackage("com.zhihao.entity");
        // 添加mapper 扫描路径
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "mappers/*.xml";
        sqlSessionFactoryBean.setMapperLocations(pathMatchingResourcePatternResolver.getResources(packageSearchPath));
        //设置核心配置文件
        String  path = "classpath:SqlMapConfig.xml";
        sqlSessionFactoryBean.setConfigLocation(pathMatchingResourcePatternResolver.getResource(path));
        return sqlSessionFactoryBean;
    }



    /**
     * 创建MapperScannerConfigurer
     *
     * @return org.mybatis.spring.mapper.MapperScannerConfigurer
     * @author: zhihao
     * @date: 7/3/2020
     * {@link #}
     */
    @Bean(name = "scannerConfigurer")
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("com.zhihao.dao");
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return configurer;
    }

    /** 
     * 事务管理器
     *
     * @param  
     * @return org.springframework.jdbc.datasource.DataSourceTransactionManager 
     * @author: zhihao
     * @date: 7/3/2020 
     * {@link #}
     */
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }
}
