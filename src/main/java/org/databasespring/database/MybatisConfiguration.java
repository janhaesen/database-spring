package org.databasespring.database;

import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.TransactionManager;

@Configuration
public class MybatisConfiguration implements DataSourceFactory {

    private static final Logger LOG = LogManager.getLogger(MybatisConfiguration.class);

    private Properties properties;

    private final ResourceLoader resourceLoader;

    public MybatisConfiguration(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setProperties(Properties props) {
        this.properties = props;
    }

    @Override
    public DataSource getDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(properties.getProperty("driver"));
        hikariDataSource.setJdbcUrl(properties.getProperty("url"));
        hikariDataSource.setUsername(properties.getProperty("username"));
        hikariDataSource.setPassword(properties.getProperty("password"));
        hikariDataSource.setConnectionTimeout(30000L); // Set default timeout to 30 seconds, this is already long
        return hikariDataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(resourceLoader.getResource("classpath:database/mybatis/mybatis-config.xml"));
        try {
            sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:database/mybatis/mappers/*.xml"));
        } catch (IOException e) {
            LOG.error("Unable to set mapper locations, continuing until better strategy is available. This will mean queries can't be found", e);
        }
        return sqlSessionFactoryBean;
    }

    @Bean
    public TransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new JdbcTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

}
