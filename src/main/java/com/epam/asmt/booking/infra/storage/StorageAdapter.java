package com.epam.asmt.booking.infra.storage;

import com.epam.asmt.booking.domain.models.Provider;
import com.epam.asmt.booking.domain.models.Route;
import com.epam.asmt.booking.domain.ports.StoragePort;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StorageAdapter implements StoragePort {

    private final int MAX_PARALLEL_FETCH_POOLS = 3;

    private final RedisRepo routesRepo;
    private final ForkJoinPool pool = new ForkJoinPool(Math.max(MAX_PARALLEL_FETCH_POOLS, Provider.values().length));

    @Override
    public void saveProviderRoutes(Provider provider, Set<Route> routes) {
        routesRepo.saveProviderRoutes(provider, routes);
    }

    @Override
    public Set<Route> fetchDistinct(@Nullable String fromAirport, @Nullable String toAirport) {
        return pool.submit(() -> Arrays.stream(Provider.values())
                .parallel()
                .flatMap(p -> routesRepo.getProviderRoutes(p).stream())
                .filter(r -> fromAirport == null || r.sourceAirport().equals(fromAirport))
                .filter(r -> toAirport == null || r.destinationAirport().equals(toAirport))
                .collect(Collectors.toSet())
        ).join();
    }
}
