package com.km.app.location;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
class GeoLocationService implements LocationService {

    private final RestTemplate restTemplate;

    @Override
    public String getCountryName(String ipAddress) {
        try {
            String url = "https://ipapi.co/" + ipAddress + "/country_name/";
            String countryName = restTemplate.getForObject(url, String.class);
            if (Objects.equals(countryName, "Undefined")) {
                log.warn("[GeoLocationService] No country name found for IP address: {}", ipAddress);
                return "Unknown";
            }
            return countryName;
        } catch (RestClientException e) {
            log.error("[GeoLocationService] Error fetching country name for IP address {}: {}", ipAddress, e.getMessage());
            throw e;
        }
    }
}
