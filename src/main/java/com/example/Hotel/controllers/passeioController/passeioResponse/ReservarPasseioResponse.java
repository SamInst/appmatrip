package com.example.Hotel.controllers.passeioController.passeioResponse;

import java.time.LocalDate;

public record ReservarPasseioResponse(

        String passeio_nome,
        City city,
        Cliente cliente,
        Integer amount_adults,
        Integer amount_childs,
        Passeio prices

) {
    public record City(
            String cityName,
            String stateName){
    }
    public record Cliente(
            String name,
            String cpf,
            String phone,
            String email,
            String address,
            LocalDate dataEntry,
            LocalDate dataOut
    ){}
    public record Passeio(
            Float days,
            Float daily,
            Float total_price

    ){}
}


