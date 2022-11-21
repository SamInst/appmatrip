package com.example.Hotel.controllers;



public record PackageResponse(
        City city,
        Passeio passeio,
        Hotels hotels,
        Float total

) {
    public record Passeio(
            String name,
            String descricao,
            PasseiosPrecos passeiosPrecos,
            Integer star
    ){public record PasseiosPrecos(
                Float preco
                ){}
    }
    public record Hotels(
            String name,
            String address,
            String hotel_description,
            Integer star,
            HotelPrecos hotelPrecos


    ){    public record HotelPrecos(
            Float preco){}
    }
    public record City(
            String name,
            String estado
    ){
        public record Estado(
                String name
        ){}
    }
}


