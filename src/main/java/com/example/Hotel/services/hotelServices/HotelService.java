package com.example.Hotel.services.hotelServices;

import com.example.Hotel.controllers.hotelController.responses.hotelResponses.request.hotelRequest.HotelsListInCityResponse2;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.model.hotel.Hotels;
import com.example.Hotel.repositorys.hotelRepository.HotelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HotelService {
    Float price;

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public ResponseEntity<List<HotelsListInCityResponse2>> PriceBetween (LocalDate dataEntry, LocalDate dataOut, Integer quantidadePessoa, Optional<Float> optionalPrice1, Optional<Float> optionalPrice2 ){
        List<Hotels> hotelsList = new ArrayList<>();
    if (quantidadePessoa == 1){
             hotelsList.addAll(hotelRepository.queryHotelsByHotelPrices_PriceOneBetween(optionalPrice1, optionalPrice2));
        } else if (quantidadePessoa == 2) {
             hotelsList.addAll(hotelRepository.queryHotelsByHotelPrices_PriceTwoBetween(optionalPrice1, optionalPrice2));
        } else if (quantidadePessoa == 3) {
             hotelsList.addAll(hotelRepository.queryHotelsByHotelPrices_PriceThreeBetween(optionalPrice1, optionalPrice2));
        }else if (quantidadePessoa == 4) {
             hotelsList.addAll(hotelRepository.queryHotelsByHotelPrices_PriceFourBetween(optionalPrice1, optionalPrice2));
        }else if (quantidadePessoa == 5) {
             hotelsList.addAll(hotelRepository.queryHotelsByHotelPrices_PriceFiveBetween(optionalPrice1, optionalPrice2));
        } else {
        throw new EntityNotFound("Não existe reservas para essa quantidade de pessoas");
    }
    return getListResponseEntityHotel( dataEntry, dataOut, quantidadePessoa, hotelsList);
}


    public ResponseEntity<List<HotelsListInCityResponse2>> QueryBy_CityId_StateId_DataEntry_DataOut(
            LocalDate dataEntry, LocalDate dataOut, Integer quantidadePessoa, Long city_id, Long state_id){

        List<Hotels> hotelsList = new ArrayList<>(hotelRepository.queryHotelsByCity_IdAndCity_State_Id(city_id, state_id));
        return getListResponseEntityHotel(dataEntry,dataOut, quantidadePessoa, hotelsList);
    }
    private ResponseEntity<List<HotelsListInCityResponse2>> getListResponseEntityHotel(
            LocalDate dataEntry, LocalDate dataOut,Integer quantidadePessoa, List<Hotels> hotels) {

        List<HotelsListInCityResponse2> hotelsList = new ArrayList<>();
        hotels.forEach(hotel1 ->
                {
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
                    Integer p1 = Period.between(dataEntry, dataOut).getDays();
                    float total = price * p1;

                    hotelsList.add(new HotelsListInCityResponse2(
                            new HotelsListInCityResponse2.Categoria(
                                    hotel1.getCategoria().getName()
                            ),
                            new HotelsListInCityResponse2.City(
                                    hotel1.getCity().getName(),
                                    hotel1.getCity().getState().getName()),
                            hotel1.getName(),
                            hotel1.getHotelDescription(),
                            hotel1.getAddress(),
                            new HotelsListInCityResponse2.HotelPrices(
                                    price
                            ),
                            hotel1.getStar(),
                            p1,
                            total
                    ));
                }
        );
        return  ResponseEntity.ok(hotelsList);
    }
        public ResponseEntity<List<HotelsListInCityResponse2>> queryByCityID_StateID (LocalDate dataEntry, LocalDate dataOut, Integer quantidadePessoa, Long city_id, Long city_state_id, Float optionalPrice1, Float optionalPrice2, String name ){
        List<Hotels> hotelsList = new ArrayList<>();

        if (quantidadePessoa == 1) {
             hotelsList.addAll(hotelRepository.queryHotelsByCity_IdAndCity_State_IdAndNameAndHotelPrices_PriceOneBetween(city_id, city_state_id, name, optionalPrice1, optionalPrice2));
        }else if (quantidadePessoa == 2) {
             hotelsList.addAll(hotelRepository.queryHotelsByCity_IdAndCity_State_IdAndNameAndHotelPrices_PriceTwoBetween(city_id, city_state_id, name, optionalPrice1, optionalPrice2));
        }else if (quantidadePessoa == 3) {
             hotelsList.addAll(hotelRepository.queryHotelsByCity_IdAndCity_State_IdAndNameAndHotelPrices_PriceThreeBetween(city_id, city_state_id, name, optionalPrice1, optionalPrice2));
        }else if (quantidadePessoa == 4) {
             hotelsList.addAll(hotelRepository.queryHotelsByCity_IdAndCity_State_IdAndNameAndHotelPrices_PriceFourBetween(city_id, city_state_id, name, optionalPrice1, optionalPrice2));
        }else if (quantidadePessoa == 5) {
             hotelsList.addAll(hotelRepository.queryHotelsByCity_IdAndCity_State_IdAndNameAndHotelPrices_PriceFiveBetween(city_id, city_state_id, name, optionalPrice1, optionalPrice2));
        }else {
        throw new EntityNotFound("Cada reserva deve ser feitas por no máximo 5 pessoas");
    }
    return getListResponseEntityHotel(dataEntry,dataOut, quantidadePessoa, hotelsList);
}

}