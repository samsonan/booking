package com.epam.asmt.booking.infra.web.providers;

import com.epam.asmt.booking.domain.models.Route;
import com.epam.asmt.booking.domain.ports.ProviderPort;
import com.epam.asmt.booking.infra.entities.RouteMapper;
import com.epam.asmt.booking.infra.entities.RouteResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class BaseProvider implements ProviderPort {

    private final WebClient providerWebClient;
    private final RouteMapper mapper;
    private final String requestPath;
    private final String providerName;

    public Set<Route> fetchAllRoutes() {
        try {
            var routes = providerWebClient
                    .get()
                    .uri(requestPath)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Set<RouteResponse>>() { })
                    .block();
            return routes == null ? Collections.emptySet() :
                    routes.stream().map(mapper::toDomainEntity).collect(Collectors.toSet());
        } catch (Exception ex) {
            log.error("Failed to get routes from [{}]: {}", providerName, ex.getMessage());
            return Collections.emptySet();
        }
    }
}
