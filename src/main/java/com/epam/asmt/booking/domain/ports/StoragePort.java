package com.epam.asmt.booking.domain.ports;

import com.epam.asmt.booking.domain.models.Route;

import java.util.Set;

public interface StoragePort {
    void saveAll(Set<Route> routes);

    Set<Route> fetchDistinct();
}
