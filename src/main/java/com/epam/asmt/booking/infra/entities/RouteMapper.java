package com.epam.asmt.booking.infra.entities;

import com.epam.asmt.booking.domain.models.Route;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RouteMapper {

    @Mapping(target = "id", source="src", qualifiedByName = "generateId")
    @Mapping(target = "created", expression = "java(LocalDateTime.now())")
    MongoDbRoute toDbEntity(Route src);

    RouteResponse toResponse(Route src);

    Route toDomainEntity(MongoDbRoute src);
    Route toDomainEntity(RouteResponse src);

    @Named("generateId")
    static String generateId(RouteResponse src) {
        return String.valueOf(src.hashCode());
    }
}
