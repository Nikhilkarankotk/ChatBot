package com.portfolio.chatbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
public class DatabaseConfig {
    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(new DatabasePopulator() {
            @Override
            public void populate(Connection connection) throws SQLException {
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute("CREATE EXTENSION IF NOT EXISTS vector");
                }
            }
        });
        return initializer;
    }
}
