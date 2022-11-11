package com.example.Hotel.controllers.outrosControllers.outrosResponses;

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

