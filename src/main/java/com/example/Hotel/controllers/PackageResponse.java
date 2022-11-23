package com.example.Hotel.controllers;



public record PackageResponse(
        City city,
        Passeio passeio,
        Hotels hotels,
        Informacoes informacoes,
        Float total

) {
    public record Passeio(
            String name,
            String descricao,
            Integer star
    ){}
    public record Hotels(
            String name,
            String address,
            String hotel_description,
            Integer star

    ){}
    public record City(
            String name,
            String estado
    ){}
    public record Informacoes(
            Integer quantidade_pessoas,
            Integer quantidade_dias,
            Float preco_passeio,
            Float diaria_hotel,
            Float total_passeio,
            Float total_hotel

    ){

}
}


