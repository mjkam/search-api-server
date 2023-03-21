package com.mjkam.search.external;

import com.mjkam.search.external.provider.ProviderType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "external.circuit")
@Getter
@Setter
public class ProviderCircuitConfiguration {
    private ProviderType main;
    private ProviderType fallback;
}
