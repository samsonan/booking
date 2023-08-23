package com.epam.asmt.booking.domain.models;

public record Route(
        String airline,
        String sourceAirport,
        String destinationAirport,
        String codeShare,
        int stops,
        String equipment) {
}
