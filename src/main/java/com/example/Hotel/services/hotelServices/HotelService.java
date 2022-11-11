package com.example.Hotel.services.hotelServices;

import com.example.Hotel.controllers.hotelController.responses.request.hotelRequest.HotelsListInCityResponse;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.model.hotel.Hotels;
import com.example.Hotel.repositorys.hotelRepository.HotelRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;
//    List<ReservarHotel> reservarHotel;
//    Integer quantidadePessoa = reservarHotel.getAdults() + reservarHotel.getChilds();

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public ResponseEntity<List<HotelsListInCityResponse>> PriceBetween (Integer quantidadePessoa, Float price1, Float price2 ){
        List<Hotels> hotelsList = new ArrayList<>();

    if (quantidadePessoa == 1){
             hotelsList.addAll(hotelRepository.queryByHotelPrices_PriceOneBetween(price1, price2));
        } else if (quantidadePessoa == 2) {
             hotelsList.addAll(hotelRepository.queryByHotelPrices_PriceTwoBetween(price1, price2));
        } else if (quantidadePessoa == 3) {
             hotelsList.addAll(hotelRepository.queryByHotelPrices_PriceThreeBetween(price1, price2));
        }else if (quantidadePessoa == 4) {
             hotelsList.addAll(hotelRepository.queryByHotelPrices_PriceFourBetween(price1, price2));
        }else if (quantidadePessoa == 5) {
             hotelsList.addAll(hotelRepository.queryByHotelPrices_PriceFiveBetween(price1, price2));
        } else {
        throw new EntityNotFound("Não existe reservas para essa quantidade de pessoas");
    }

    return getListResponseEntityPriceForOne(quantidadePessoa, hotelsList);
}

    private ResponseEntity<List<HotelsListInCityResponse>> getListResponseEntityPriceForOne(Integer quantidadePessoa,List<Hotels> hotels) {
        List<HotelsListInCityResponse> hotelsList = new ArrayList<>();
        //Optional<Hotels> hotels1 = hotelRepository.findById(id);



        hotels.forEach(hotel1 ->
        {
            Float price;
            if (quantidadePessoa == 1){
                price = hotel1.getHotelPrices().getPriceOne();
            } else if (quantidadePessoa == 2) {
                price = hotel1.getHotelPrices().getPriceTwo();
            } else if (quantidadePessoa == 3) {
                price = hotel1.getHotelPrices().getPriceThree();
            }else if (quantidadePessoa == 4) {
                price = hotel1.getHotelPrices().getPriceFour();
            }else if (quantidadePessoa == 5) {
                price = hotel1.getHotelPrices().getPriceFive();
            } else {
                throw new EntityNotFound("Tamanho não suportado");
            }
            hotelsList.add(new HotelsListInCityResponse(
                    new HotelsListInCityResponse.Categoria(
                            hotel1.getCategoria().getName()
                    ),
                    new HotelsListInCityResponse.City(
                            hotel1.getCity().getName(),
                            hotel1.getCity().getState().getName()),
                    hotel1.getName(),
                    hotel1.getHotelDescription(),
                    hotel1.getAddress(),
                    new HotelsListInCityResponse.HotelPrices(
                                price
                    ),

                    hotel1.getStar()
            ));
        }
        );
        return  ResponseEntity.ok(hotelsList);
    }

}
