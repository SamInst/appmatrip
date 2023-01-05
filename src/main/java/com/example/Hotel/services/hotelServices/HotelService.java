package com.example.Hotel.services.hotelServices;

import com.example.Hotel.controllers.hotelController.responses.hotelResponses.request.hotelRequest.HotelsListInCityResponse;
import com.example.Hotel.controllers.hotelController.responses.hotelResponses.request.hotelRequest.HotelsListInCityResponse2;
import com.example.Hotel.controllers.hotelController.responses.hotelResponses.request.hotelRequest.HotelsResponse;
import com.example.Hotel.exceptions.EntityInUse;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.model.hotel.Hotels;
import com.example.Hotel.repositorys.hotelRepository.HotelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;
import static org.springframework.http.ResponseEntity.status;

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

    public ResponseEntity<List<HotelsListInCityResponse2>> QueryBy_CityId_StateId_DataEntry_DataOut(LocalDate dataEntry, LocalDate dataOut, Integer quantidadePessoa, Long city_id, Long state_id){
        List<Hotels> hotelsList = new ArrayList<>(hotelRepository.queryHotelsByCity_IdAndCity_State_Id(city_id, state_id));
        return getListResponseEntityHotel(dataEntry,dataOut, quantidadePessoa, hotelsList);
    }

    public ResponseEntity<List<HotelsListInCityResponse2>> queryHotelsByName (LocalDate dataEntry, LocalDate dataOut, Integer quantidadePessoa, Long city_id, Long state_id, String name){
        List<Hotels> hotelsList = new ArrayList<>( hotelRepository.findHotelsByNameAndCity_IdAndCity_State_Id(name, city_id, state_id));
        return getListResponseEntityHotel(dataEntry,dataOut,quantidadePessoa,hotelsList);
    }

    public ResponseEntity<List<HotelsListInCityResponse>> hotelsByDestaques(Integer destaque, Integer quantidadePessoa) {
        final var hotelsList = hotelRepository.queryHotelsByDestaque(destaque);
        return getListResponseEntityHotel2(destaque, hotelsList);
    }

    private ResponseEntity<List<HotelsListInCityResponse2>> getListResponseEntityHotel(LocalDate dataEntry, LocalDate dataOut,Integer quantidadePessoa, List<Hotels> hotels) {

        List<HotelsListInCityResponse2> hotelsList = new ArrayList<>();
        hotels.forEach(hotel1 ->
                {
                    quantidadeDePessoas(quantidadePessoa, hotel1);
                    Integer p1 = Period.between(dataEntry, dataOut).getDays();
                    float total = price * p1;

                    hotelsList.add(new HotelsListInCityResponse2(
                            new HotelsListInCityResponse2.Categoria(
                                    hotel1.getCategoria().getName()),
                            new HotelsListInCityResponse2.City(
                                    hotel1.getCity().getName(),
                                    hotel1.getCity().getState().getName()),
                            hotel1.getName(),
                            hotel1.getHotelDescription(),
                            hotel1.getAddress(),
                            new HotelsListInCityResponse2.HotelPrices(
                                    price),
                            hotel1.getStar(),
                            p1,
                            total
                    ));
                }
        );
        return  ResponseEntity.ok(hotelsList);
    }
        public ResponseEntity<List<HotelsListInCityResponse2>> queryHotelsByCityID_StateID_AndName_And_HotelPricesBetween (LocalDate dataEntry, LocalDate dataOut, Integer quantidadePessoa, Long city_id, Long city_state_id, Float optionalPrice1, Float optionalPrice2, String name ){
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
    private ResponseEntity<List<HotelsListInCityResponse>> getListResponseEntityHotel2(Integer quantidadePessoa, List<Hotels> hotels) {

        List<HotelsListInCityResponse> hotelsList = new ArrayList<>();
        hotels.forEach(hotel1 ->
                {
                    quantidadeDePessoas(quantidadePessoa, hotel1);

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

    private void quantidadeDePessoas(Integer quantidadePessoa, Hotels hotel1) {
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
    }
    public ResponseEntity<HotelsResponse> findHotelById(Long id){
        final var hotels = hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Hotel not found"));
        if (hotels != null) {
            final var response = new HotelsResponse(
                    hotels.getName(),
                    hotels.getEin(),
                    hotels.getPhone(),
                    hotels.getHotelDescription(),
                    new HotelsResponse.City(
                            hotels.getCity().getName(),
                            hotels.getCity().getState().getName()
                    ),
                    hotels.getAddress(),
                    new HotelsResponse.HotelPrices(
                            hotels.getHotelPrices().getPriceOne(),
                            hotels.getHotelPrices().getPriceTwo(),
                            hotels.getHotelPrices().getPriceThree(),
                            hotels.getHotelPrices().getPriceFour(),
                            hotels.getHotelPrices().getPriceFive()),
                    hotels.getStar()
            );
            return ok(response);
        } else {
            return notFound().build();
        }
    }

    public ResponseEntity<Hotels> deleteById(Long hotelId){
        try {
            exclude(hotelId);
            return noContent().build();
        } catch (EntityNotFound e) {
            return notFound().build();
        } catch (EntityInUse e) {
            return status(HttpStatus.CONFLICT).build();
        }
    }

    public Hotels updateById(@PathVariable Long hotelId, @RequestBody Hotels hotels){
        Hotels hotels1 = hotelRepository.findById(hotelId).get();
        BeanUtils.copyProperties(hotels, hotels1, "id", "city", "ein", "categoria");
        return hotelRepository.save(hotels1);
    }

    public Hotels add(Hotels hotels) {
        return hotelRepository.save(hotels);
    }
    public void exclude(Long hotelId) {
        try {
            hotelRepository.deleteById(hotelId);
        } catch (EmptyResultDataAccessException e){
            throw new EntityNotFound("Hotel code % not found" + hotelId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUse("Hotel code % could be not removed," + hotelId);
        }
    }
}