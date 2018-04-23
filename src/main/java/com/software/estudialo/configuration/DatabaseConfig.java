/**
 * 
 */
package com.software.estudialo.configuration;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

// TODO: Auto-generated Javadoc
/**
 * The Class DatabaseConfig.
 *
 * @author LUIS
 */
@Configuration
@PropertySource({ "classpath:application.properties" })
public class DatabaseConfig {
    
    /**
     * Data source.
     *
     * @return the data source
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
}
