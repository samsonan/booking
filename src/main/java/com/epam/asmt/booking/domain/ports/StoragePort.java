package com.epam.asmt.booking.domain.ports;

import com.epam.asmt.booking.domain.models.Provider;
import com.epam.asmt.booking.domain.models.Route;
import org.springframework.lang.Nullable;

import java.util.Set;

public interface StoragePort {

    void saveProviderRoutes(Provider provider, Set<Route> routes);

    Set<Route> fetchDistinct(@Nullable String fromAirport, @Nullable String toAirport);
}
