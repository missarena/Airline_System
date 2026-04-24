package com.example.location_service.controller;

import com.example.location_service.service.AirportService;
import com.example.payload.request.AirportRequest;
import com.example.payload.response.AirportResponse;
import com.example.payload.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/airports")
public class AirportController {

    private final AirportService airportService;

    @PostMapping
    public ResponseEntity<AirportResponse> createAirport(@Valid @RequestBody AirportRequest airportRequest) throws Exception{
        return ResponseEntity.status(HttpStatus.CREATED).body(
                airportService.createAirport(airportRequest)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportResponse> getAirportById(@PathVariable Long id) throws Exception{
        return ResponseEntity.ok(airportService.getAirportById(id));
    }

    @GetMapping
    public ResponseEntity<List<AirportResponse>> getAllAirports() {
        return ResponseEntity.ok(airportService.getAllAirports());
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity <List<AirportResponse>>getAirportByCityId(@PathVariable("cityId") Long cityId) throws Exception{
        return ResponseEntity.ok(airportService.getAirportByCityId(cityId));
    }

    @PutMapping
    public ResponseEntity<AirportResponse> updateAirport(
            @PathVariable Long id,
            @Valid @RequestBody AirportRequest request) throws Exception {

      return ResponseEntity.ok(airportService.updateAirport(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAirport(
            @PathVariable Long id)throws Exception{
           airportService.deleteAirport(id);
        return ResponseEntity.ok(new ApiResponse("Airport deleted successfully"));
    }


}
