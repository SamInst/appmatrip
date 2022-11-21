package com.example.Hotel.controllers;

import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.repositorys.hotelRepository.HotelRepository;
import com.example.Hotel.repositorys.passeioRepository.PasseioRepository;
import com.example.Hotel.services.hotelServices.HotelRegistrationService;
import com.example.Hotel.services.passeiosServices.PasseioRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/packages")
public class PackageController {

    private final PasseioRepository passeioRepository;


    private final HotelRepository hotelRepository;

    public PackageController(PasseioRepository passeioRepository, PasseioRegistrationService passeioRegistrationService, HotelRepository hotelRepository, HotelRegistrationService hotelRegistrationService) {
        this.passeioRepository = passeioRepository;
        this.hotelRepository = hotelRepository;
    }



    @GetMapping("/{packageId}") //--------------------------------------------------------------------------------------
    public ResponseEntity<PackageResponse> findPackage (Long id){


        final var hotels = hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Hotel not found"));

        final var trips = passeioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Trip not found"));

        float total = hotels.getHotelPrices().getPriceOne() + trips.getPasseiosPrecos().getPriceOne();

        final var packageResponse = new PackageResponse(
                new PackageResponse.City(
                        hotels.getCity().getName(),
                        hotels.getCity().getState().getName()
                        ), new PackageResponse.Passeio(
                                trips.getNomePasseio(),
                                trips.getDescricao(),
                new PackageResponse.Passeio.PasseiosPrecos(
                        trips.getPasseiosPrecos().getPriceOne()
                ),
                trips.getEstrela()
                ), new PackageResponse.Hotels(
                        hotels.getName(),
                hotels.getAddress(),
                hotels.getHotelDescription(),
                hotels.getStar(),
                new PackageResponse.Hotels.HotelPrecos(
                        hotels.getHotelPrices().getPriceOne()
                )
        ), total
        );
        return ok(packageResponse);
    }

}
