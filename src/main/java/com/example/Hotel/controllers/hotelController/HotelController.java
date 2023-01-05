package com.example.Hotel.controllers.hotelController;

import com.example.Hotel.controllers.hotelController.responses.hotelResponses.request.hotelRequest.HotelsListInCityResponse;
import com.example.Hotel.controllers.hotelController.responses.hotelResponses.request.hotelRequest.HotelsListInCityResponse2;
import com.example.Hotel.controllers.hotelController.responses.hotelResponses.request.hotelRequest.HotelsResponse;
import com.example.Hotel.model.hotel.Hotels;
import com.example.Hotel.repositorys.hotelRepository.HotelRepository;
import com.example.Hotel.services.hotelServices.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    private final HotelRepository hotelRepository;
    private final HotelService hotelService;
//    private final static Long HOTEL_CATEGORIA = 1L;

    public HotelController(HotelRepository hotelRepository, HotelService hotelService) {
        this.hotelRepository = hotelRepository;
        this.hotelService = hotelService;
    }

    @GetMapping
    public List<Hotels> list() {
        return hotelRepository.findAll();
    }

    @GetMapping("/{hotelId}") //--------------------------------------------------------------------------------------
    public ResponseEntity<HotelsResponse> find(@PathVariable("hotelId") Long id) {
       return hotelService.findHotelById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping //---------------------------------------------------------------------------------------------------
    public Hotels add(@RequestBody Hotels hotels) {
        return hotelService.add(hotels);
    }

    @PutMapping("/{hotelId}") //--------------------------------------------------------------------------------------
    public Hotels update(@PathVariable Long hotelId, @RequestBody Hotels hotels) {
       return hotelService.updateById(hotelId, hotels);
    }

    @DeleteMapping("/{hotelId}") //-----------------------------------------------------------------------------------
    public ResponseEntity<Hotels> remove(@PathVariable Long hotelId) {
        return hotelService.deleteById(hotelId);
    }

    @GetMapping("/findHotelsByDestaques") //--------------------------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findHotelsByDestaques(Integer quantidadePessoa, Integer destaque) {
        return hotelService.hotelsByDestaques(quantidadePessoa, destaque);
    }

    @GetMapping("/QueryBy_DataEntry_DataOut_AmountPeople_TotalPrice") //----------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse2>> testData(
            LocalDate dataEntry,
            LocalDate dataOut,
            Integer quantidadePessoa,
            Long city_id,
            Long state_id,
            @RequestParam(required = false) Float hotelPrices,
            @RequestParam(required = false) Float hotelPrices2,
            @RequestParam(required = false) String name) {

        if (hotelPrices == null && hotelPrices2 == null && name == null) {
            return hotelService.QueryBy_CityId_StateId_DataEntry_DataOut(dataEntry, dataOut, quantidadePessoa, city_id, state_id);

        } else if(name != null && hotelPrices == null && hotelPrices2 == null){
            return hotelService.queryHotelsByName(dataEntry, dataOut, quantidadePessoa, city_id, state_id, name);

        } else if (name == null) {
            return hotelService.PriceBetween(dataEntry, dataOut,quantidadePessoa, Optional.ofNullable(hotelPrices), Optional.ofNullable(hotelPrices2));

        } else {
            return hotelService.queryHotelsByCityID_StateID_AndName_And_HotelPricesBetween(dataEntry, dataOut, quantidadePessoa, city_id, state_id, hotelPrices, hotelPrices2, name);
        }
    }
}