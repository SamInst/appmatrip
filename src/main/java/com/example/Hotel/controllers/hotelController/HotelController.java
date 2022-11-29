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
import java.util.Optional;

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

    @GetMapping("/findByPriceBetweenAndAmountOfPeople") //------------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse2>> findByPriceBetweenTwoPersons(LocalDate dataEntry, LocalDate dataOut, Integer quantidadePessoa, Optional<Float> optionalPrice1, Optional<Float> optionalPrice2) {
        return hotelService.PriceBetween(dataEntry,dataOut,quantidadePessoa,optionalPrice1,optionalPrice2);
    }

    @GetMapping("queryCityState_Id")
    public ResponseEntity<List<HotelsListInCityResponse2>> queryCityState_Id (LocalDate dataEntry, LocalDate dataOut, Integer quantidadePessoa, Long city_id, Long state_id){
        return hotelService.QueryBy_CityId_StateId_DataEntry_DataOut(dataEntry, dataOut,quantidadePessoa,city_id,state_id);
    }

    @GetMapping("/QueryBy_DataEntry_DataOut_AmountPeople_TotalPrice") //----------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse2>> testData( LocalDate dataEntry, LocalDate dataOut, Integer quantidadePessoa, Long city_id, Long state_id,@RequestParam(required = false) Float hotelPrices, @RequestParam(required = false) Float hotelPrices2, @RequestParam(required = false) String name) {
        return hotelService.queryByCityID_StateID(dataEntry,dataOut,quantidadePessoa,city_id,state_id,hotelPrices,hotelPrices2, name);
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