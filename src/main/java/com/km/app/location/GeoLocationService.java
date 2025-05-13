package com.km.app.location;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
class GeoLocationService implements LocationService {

    private final RestTemplate restTemplate;

    @Override
    public String getCountryName(String ipAddress) {
        try {
            String url = "https://ipapi.co/" + ipAddress + "/country_name/";
            return restTemplate.getForObject(url, String.class);
        } catch (RestClientException e) {
            // TODO handle exception
            log.error("Error fetching country name for IP address {}: {}", ipAddress, e.getMessage());
            return "Unknown";
        }
    }
}
