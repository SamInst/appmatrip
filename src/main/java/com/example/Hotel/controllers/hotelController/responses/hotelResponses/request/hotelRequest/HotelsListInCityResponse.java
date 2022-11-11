package com.example.Hotel.controllers.hotelController.responses.hotelResponses.request.hotelRequest;

public record HotelsListInCityResponse(
        Categoria categoria,
        City Local,
        String nome,
        String Descricao,
        String endereco,
        HotelPrices valor,
        Integer estrelas

) {
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


