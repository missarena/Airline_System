package com.example.location_service.service;

import com.example.payload.request.cityRequest;
import com.example.payload.response.cityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CityService {

     cityResponse createCity(cityRequest request);
     cityResponse getCityById(Long id);
     cityResponse updateCity(Long id,cityRequest request);
     void deleteCity(Long id);
     Page<cityResponse> getAllCities(Pageable pageable);
     Page<cityResponse> searchCities(String keyword,Pageable pageable);

      boolean cityExists(String cityCode);
     // boolean validateCityCode(String cityCode);
      Page<cityResponse>getCitiesByCountryCode(String countryCode,Pageable pageable);

}
