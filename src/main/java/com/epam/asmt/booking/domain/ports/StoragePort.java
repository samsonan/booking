package com.epam.asmt.booking.domain.ports;

import com.epam.asmt.booking.domain.models.Provider;
import com.epam.asmt.booking.domain.models.Route;
import org.springframework.lang.Nullable;

import java.util.Set;

public interface StoragePort {

    /**
     * Save the routes of given provider into some storage.
     */
    void saveProviderRoutes(Provider provider, Set<Route> routes);

    Set<Route> fetchDistinctRoutes(@Nullable String fromAirport, @Nullable String toAirport);
}
