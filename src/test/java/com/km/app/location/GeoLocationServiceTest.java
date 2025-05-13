package com.km.app.location;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class GeoLocationServiceTest {

    private static final String IP_ADDRESS = "8.8.8.8";

    @Mock
    private RestTemplate restTemplate;

    private GeoLocationService geoLocationService;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        geoLocationService = new GeoLocationService(restTemplate);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void shouldReturnCountryNameWhenApiReturnsValidResponse() {
        // given
        String expectedCountry = "United States";
        when(restTemplate.getForObject(anyString(), any())).thenReturn(expectedCountry);

        // when
        String actualCountry = geoLocationService.getCountryName(IP_ADDRESS);

        // then
        assertEquals(expectedCountry, actualCountry);
    }

    @Test
    void shouldReturnUnknownWhenApiThrowsException() {
        // given
        when(restTemplate.getForObject(anyString(), any())).thenThrow(new RestClientException("API error"));

        // when
        String actualCountry = geoLocationService.getCountryName(IP_ADDRESS);

        // then
        assertEquals("Unknown", actualCountry);
    }
}