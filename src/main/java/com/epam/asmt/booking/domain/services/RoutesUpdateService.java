package com.epam.asmt.booking.domain.services;

import com.epam.asmt.booking.domain.exception.RemoteServiceException;
import com.epam.asmt.booking.domain.ports.ProviderPort;
import com.epam.asmt.booking.domain.ports.StoragePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoutesUpdateService {

    private final List<ProviderPort> provider;
    private final StoragePort storagePort;

    @Scheduled(fixedDelayString = "${providers.fetch-delay-ms}")
    public void updateRoutes() {
        log.debug("Updating routes from providers: {}", provider);
        provider.forEach(p -> {
            try {
                var routes = p.fetchAllRoutes();
                storagePort.saveProviderRoutes(p.getProvider(), routes);
            } catch (RemoteServiceException e) {
                log.error("Error fetching routes: {}", e.getMessage());
            }
        });
    }

}
