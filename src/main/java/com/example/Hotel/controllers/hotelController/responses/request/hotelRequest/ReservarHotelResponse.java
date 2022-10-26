package com.example.Hotel.controllers.hotelController.responses.request.hotelRequest;

import java.time.LocalDate;

public record ReservarHotelResponse(

        String hotelName,
        City city,
        Client client,
        Integer amount_adults,
        Integer amount_childs,
        Hotel prices

) {
    public record City(
            String cityName,
            String stateName){
    }
    public record Client(
            String name,
            String cpf,
            String phone,
            String email,
            String address,
            LocalDate dataEntry,
            LocalDate dataOut
    ){}
    public record Hotel(
            Float days,
            Float daily,
            Float total_price

    ){}
}


