package com.epam.asmt.booking.infra.web.dto;

public record Route(
        String airline,
        String sourceAirport,
        String destinationAirport,
        String codeShare,
        int stops,
        String equipment) {
}
