package com.example.Hotel.controllers.hotelController;

import com.example.Hotel.controllers.hotelController.responses.hotelResponses.request.hotelRequest.HotelsListInCityResponse;
import com.example.Hotel.controllers.hotelController.responses.hotelResponses.request.hotelRequest.HotelsListInCityResponse2;
import com.example.Hotel.controllers.hotelController.responses.hotelResponses.request.hotelRequest.HotelsResponse;
import com.example.Hotel.model.hotel.Hotels;
import com.example.Hotel.exceptions.EntityInUse;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.repositorys.hotelRepository.HotelRepository;
import com.example.Hotel.services.hotelServices.HotelRegistrationService;
import com.example.Hotel.services.hotelServices.HotelService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    @Autowired
    private HotelRepository hotelRepository;
    private final HotelRegistrationService hotelRegistrationService;

    private final HotelService hotelService;
    private final static Long  HOTEL_CATEGORIA = 1L;

    public HotelController(HotelRegistrationService hotelRegistrationService, HotelRepository hotelRepository, HotelService hotelService) {
        this.hotelRegistrationService = hotelRegistrationService;
        this.hotelRepository = hotelRepository;
        this.hotelService = hotelService;
    }
    @GetMapping
    public List<Hotels> list() {
        return hotelRepository.findAll();
    }

    @GetMapping("/{hotelId}") //--------------------------------------------------------------------------------------
    public ResponseEntity<HotelsResponse> find(@PathVariable("hotelId") Long id) {
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping //---------------------------------------------------------------------------------------------------
    public Hotels add(@RequestBody Hotels hotels) {
        return hotelRegistrationService.add(hotels);
    }

    @PutMapping("/{hotelId}") //--------------------------------------------------------------------------------------
    public Hotels update(@PathVariable Long hotelId, @RequestBody Hotels hotels) {
        Hotels hotels1 = hotelRepository.findById(hotelId).get();
            BeanUtils.copyProperties(hotels, hotels1, "id","city","ein","categoria");
           return hotelRepository.save(hotels1);
        }
    @DeleteMapping("/{hotelId}") //-----------------------------------------------------------------------------------
    public ResponseEntity<Hotels> remove(@PathVariable Long hotelId) {
        try {
            hotelRegistrationService.exclude(hotelId);
            return noContent().build();
        } catch (EntityNotFound e) {
            return notFound().build();
        } catch (EntityInUse e) {
            return status(HttpStatus.CONFLICT).build();
        }
    }

//    @GetMapping("/findHotelsByStateId") //----------------------------------------------------------------------------
//    public ResponseEntity<List<HotelsListInCityResponse>> findHotelsByStateId(@RequestParam (name = "state_id") Long state_id) {
//        final var hotels = hotelRepository.queryHotelsByCity_State_IdAndCategoria_Id(state_id, HOTEL_CATEGORIA);
//        return getListResponseEntityPriceForOne(hotels);
//    }

    @GetMapping("/findHotelsByDestaques") //--------------------------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findHotelsByDestaques(Integer destaque) {
        final var hotels = hotelRepository.queryHotelsByDestaque(destaque);
        return getListResponseEntityPriceForOne(hotels);
    }

    @GetMapping("/find/hotelsByName") //------------------------------------------------------------------------------
    @ResponseBody
    public ResponseEntity<List<HotelsListInCityResponse>> hotelsByName(@RequestParam(name = "name") String name) {
        final var hotels = hotelRepository.findByName(name.trim().toUpperCase());
        return getListResponseEntityPriceForOne(hotels);
    }
//    @GetMapping("/findHotelsByCityName") //---------------------------------------------------------------------------
//    @ResponseBody
//    public ResponseEntity<List<HotelsListInCityResponse>> findHotelsByCityName(@RequestParam(name = "name") String name) {
//        final var hotels = hotelRepository.findHotelsByCity_Name(name.trim().toUpperCase());
//        return getListResponseEntityPriceForOne(hotels);
//    }

    @GetMapping("/findByPriceBetweenAndAmountOfPeople") //------------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findByPriceBetweenTwoPersons(Integer quantidadePessoa, Float price1, Float price2) {
        return hotelService.PriceBetween(quantidadePessoa, price1, price2);
    }

//    @GetMapping("/findByPriceBetweenAndCityId") //--------------------------------------------------------------------
//    public ResponseEntity<List<HotelsListInCityResponse>> findByPriceBetween(Integer quantidadePessoa, Float price1,
//    Float price2, Long id) {
//        return hotelService.PriceBetweenAndCityID(quantidadePessoa, price1, price2, id);
//    }

//    @GetMapping("/findByPriceBetweenAndCityName") //------------------------------------------------------------------
//    public ResponseEntity<List<HotelsListInCityResponse>> findByPriceBetweenAndCityName(Integer quantidadePessoa,
//    Float price1, Float price2, String name) {
//        return hotelService.PriceBetweenAndCityName(quantidadePessoa, price1, price2, name);
//    }

//    @GetMapping("/findHotelPriceByAmountPeopleAndCityAndStateId") //--------------------------------------------------
//    public ResponseEntity<List<HotelsListInCityResponse>> findHotelPriceOneByCityAndStateId(Integer quantidadePessoa,
//    Long city_id, Long state_id) {
//        return hotelService.PriceBetweenAndCity_IdAndState_Id(quantidadePessoa, city_id, state_id);
//    }

    @GetMapping("/QueryBy_DataEntry_DataOut_AmountPeople_TotalPrice") //----------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse2>> testData(LocalDate dataEntry, LocalDate dataOut,
                                                                    Integer quantidadePessoa, Long city_id, Long state_id) {
        return hotelService.QueryBy_CityId_StateId_DataEntry_DataOut(dataEntry,dataOut, quantidadePessoa, city_id, state_id);
    }

    private ResponseEntity<List<HotelsListInCityResponse>> getListResponseEntityPriceForOne(List<Hotels> hotels) {
        List<HotelsListInCityResponse> hotelsList = new ArrayList<>();
        hotels.forEach(
                hotel1 -> hotelsList.add(new HotelsListInCityResponse(
                        new HotelsListInCityResponse.Categoria(
                                hotel1.getCategoria().getName()
                        ),
                        new HotelsListInCityResponse.City(
                                hotel1.getCity().getName(),
                                hotel1.getCity().getState().getName()),
                        hotel1.getName(),
                        hotel1.getHotelDescription(),
                        hotel1.getAddress(),
                        new HotelsListInCityResponse.HotelPrices (
                                hotel1.getHotelPrices().getPriceOne()),
                        hotel1.getStar()
                )));
        return  ResponseEntity.ok(hotelsList);
    }
}