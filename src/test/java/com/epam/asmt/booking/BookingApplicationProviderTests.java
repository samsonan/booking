package com.epam.asmt.booking;

import com.epam.asmt.booking.domain.models.Provider;
import com.epam.asmt.booking.domain.models.Route;
import com.epam.asmt.booking.domain.ports.ProviderPort;
import com.epam.asmt.booking.domain.ports.StoragePort;
import com.epam.asmt.booking.domain.services.RoutesUpdateService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
class BookingApplicationProviderTests {

	@Autowired
	private List<ProviderPort> providerPort;

	@MockBean
	private RoutesUpdateService mockService;

	private static final MockWebServer mockWebServer;

	static {
		GenericContainer<?> redis =
				new GenericContainer<>(DockerImageName.parse("redis:5.0.3-alpine")).withExposedPorts(6379);
		redis.start();
		System.setProperty("spring.data.redis.host", redis.getHost());
		System.setProperty("spring.data.redis.port", redis.getMappedPort(6379).toString());

		mockWebServer = new MockWebServer();
		var baseUrl = mockWebServer.url("").toString();
		System.setProperty("providers.provider1", baseUrl);
		System.setProperty("providers.provider2", baseUrl);
		System.clearProperty("providers.fetch-delay-ms");
	}

	@Test
	void testDataIsFetchedFromProviders() {
		// given
		var firstProvider = providerPort.stream().filter(p -> p.getProvider() == Provider.PROVIDER_1).findFirst()
				.orElseThrow(() -> new RuntimeException("Provider 1 port not found"));

		mockWebServer.enqueue(newMockResponse("""
			[
			   {
				  "codeShare":"A",
				  "sourceAirport":"PRG",
				  "equipment":"A",
				  "stops":0,
				  "airline":"EU",
				  "destinationAirport":"MAD"
			   },
			   {
				  "codeShare":"",
				  "sourceAirport":"MAD",
				  "equipment":"B",
				  "stops":1,
				  "airline":"US",
				  "destinationAirport":"PRG"
			   }
			]
			"""));

		// when
		var actualResult = firstProvider.fetchAllRoutes();

		// then
		Assertions.assertEquals(Provider.PROVIDER_1, firstProvider.getProvider());

		var route1 = new Route("EU", "PRG", "MAD", "A", 0, "A");
		var route2 = new Route("US", "MAD", "PRG", "", 1, "B");

		Assertions.assertEquals(Set.of(route1, route2), actualResult);
	}

	@NotNull
	private MockResponse newMockResponse(String json) {
		return new MockResponse().setBody(json).setHeader("Content-Type", "application/json");
	}

	public static WebClient createTestWebClient(String basePath) {
		return WebClient.builder().baseUrl(basePath).build();
	}
}
