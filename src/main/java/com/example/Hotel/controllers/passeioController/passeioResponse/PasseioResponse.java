package com.example.Hotel.controllers.passeioController.passeioResponse;

import java.time.LocalDate;
import java.time.LocalTime;

public record PasseioResponse(
        String cliente_nome,
        String nomeDoPasseio,
        String descricao,
        PasseiosPrecos preco,
        GuiaTuristico guiaTuristico,
        City cidade,
        Float quantidade_pessoas,
        Datas datas,
        Float total
) {
    public record City(
        String cidade,
        String estado
    ){}
    public record PasseiosPrecos(
            Float preco_pessoa
    ){}
    public record Datas(
            LocalDate data,
            String hora
    ){}
    public record GuiaTuristico(
            String nome_guia
    ){}
}


