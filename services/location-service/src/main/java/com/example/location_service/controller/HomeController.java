package com.example.location_service.controller;

import com.example.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping()
    public ApiResponse HomeController(){
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setMessage("hello I am location service");
        return apiResponse;
    }
}
