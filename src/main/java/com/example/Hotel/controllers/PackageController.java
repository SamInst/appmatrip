//package com.example.Hotel.controllers;
//
//import com.example.Hotel.controllers.hotelController.HotelController;
//import com.example.Hotel.controllers.hotelController.responses.hotelResponses.request.hotelRequest.HotelsListInCityResponse;
//import com.example.Hotel.exceptions.EntityNotFound;
//import com.example.Hotel.model.hotel.Hotels;
//import com.example.Hotel.model.passeios.Passeio;
//import com.example.Hotel.repositorys.hotelRepository.HotelRepository;
//import com.example.Hotel.repositorys.passeioRepository.PasseioRepository;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/packages")
//public class PackageController {
//    private Hotels hotels;
//    private HotelRepository hotelRepository;
//    private PasseioRepository passeioRepository;
//    Float price;
//
//    public void test(Integer quantidadePessoa, List<Hotels> hotels, List<Passeio> passeios){
//
//        List<HotelsListInCityResponse> hotelsList = new ArrayList<>();
//        hotels.forEach(hotel1 ->
//        {
//        if (quantidadePessoa == 1){
//            price = hotel1.getHotelPrices().getPriceOne();
//        } else if (quantidadePessoa == 2) {
//            price = hotel1.getHotelPrices().getPriceTwo();
//        } else if (quantidadePessoa == 3) {
//            price = hotel1.getHotelPrices().getPriceThree();
//        }else if (quantidadePessoa == 4) {
//            price = hotel1.getHotelPrices().getPriceFour();
//        }else if (quantidadePessoa == 5) {
//            price = hotel1.getHotelPrices().getPriceFive();
//        } else {
//            throw new EntityNotFound("Tamanho não suportado");
//        }
//            hotelsList.add();
//
//    }
//}
