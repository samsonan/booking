package com.epam.asmt.booking.infra.web;

import com.epam.asmt.booking.domain.ports.StoragePort;
import com.epam.asmt.booking.infra.entities.RouteMapper;
import com.epam.asmt.booking.infra.entities.RouteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private final StoragePort storagePort;
    private final RouteMapper mapper;

    @GetMapping("routes")
    public Set<RouteResponse> routes(@RequestParam(name = "from", required = false) String fromAirport,
                                     @RequestParam(name = "to", required = false) String toAirport) {
        return storagePort.fetchDistinctRoutes(fromAirport, toAirport).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toSet());
    }
}
