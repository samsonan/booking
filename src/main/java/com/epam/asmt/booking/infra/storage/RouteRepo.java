package com.epam.asmt.booking.infra.storage;

import com.epam.asmt.booking.infra.entities.MongoDbRoute;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RouteRepo extends MongoRepository<MongoDbRoute, String> {

    @Override
    List<MongoDbRoute> findAll();

    @Override
    <S extends MongoDbRoute> List<S> saveAll(Iterable<S> entities);
}
