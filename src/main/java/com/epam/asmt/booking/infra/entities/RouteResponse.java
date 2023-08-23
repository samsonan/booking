package com.epam.asmt.booking.infra.entities;

public record RouteResponse(
        String airline,
        String sourceAirport,
        String destinationAirport,
        String codeShare,
        int stops,
        String equipment) {
}
