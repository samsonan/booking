package com.epam.asmt.booking.infra.entities;

import com.epam.asmt.booking.domain.models.Route;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RouteMapper {
    RouteResponse toResponse(Route src);
    Route toDomainEntity(RouteResponse src);
}
