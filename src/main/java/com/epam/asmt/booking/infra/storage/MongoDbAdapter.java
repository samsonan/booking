package com.epam.asmt.booking.infra.storage;

import com.epam.asmt.booking.domain.models.Route;
import com.epam.asmt.booking.domain.ports.StoragePort;
import com.epam.asmt.booking.infra.entities.RouteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MongoDbAdapter implements StoragePort {

    private final RouteRepo routeRepo;
    private final RouteMapper mapper;

    @Override
    public void saveAll(Set<Route> routes) {
        routeRepo.saveAll(
                routes.stream().map(mapper::toDbEntity).toList()
        );
    }

    @Override
    public Set<Route> fetchDistinct() {
        return routeRepo.findAll().stream()
                .map(mapper::toDomainEntity)
                .collect(Collectors.toSet());
    }

}
