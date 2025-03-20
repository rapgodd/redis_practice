package com.giyeon.redis_practice.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource(){
        HikariConfig hc = new HikariConfig();
        hc.setJdbcUrl("jdbc:mysql://redis-practice:3306/mydb");
        hc.setUsername("root");
        hc.setPassword("password");
        hc.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hc.setMaximumPoolSize(20);
        hc.setMinimumIdle(20);
        return new HikariDataSource(hc);
    }




}
