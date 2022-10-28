package com.example.Hotel.controllers.hotelController.responses;

import java.time.LocalDate;

public record ClienteResponse(
        String nome_completo,
        String apelido,
        String cpf,
        String telefone,
        String email,
        String endereco,
        Nascimento nascimento
){
    public record Nascimento(
            LocalDate aniversario
    ){}
}

