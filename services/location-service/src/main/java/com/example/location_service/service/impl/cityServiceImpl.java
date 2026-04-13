package com.example.location_service.service.impl;


import com.example.location_service.Mapper.CityMapper;
import com.example.location_service.Repository.CityRepository;
import com.example.location_service.model.City;
import com.example.location_service.service.CityService;
import com.example.payload.request.cityRequest;
import com.example.payload.response.cityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class cityServiceImpl implements CityService {

    CityRepository cityRepository;

    @Override
    public cityResponse createCity(cityRequest request) {

        if (cityRepository.existsByCityCode(request.getCityCode())) {
            throw new RuntimeException("City with given code already exists");
        }

        City city = CityMapper.toEntity(request);
        City result = cityRepository.save(city);

        return CityMapper.toResponse(result);
    }

    @Override
    public cityResponse getCityById(Long id) {
        City city=cityRepository.findById(id).orElseThrow(
                ()->new RuntimeException("city not exists with given id")
        );
        return CityMapper.toResponse(city);
    }

    @Override
    public cityResponse updateCity(Long id, cityRequest request) {

        City city = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not exists with given id"));

        if (cityRepository.existsByCityCode(request.getCityCode())
                && !city.getCityCode().equals(request.getCityCode())) {
            throw new RuntimeException("City with given code already exists");
        }

        City updatedCity = cityRepository.save(
                CityMapper.updateEntity(city, request)
        );

        return CityMapper.toResponse(updatedCity);
    }


    @Override
    public void deleteCity(Long id) {
        City city = cityRepository.findById(id).orElseThrow(
                () -> new RuntimeException("City not exists with given id")
        );
        cityRepository.delete(city);
    }

    @Override
    public Page<cityResponse> getAllCities(Pageable pageable) {

        return cityRepository.findAll(pageable).map(CityMapper::toResponse);
    }

    @Override
    public Page<cityResponse> searchCities(String keyword, Pageable pageable) {
        return cityRepository.searchByKeyword(keyword,pageable).map(CityMapper::toResponse);
    }

    @Override
    public boolean cityExists(String cityCode) {
        return false;
    }

   @Override
   public Page<cityResponse> getCitiesByCountryCode(String countryCode,Pageable pageable){

        return cityRepository.findByCountryCodeIgnoreCase(countryCode,pageable).map(CityMapper::toResponse);
   }
}
