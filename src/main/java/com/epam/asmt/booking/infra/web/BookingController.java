package com.epam.asmt.booking.infra.web;

import com.epam.asmt.booking.infra.web.dto.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private final ProviderAdapter provider;

    @GetMapping("routes")
    public Set<Route> routeList() {
        return provider.fetchAllRoutes();
    }
}
