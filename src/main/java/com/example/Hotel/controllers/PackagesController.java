package com.example.Hotel.controllers;

import com.example.Hotel.services.outrosServices.PackageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/packages")
public class PackagesController {
    private final PackageService packageService;

    public PackagesController(PackageService packageService) {
        this.packageService = packageService;
    }

    @GetMapping("/packages")
    public ResponseEntity<List<PackageResponse>> packagePrice(LocalDate data_entry, LocalDate data_out, Integer quantidadePessoas,Long id){
        return packageService.JajaDecidoONome(data_entry, data_out,quantidadePessoas,id);

    }
}
