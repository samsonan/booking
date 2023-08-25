package com.epam.asmt.booking;

import com.epam.asmt.booking.domain.models.Provider;
import com.epam.asmt.booking.domain.models.Route;
import com.epam.asmt.booking.domain.ports.StoragePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Set;

@SpringBootTest
class BookingApplicationStorageTests {

	static {
		GenericContainer<?> redis =
				new GenericContainer<>(DockerImageName.parse("redis:5.0.3-alpine")).withExposedPorts(6379);
		redis.start();
		System.setProperty("spring.data.redis.host", redis.getHost());
		System.setProperty("spring.data.redis.port", redis.getMappedPort(6379).toString());

		System.clearProperty("providers.fetch-delay-ms");
	}

	@Autowired
	private StoragePort storagePort;

	@Test
	void testRoutesSavedAndRetrieved() {
		// given
		var route1 = new Route("EU", "PRG", "MAD", "", 0, "A");
		var route2 = new Route("US", "MAD", "PRG", "", 1, "B");
		var route3 = new Route("BE", "SFR", "PAR", "AS", 3, "C");

		// when
		storagePort.saveProviderRoutes(Provider.PROVIDER_1, Set.of(route1, route2));
		storagePort.saveProviderRoutes(Provider.PROVIDER_2, Set.of(route2, route3));

		var actualResult = storagePort.fetchDistinctRoutes(null, null);

		// then - order is ignored
		Assertions.assertEquals(Set.of(route1, route3, route2), actualResult);
	}

	@Test
	void testRoutesUpdatedOnSave() {
		// given
		var route1 = new Route("EU", "PRG", "MAD", "", 0, "A");
		var route2 = new Route("US", "MAD", "PRG", "", 1, "B");
		var route3 = new Route("BE", "SFR", "PAR", "AS", 3, "C");

		// when
		storagePort.saveProviderRoutes(Provider.PROVIDER_1, Set.of(route1, route2));
		storagePort.saveProviderRoutes(Provider.PROVIDER_1, Set.of(route2, route3));

		var actualResult = storagePort.fetchDistinctRoutes(null, null);

		// then - order is ignored
		Assertions.assertEquals(Set.of(route2, route3), actualResult);
	}

}
