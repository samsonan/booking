package com.epam.asmt.booking.domain.ports;

import com.epam.asmt.booking.domain.models.Provider;
import com.epam.asmt.booking.domain.models.Route;

import java.util.Set;

public interface ProviderPort {
    /**
     * Fetch all routes for given provider
     * @return set of routes
     */
    Set<Route> fetchAllRoutes();

    Provider getProvider();
}
