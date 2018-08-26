package com.intellias.greeting.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "weather")
public class WeatherServiceConfig {

    private String token;

    private String url;

    private String coordinates;

    public String getUrl() {
        return url +
                token +
                "/" +
                coordinates;

    }

}
