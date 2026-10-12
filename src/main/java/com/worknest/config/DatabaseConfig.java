package com.worknest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url:}")
    private String configuredUrl;

    @Value("${spring.datasource.username:postgres}")
    private String defaultUsername;

    @Value("${spring.datasource.password:postgres}")
    private String defaultPassword;

    @Bean
    @Primary
    public DataSource dataSource() {
        String databaseUrl = System.getenv("DATABASE_URL");
        if (databaseUrl == null || databaseUrl.isBlank()) {
            databaseUrl = configuredUrl;
        }

        // Handle postgres:// or postgresql:// URLs typically provided by Render, Supabase, Neon
        if (databaseUrl != null && (databaseUrl.startsWith("postgres://") || databaseUrl.startsWith("postgresql://"))) {
            try {
                URI uri = new URI(databaseUrl);
                String host = uri.getHost();
                int port = uri.getPort() == -1 ? 5432 : uri.getPort();
                String path = uri.getPath(); // e.g. /worknest_db
                String userInfo = uri.getUserInfo();

                String username = defaultUsername;
                String password = defaultPassword;

                if (userInfo != null && userInfo.contains(":")) {
                    String[] parts = userInfo.split(":", 2);
                    username = parts[0];
                    password = parts[1];
                }

                String jdbcUrl = "jdbc:postgresql://" + host + ":" + port + path;

                return DataSourceBuilder.create()
                        .driverClassName("org.postgresql.Driver")
                        .url(jdbcUrl)
                        .username(username)
                        .password(password)
                        .build();
            } catch (URISyntaxException e) {
                // Fall back to standard builder if URI parsing fails
            }
        }

        // Standard JDBC URL
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url(databaseUrl != null && !databaseUrl.isBlank() ? databaseUrl : "jdbc:postgresql://localhost:5432/worknest_db")
                .username(defaultUsername)
                .password(defaultPassword)
                .build();
    }
}
