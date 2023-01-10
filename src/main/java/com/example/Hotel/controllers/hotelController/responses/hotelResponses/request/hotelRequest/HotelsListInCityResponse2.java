package com.example.Hotel.controllers.hotelController.responses.hotelResponses.request.hotelRequest;

public record HotelsListInCityResponse2(
        Categoria categoria,
        City Local,
        String nome,
        String Descricao,
        String endereco,
        HotelPrices diaria,
        Integer estrelas,
        Integer quantidade_dias,
        Float total
    ){
    public record City(
        String cidade,
        String estado
    ){}
    public record HotelPrices(
            Float preco
    ){}
    public record Categoria(
            String tipo
    ){}
}


