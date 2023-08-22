package com.epam.asmt.booking.infra.web;

import com.epam.asmt.booking.infra.web.dto.Route;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProviderAdapter { // TODO per provider?

    private final WebClient providerWebClient;

    public Set<Route> fetchAllRoutes() {
        try {
            return providerWebClient
                    .get()
                    .uri("https://zretmlbsszmm4i35zrihcflchm0ktwwj.lambda-url.eu-central-1.on.aws/provider/flights2") // TODO: cfg
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Set<Route>>() {})
                    .block();
        } catch (Exception ex) {
            log.error("Failed to get routes.", ex); // TODO
            return Collections.emptySet();
        }
    }
}
