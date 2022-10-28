package com.example.Hotel.controllers.hotelController;

import com.example.Hotel.controllers.hotelController.responses.request.hotelRequest.HotelsListInCityResponse;
import com.example.Hotel.controllers.hotelController.responses.request.hotelRequest.HotelsResponse;
import com.example.Hotel.model.hotel.Hotels;
import com.example.Hotel.exceptions.EntityInUse;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.repositorys.hotelRepository.HotelRepository;
import com.example.Hotel.services.hotelServices.HotelRegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private HotelRegistrationService hotelRegistrationService;
    private final static Long  HOTEL_CATEGORIA = 1L;

    public HotelController(HotelRegistrationService hotelRegistrationService, HotelRepository hotelRepository) {
        this.hotelRegistrationService = hotelRegistrationService;
        this.hotelRepository = hotelRepository;
    }
    @GetMapping
    public List<Hotels> list() {
        return hotelRepository.findAll();
    }

    @GetMapping("/{hotelId}") //----------------------------------------------------------------------------------
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
    @PostMapping //-----------------------------------------------------------------------------------------------
    public Hotels add(@RequestBody Hotels hotels) {
        return hotelRegistrationService.add(hotels);
    }

    @PutMapping("/{hotelId}") //----------------------------------------------------------------------------------
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

    @GetMapping("/findHotelsByStateId") //-------------------------------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findHotelsByStateId(@RequestParam (name = "state_id") Long state_id) {
        final var hotels = hotelRepository.queryHotelsByCity_State_IdAndCategoria_Id(state_id, HOTEL_CATEGORIA);
        return getListResponseEntityPriceForOne(hotels);
    }

    @GetMapping("/findHotelsByDestaques") //-------------------------------------------------------------------------------
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
    @GetMapping("/findHotelsByCityName") //---------------------------------------------------------------------------
    @ResponseBody
    public ResponseEntity<List<HotelsListInCityResponse>> findHotelsByCityName(@RequestParam(name = "name") String name) {
        final var hotels = hotelRepository.findHotelsByCity_Name(name.trim().toUpperCase());
        return getListResponseEntityPriceForOne(hotels);
    }
    @GetMapping("/findHotelsInCity") //-------------------------------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findHotelsInCity(Long id) {
        final var hotels = hotelRepository.findHotelsByCityId(id);
        return getListResponseEntityPriceForOne(hotels);
    }
    @GetMapping("/findByPriceBetween") //-----------------------------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findByPriceBetween(Float price1, Float price2) {
        final var hotels = hotelRepository.queryByHotelPrices_PriceOneBetween(price1, price2);
        return getListResponseEntityPriceForOne(hotels);
    }
    @GetMapping("/findByPriceBetweenTwoPersons") //-------------------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findByPriceBetweenTwoPersons(Float price1, Float price2) {
        final var hotels = hotelRepository.queryByHotelPrices_PriceTwoBetween(price1, price2);
        return getListResponseEntityPriceTwo(hotels);
    }
    @GetMapping("/findByPriceBetweenThreePersons") //-----------------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findByPriceBetweenThreePersons(Float price1, Float price2) {
        final var hotels = hotelRepository.queryByHotelPrices_PriceThreeBetween(price1, price2);
        return getListResponseEntityPriceThree(hotels);
    }
    @GetMapping("/findByPriceBetweenFourPersons") //------------------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findByPriceBetweenFourPersons(Float price1, Float price2) {
        final var hotels = hotelRepository.queryByHotelPrices_PriceFourBetween(price1, price2);
        return getListResponseEntityPriceFour(hotels);
    }
    @GetMapping("/findByPriceBetweenFivePersons") //------------------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findByPriceBetweenFivePersons(Float price1, Float price2) {
        final var hotels = hotelRepository.queryByHotelPrices_PriceFiveBetween(price1, price2);
        return getListResponseEntityPriceFive(hotels);
    }






    @GetMapping("/findByPriceBetweenAndCityId") //--------------------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findByPriceBetween(Float price1, Float price2, Long id) {
        final var hotels = hotelRepository.queryByHotelPrices_PriceOneBetweenAndCityId(price1, price2, id);
        return getListResponseEntityPriceForOne(hotels);
    }
    @GetMapping("/findByPriceBetweenTwoPersonsAndCityId") //----------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findByPriceBetweenTwoPersonsAndCityId(Float price1, Float price2, Long id) {
        final var hotels = hotelRepository.queryByHotelPrices_PriceTwoBetweenAndCityId(price1, price2, id);
        return getListResponseEntityPriceTwo(hotels);
    }
    @GetMapping("/findByPriceBetweenThreePersonsAndCityId") //--------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findByPriceBetweenThreePersonsAndCityId(Float price1, Float price2, Long id) {
        final var hotels = hotelRepository.queryByHotelPrices_PriceThreeBetweenAndCityId(price1, price2, id);
        return getListResponseEntityPriceThree(hotels);
    }
    @GetMapping("/findByPriceBetweenFourPersonsAndCityId") //---------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findByPriceBetweenFourPersonsAndCityId(Float price1, Float price2, Long id) {
        final var hotels = hotelRepository.queryByHotelPrices_PriceFourBetweenAndCityId(price1, price2, id);
        return getListResponseEntityPriceFour(hotels);
    }
    @GetMapping("/findByPriceBetweenFivePersonsAndCityId") //---------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findByPriceBetweenFivePersonsAndCityId(Float price1, Float price2, Long id) {
        final var hotels = hotelRepository.queryByHotelPrices_PriceFiveBetweenAndCityId(price1, price2, id);
        return getListResponseEntityPriceFive(hotels);
    }

    @GetMapping("/findByPriceBetweenAndCityName") //--------------------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findByPriceBetweenAndCityName(Float price1, Float price2, String name) {
        final var hotels = hotelRepository.queryByHotelPrices_PriceOneBetweenAndCity_Name(price1, price2, name.trim().toUpperCase());
        return getListResponseEntityPriceForOne(hotels);
    }
    @GetMapping("/findByPriceBetweenTwoPersonsAndCityName") //--------------------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findByPriceBetweenTwoPersonsAndCityName(Float price1, Float price2, String name) {
        final var hotels = hotelRepository.queryByHotelPrices_PriceTwoBetweenAndCity_Name(price1, price2, name.trim().toUpperCase());
        return getListResponseEntityPriceTwo(hotels);
    }
    @GetMapping("/findByPriceBetweenThreePersonsAndCityName") //--------------------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findByPriceBetweenThreePersonsAndCityName(Float price1, Float price2, String name) {
        final var hotels = hotelRepository.queryByHotelPrices_PriceThreeBetweenAndCity_Name(price1, price2, name.trim().toUpperCase());
        return getListResponseEntityPriceThree(hotels);
    }
    @GetMapping("/findByPriceBetweenFourPersonsAndCityName") //--------------------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findByPriceBetweenFourPersonsAndCityName(Float price1, Float price2, String name) {
        final var hotels = hotelRepository.queryByHotelPrices_PriceFourBetweenAndCity_Name(price1, price2, name.trim().toUpperCase());
        return getListResponseEntityPriceFour(hotels);
    }
    @GetMapping("/findByPriceBetweenFivePersonsAndCityName") //--------------------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findByPriceBetweenFivePersonsAndCityName(Float price1, Float price2, String name) {
        final var hotels = hotelRepository.queryByHotelPrices_PriceFiveBetweenAndCity_Name(price1, price2, name.trim().toUpperCase());
        return getListResponseEntityPriceFive(hotels);
    }

    @GetMapping("/findHotelPriceOneByCityAndStateId") //--------------------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findHotelPriceOneByCityAndStateId(Long city_id, Long state_id) {
        final var hotels = hotelRepository.queryHotelsByCity_IdAndCity_State_Id(city_id,state_id);
        return getListResponseEntityPriceForOne(hotels);
    }
    @GetMapping("/findHotelPriceTwoByCityAndStateId") //--------------------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findHotelPriceTwoByCityAndStateId(Long city_id, Long state_id) {
        final var hotels = hotelRepository.queryHotelsByCity_IdAndCity_State_Id(city_id,state_id);
        return getListResponseEntityPriceTwo(hotels);
    }
    @GetMapping("/findHotelPriceThreeByCityAndStateId") //--------------------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findHotelPriceThreeByCityAndStateId(Long city_id, Long state_id) {
        final var hotels = hotelRepository.queryHotelsByCity_IdAndCity_State_Id(city_id,state_id);
        return getListResponseEntityPriceThree(hotels);
    }
    @GetMapping("/findHotelPriceFourByCityAndStateId") //--------------------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findHotelPriceFourByCityAndStateId(Long city_id, Long state_id) {
        final var hotels = hotelRepository.queryHotelsByCity_IdAndCity_State_Id(city_id,state_id);
        return getListResponseEntityPriceFour(hotels);
    }
    @GetMapping("/findHotelPriceFiveByCityAndStateId") //--------------------------------------------------------------------
    public ResponseEntity<List<HotelsListInCityResponse>> findHotelPriceFiveByCityAndStateId(Long city_id, Long state_id) {
        final var hotels = hotelRepository.queryHotelsByCity_IdAndCity_State_Id(city_id,state_id);
        return getListResponseEntityPriceFive(hotels);
    }


