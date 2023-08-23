package com.epam.asmt.booking.domain.services;

import com.epam.asmt.booking.domain.ports.ProviderPort;
import com.epam.asmt.booking.domain.ports.StoragePort;
import com.epam.asmt.booking.infra.entities.RouteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoutesUpdateService {

    private final List<ProviderPort> provider;
    private final StoragePort storagePort;
    private final RouteMapper mapper;

    @Scheduled(fixedDelay = 10000)
    public void updateRoutes() {
        log.debug("Updating routes from providers: {}", provider);
        provider.forEach(p -> {
            var routes = p.fetchAllRoutes();
            storagePort.saveAll(routes);
        });
    }

}
