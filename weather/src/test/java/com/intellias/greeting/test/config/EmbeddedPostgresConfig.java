package com.intellias.greeting.test.config;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@TestConfiguration
public class EmbeddedPostgresConfig {

    @Bean(destroyMethod = "close")
    @Primary
    public EmbeddedPostgres createEmbeddedPostgres() throws IOException {
        return EmbeddedPostgres
                .builder()
                .start();
    }

    @Bean
    @Primary
    public DataSource dataSource(EmbeddedPostgres embeddedPostgres) {
        Map<String, String> props = new HashMap<>();
        props.put("stringtype", "unspecified");
        return embeddedPostgres.getTemplateDatabase(props);
    }
}