//    @GetMapping("/findHotelPriceOneByCityAndStateIdAndPrice") //--------------------------------------------------------------------
//    public ResponseEntity<List<HotelsListInCityResponse>> findHotelPriceOneByCityAndStateIdAndPrice(Long city_id, Long state_id, ) {
//        final var hotels = hotelRepository.queryHotelsByCity_IdAndCity_State_Id(city_id,state_id);
//        return getListResponseEntityPriceForOne(hotels);
//    }

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
    private ResponseEntity<List<HotelsListInCityResponse>> getListResponseEntityPriceTwo(List<Hotels> hotels) {
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
                                hotel1.getHotelPrices().getPriceTwo()),
                        hotel1.getStar()
                )));
        return  ResponseEntity.ok(hotelsList);
    }
    private ResponseEntity<List<HotelsListInCityResponse>> getListResponseEntityPriceThree(List<Hotels> hotels) {
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
                                hotel1.getHotelPrices().getPriceThree()),
                        hotel1.getStar()
                )));
        return  ResponseEntity.ok(hotelsList);
    }
    private ResponseEntity<List<HotelsListInCityResponse>> getListResponseEntityPriceFour(List<Hotels> hotels) {
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
                                hotel1.getHotelPrices().getPriceFour()),
                        hotel1.getStar()
                )));
        return  ResponseEntity.ok(hotelsList);
    }
    private ResponseEntity<List<HotelsListInCityResponse>> getListResponseEntityPriceFive(List<Hotels> hotels) {
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
                                hotel1.getHotelPrices().getPriceFive()),
                        hotel1.getStar()
                )));
        return  ResponseEntity.ok(hotelsList);
    }

}