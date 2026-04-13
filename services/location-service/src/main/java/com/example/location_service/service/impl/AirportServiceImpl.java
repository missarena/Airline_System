package com.example.location_service.service.impl;

import com.example.location_service.Mapper.AirportMapper;
import com.example.location_service.Repository.AirportRepository;
import com.example.location_service.Repository.CityRepository;
import com.example.location_service.model.Airport;
import com.example.location_service.model.City;
import com.example.location_service.service.AirportService;
import com.example.payload.request.AirportRequest;
import com.example.payload.response.AirportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;

    @Override
    public AirportResponse createAirport(AirportRequest request)  throws Exception{
        if(airportRepository.findByIataCode(request.getIataCode()).isPresent()){
           throw new Exception("Airport with Iata Code already exist");
        }
        City city=cityRepository.findById(request.getCityId())
                .orElseThrow(()->new Exception("City  not found"));
                Airport airport= AirportMapper.toEntity(request);
                airport.setCity(city);
             Airport savedAirport= airportRepository.save(airport);
        return AirportMapper.toresponse(savedAirport);
    }

    @Override
    public AirportResponse getAirportById(Long id) throws Exception {
        Airport airport=airportRepository.findById(id).orElseThrow(
                ()->new Exception("airport not exist with proovidded id")
        );
        return AirportMapper.toresponse(airport);
    }

    @Override
    public List<AirportResponse> getAllAirports() {
        return airportRepository.findAll().stream()
                .map(AirportMapper::toresponse)
                .collect(Collectors.toList());
    }

    @Override
    public AirportResponse updateAirport(Long id, AirportRequest request) throws Exception {
        Airport existingAirport=airportRepository.findById(id).orElseThrow(
            ()->new Exception("airport not exist with proovidded id"+id)
              );
        if(request.getIataCode()!=null
                && !existingAirport.getIataCode().equals(request.getIataCode())
            && airportRepository.findByIataCode(request.getIataCode()).isPresent()){
          throw new Exception("Airport with IAta code already exist");
        }
        AirportMapper.updateEntity(request,existingAirport);
        Airport updatedAirport=airportRepository.save(existingAirport);
        return AirportMapper.toresponse(updatedAirport);
        //return null;
    }

    @Override
    public void deleteAirport(Long id) throws Exception {
        Airport airport=airportRepository.findById(id).orElseThrow(
                ()->new Exception("airport not exist with proovidded id"+id)
        );
        airportRepository.delete(airport);
    }

    @Override
    public List<AirportResponse> getAirportByCityId(Long cityId) {
        return airportRepository.findByCityId(cityId).stream()
                .map(AirportMapper::toresponse)
                .collect(Collectors.toList());
    }
}
