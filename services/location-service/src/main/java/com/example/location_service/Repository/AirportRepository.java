package com.example.location_service.Repository;

import com.example.location_service.model.Airport;
import com.example.payload.response.AirportResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AirportRepository  extends JpaRepository<Airport,Long> {
    Optional<Airport>  findByIataCode(String iataCode);

     List<AirportResponse> getByCityId(Long cityId);


    Optional<Airport> findByCityId(Long cityId);
}
