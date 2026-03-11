package com.sentura.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.sentura.backend.dto.CountryDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService {

    private final RestTemplate restTemplate;

    public CountryService() {
        this.restTemplate = new RestTemplate();
    }

    @Cacheable("countries")
    public List<CountryDTO> getAllCountries() {
        String url = "https://restcountries.com/v3.1/all?fields=name,capital,region,population,flags";
        JsonNode[] response = restTemplate.getForObject(url, JsonNode[].class);
        List<CountryDTO> countries = new ArrayList<>();

        if (response != null) {
            for (JsonNode node : response) {
                String name = node.has("name") && node.get("name").has("common") ? node.get("name").get("common").asText() : "";
                String capital = node.has("capital") && node.get("capital").isArray() && !node.get("capital").isEmpty() ? node.get("capital").get(0).asText() : "";
                String region = node.has("region") ? node.get("region").asText() : "";
                long population = node.has("population") ? node.get("population").asLong() : 0;
                String flag = node.has("flags") && node.get("flags").has("svg") ? node.get("flags").get("svg").asText() : "";

                countries.add(CountryDTO.builder()
                        .name(name)
                        .capital(capital)
                        .region(region)
                        .population(population)
                        .flag(flag)
                        .build());
            }
        }
        return countries;
    }
}
