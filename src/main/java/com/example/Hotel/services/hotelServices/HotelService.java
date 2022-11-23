package com.example.Hotel.services.hotelServices;

import com.example.Hotel.controllers.hotelController.responses.hotelResponses.request.hotelRequest.HotelsListInCityResponse;
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

@Service
public class HotelService {
    Float price;

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final HotelRepository hotelRepository;

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

//    public ResponseEntity<List<HotelsListInCityResponse>> PriceBetweenAndCityID (Integer quantidadePessoa, Float price1, Float price2, Long id ){
//        List<Hotels> hotelsList = new ArrayList<>();
//        if (quantidadePessoa == 1){
//            hotelsList.addAll(hotelRepository.queryByHotelPrices_PriceOneBetweenAndCityId(price1, price2, id));
//        } else if (quantidadePessoa == 2) {
//            hotelsList.addAll(hotelRepository.queryByHotelPrices_PriceTwoBetweenAndCityId(price1, price2, id));
//        } else if (quantidadePessoa == 3) {
//            hotelsList.addAll(hotelRepository.queryByHotelPrices_PriceThreeBetweenAndCityId(price1, price2, id));
//        }else if (quantidadePessoa == 4) {
//            hotelsList.addAll(hotelRepository.queryByHotelPrices_PriceFourBetweenAndCityId(price1, price2, id));
//        }else if (quantidadePessoa == 5) {
//            hotelsList.addAll(hotelRepository.queryByHotelPrices_PriceFiveBetweenAndCityId(price1, price2, id));
//        } else {
//            throw new EntityNotFound("Não existe reservas para essa quantidade de pessoas");
//        }
//        return getListResponseEntityPriceForOne(quantidadePessoa, hotelsList);
//    }

//    public ResponseEntity<List<HotelsListInCityResponse>> PriceBetweenAndCityName (Integer quantidadePessoa, Float price1, Float price2, String name){
//        List<Hotels> hotelsList = new ArrayList<>();
//        if (quantidadePessoa == 1){
//            hotelsList.addAll(hotelRepository.queryByHotelPrices_PriceOneBetweenAndCity_Name(price1, price2, name));
//        } else if (quantidadePessoa == 2) {
//            hotelsList.addAll(hotelRepository.queryByHotelPrices_PriceTwoBetweenAndCity_Name(price1, price2, name));
//        } else if (quantidadePessoa == 3) {
//            hotelsList.addAll(hotelRepository.queryByHotelPrices_PriceThreeBetweenAndCity_Name(price1, price2, name));
//        }else if (quantidadePessoa == 4) {
//            hotelsList.addAll(hotelRepository.queryByHotelPrices_PriceFourBetweenAndCity_Name(price1, price2, name));
//        }else if (quantidadePessoa == 5) {
//            hotelsList.addAll(hotelRepository.queryByHotelPrices_PriceFiveBetweenAndCity_Name(price1, price2, name));
//        } else {
//            throw new EntityNotFound("Não existe reservas para essa quantidade de pessoas");
//        }
//        return getListResponseEntityPriceForOne(quantidadePessoa, hotelsList);
//    }

//    public ResponseEntity<List<HotelsListInCityResponse>> PriceBetweenAndCity_IdAndState_Id(Integer quantidadePessoa, Long city_id, Long state_id){
//        List<Hotels> hotelsList = new ArrayList<>();
//        if (quantidadePessoa == 1){
//            hotelsList.addAll(hotelRepository.queryHotelsByCity_IdAndCity_State_Id(city_id, state_id));
//        } else if (quantidadePessoa == 2) {
//            hotelsList.addAll(hotelRepository.queryHotelsByCity_IdAndCity_State_Id(city_id, state_id));
//        } else if (quantidadePessoa == 3) {
//            hotelsList.addAll(hotelRepository.queryHotelsByCity_IdAndCity_State_Id(city_id, state_id));
//        }else if (quantidadePessoa == 4) {
//            hotelsList.addAll(hotelRepository.queryHotelsByCity_IdAndCity_State_Id(city_id, state_id));
//        }else if (quantidadePessoa == 5) {
//            hotelsList.addAll(hotelRepository.queryHotelsByCity_IdAndCity_State_Id(city_id, state_id));
//        } else {
//            throw new EntityNotFound("Não existe reservas para essa quantidade de pessoas");
//        }
//        return getListResponseEntityPriceForOne( quantidadePessoa, hotelsList);
//    }

    public ResponseEntity<List<HotelsListInCityResponse2>> QueryBy_CityId_StateId_DataEntry_DataOut(LocalDate dataEntry, LocalDate dataOut, Integer quantidadePessoa, Long city_id, Long state_id){
        List<Hotels> hotelsList = new ArrayList<>(hotelRepository.queryHotelsByCity_IdAndCity_State_Id(city_id, state_id));
        return getListResponseEntityHotel(dataEntry,dataOut, quantidadePessoa, hotelsList);
    }

    private ResponseEntity<List<HotelsListInCityResponse>> getListResponseEntityPriceForOne( Integer quantidadePessoa,List<Hotels> hotels) {
        List<HotelsListInCityResponse> hotelsList = new ArrayList<>();
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

    private ResponseEntity<List<HotelsListInCityResponse2>> getListResponseEntityHotel(LocalDate dataEntry, LocalDate dataOut, Integer quantidadePessoa, List<Hotels> hotels) {
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
}