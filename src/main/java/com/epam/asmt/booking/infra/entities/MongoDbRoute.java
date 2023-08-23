package com.epam.asmt.booking.infra.entities;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Document("routes")
public record MongoDbRoute(
        @MongoId String id,
        String airline,
        String sourceAirport,
        String destinationAirport,
        String codeShare,
        int stops,
        String equipment,
        @Indexed(expireAfterSeconds=60) LocalDateTime created) {
}
