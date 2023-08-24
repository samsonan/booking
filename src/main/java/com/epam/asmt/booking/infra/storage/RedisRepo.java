package com.epam.asmt.booking.infra.storage;

import com.epam.asmt.booking.domain.models.Provider;
import com.epam.asmt.booking.domain.models.Route;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisRepo {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String HASH_KEY_ROUTES = "routes";
    private static final String HASH_KEY_HASH = "hash";

    private final StringRedisTemplate redisTemplate;

    public Set<Route> getProviderRoutes(Provider provider) {
        try {
            HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
            var cacheVal = hashOps.get(provider.getKey(), HASH_KEY_ROUTES);
            return cacheVal == null ? Collections.emptySet() : MAPPER.readValue(cacheVal, new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error getting routes from cache: {}", e.getMessage());
            return Collections.emptySet();
        }
    }

    public void saveProviderRoutes(Provider provider, Set<Route> routes) {
        try {
            HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
            var providerName = provider.getKey();

            var routesHash = hashOps.get(providerName, HASH_KEY_HASH);
            if (routesHash == null || Integer.parseInt(routesHash) != routes.hashCode()) {
                hashOps.put(providerName, HASH_KEY_ROUTES, MAPPER.writeValueAsString(routes));
                hashOps.put(providerName, HASH_KEY_HASH, String.valueOf(routes.hashCode()));
            }
        } catch (Exception e) {
            log.error("Error saving routes to cache: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
