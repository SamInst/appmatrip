package com.example.Hotel.controllers.hotelController.responses;

public record ClienteResponse(
        String name,
        String cpf,
        String phone,
        String email,
        String address
){}

