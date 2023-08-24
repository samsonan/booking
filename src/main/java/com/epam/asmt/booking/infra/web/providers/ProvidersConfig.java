package com.epam.asmt.booking.infra.web.providers;

import com.epam.asmt.booking.domain.models.Provider;
import com.epam.asmt.booking.domain.ports.ProviderPort;
import com.epam.asmt.booking.infra.entities.RouteMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ProvidersConfig {

    @Bean
    public ProviderPort provider1(WebClient providerWebClient, RouteMapper mapper,
                                  @Value("${providers.provider1}") String requestPath) {
        return new BaseProvider(providerWebClient, mapper, requestPath, Provider.PROVIDER_1);
    }

    @Bean
    public ProviderPort provider2(WebClient providerWebClient, RouteMapper mapper,
                                  @Value("${providers.provider2}") String requestPath) {
        return new BaseProvider(providerWebClient, mapper, requestPath, Provider.PROVIDER_2);
    }


}
