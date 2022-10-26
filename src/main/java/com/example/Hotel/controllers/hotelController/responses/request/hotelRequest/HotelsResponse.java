package com.example.Hotel.controllers.hotelController.responses.request.hotelRequest;

public record HotelsResponse(
        String name,
        String ein,
        String phone,
        String hotelDescription,
        City city,
        String address,
        HotelPrices daily,
        Integer star

) {
    public record City(
        String city,
        String state
){}
    public record HotelPrices(
            Float one_Person,
            Float two_Persons,
            Float three_Persons,
            Float four_Persons,
            Float five_Persons
    ){}
}


