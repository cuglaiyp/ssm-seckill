package com.lai.app.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.beans.PropertyVetoException;
import java.io.IOException;

@EnableTransactionManagement
@Configuration
@PropertySource("classpath:jdbc.properties")
@MapperScan("com.lai.app.dao")
public class DBConfig {

    @Value("${jdbc.driver}")
    private String driver;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.user}")
    private String user;

    @Value("${jdbc.password}")
    private String password;

    @Value("${c3p0.maxPoolSize}")
    private int maxPoolSize;

    @Value("${c3p0.minPoolSize}")
    private int minPoolSize;

    @Value("${c3p0.checkoutTimeout}")
    private int checkoutTimeout;

    @Value("${c3p0.acquireRetryAttempts}")
    private int acquireRetryAttempts;

    /**
     * 数据源
     * @return
     * @throws PropertyVetoException
     */
    @Bean
    public ComboPooledDataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driver);
        dataSource.setJdbcUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        // 最大连接数
        dataSource.setMaxPoolSize(maxPoolSize);
        // 最小连接数
        dataSource.setMinPoolSize(minPoolSize);
        // 获取连接的超时时间
        dataSource.setCheckoutTimeout(checkoutTimeout);
        // 获取连接失败的重试次数
        dataSource.setAcquireRetryAttempts(acquireRetryAttempts);
        return dataSource;
    }


    /**
     * mybatis的配置文件
     * @return
     */
    @Bean
    public org.apache.ibatis.session.Configuration mybatisConfig(){
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        // 使用jdbc的getGeneratedKeys获取数据库自增主键
        configuration.setUseGeneratedKeys(true);
        // 使用列别名替换列名，默认为true
        configuration.setUseColumnLabel(true);
        // 开启驼峰命名转换
        configuration.setMapUnderscoreToCamelCase(true);

        return configuration;
    }

    /**
     * spring整合mybatis
     * @param dataSource
     * @return
     * @throws IOException
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(ComboPooledDataSource dataSource, org.apache.ibatis.session.Configuration configuration) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfiguration(configuration);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.lai.app.entity");
        PathMatchingResourcePatternResolver classPathResource = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(classPathResource.getResources("mapper/*.xml"));
        return sqlSessionFactoryBean;
    }

    /**
     * 包扫描配置
     * 使用这个会出现这个警告
     * Cannot enhance @Configuration bean definition 'DBConfig' since its singleton instance has been created too early
     * 建议换成@MapperScan
     * @return
     */
/*    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        mapperScannerConfigurer.setBasePackage("com.lai.app.dao");
        return mapperScannerConfigurer;
    }*/
    /**
     * 事务管理器
     * @param dataSource
     * @return
     */
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(ComboPooledDataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
        return dataSourceTransactionManager;

    }


    //换mybatis
/*    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {    //配置ORM框架，这里为hibernate
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setShowSql(true);
        adapter.setGenerateDdl(false);
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
        return adapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter){   //配置实体管理器工厂
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setPackagesToScan("com.lai.app.entity");  //给定实体类的扫描范围，会扫描@Entity注解的类
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean factoryBean) {
        //配置事务管理器，依赖于实体管理器
        return new JpaTransactionManager(factoryBean.getObject());
    }*/
}
