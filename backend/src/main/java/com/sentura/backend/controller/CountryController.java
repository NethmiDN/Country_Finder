package com.sentura.backend.controller;

import com.sentura.backend.dto.CountryDTO;
import com.sentura.backend.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/countries")
@CrossOrigin(origins = "*") // Allow localhost frontend
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public List<CountryDTO> getCountries(@RequestParam(required = false) String search) {
        List<CountryDTO> all = countryService.getAllCountries();
        
        if (search == null || search.trim().isEmpty()) {
            return all;
        }
        
        String lowerCaseSearch = search.toLowerCase();
        return all.stream()
                .filter(c -> (c.getName() != null && c.getName().toLowerCase().contains(lowerCaseSearch)) ||
                             (c.getCapital() != null && c.getCapital().toLowerCase().contains(lowerCaseSearch)) ||
                             (c.getRegion() != null && c.getRegion().toLowerCase().contains(lowerCaseSearch)))
                .collect(Collectors.toList());
    }
}
