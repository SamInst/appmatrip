package com.example.Hotel.controllers.hotelController.responses.request;

public record PasseioPrecoUnicoResponse(
        Categoria categoria,
        String nomeDoPasseio,
        String descricao,
        PasseiosPrecos preco,
        City cidade,
        Integer estrela
) {
    public record City(
        String cidade,
        String estado
){}
    public record PasseiosPrecos(
            Float preco
    ){}
    public record Categoria(
            String nome
    ){}
}


