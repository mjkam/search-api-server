package com.mjkam.search.api.ranking;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "search.consumer")
@Getter
@Setter
public class ConsumerConfiguration {
    private Integer size;
}
