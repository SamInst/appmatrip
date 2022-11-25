//package com.example.Hotel.controllers.hotelController;
//
//import com.example.Hotel.controllers.hotelController.responses.hotelResponses.request.hotelRequest.HotelPrecoResponse;
//import com.example.Hotel.exceptions.EntityInUse;
//import com.example.Hotel.exceptions.EntityNotFound;
//import com.example.Hotel.model.hotel.HotelPrecos;
//import com.example.Hotel.repositorys.hotelRepository.HotelPrecosRepository;
//import com.example.Hotel.services.hotelServices.HotelPrecosRegistrationService;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/hotel_prices")
//public class HotelPrecoController {
//        @Autowired
//        private HotelPrecosRepository hotelPricesRepository;
//        @Autowired
//        private HotelPrecosRegistrationService hotelPricesRegistrationService;
//        @GetMapping
//        public List<HotelPrecos> list() {
//            return hotelPricesRepository.all();
//        }
//
//        @GetMapping("/{hotelPriceId}")
//        public ResponseEntity<HotelPrecoResponse> find(@PathVariable("hotelPriceId") Long id) {
//            HotelPrecos hotelPrices = hotelPricesRepository.perId(id);
//            if (hotelPrices != null) {
//                final var response = new HotelPrecoResponse(
//                        hotelPrices.getPriceOne(),
//                        hotelPrices.getPriceTwo(),
//                        hotelPrices.getPriceThree(),
//                        hotelPrices.getPriceFour(),
//                        hotelPrices.getPriceFive()
//                );
//                return ResponseEntity.ok(response);
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        }
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping
//    public HotelPrecos add(@RequestBody HotelPrecos hotelPrices) {
//            return hotelPricesRegistrationService.add(hotelPrices);
//        }
//    @PutMapping("/{hotelPriceId}")
//    public ResponseEntity<HotelPrecos> update(@PathVariable Long hotelPriceId, @RequestBody HotelPrecos hotelPrices) {
//        HotelPrecos hotelPrices1 = hotelPricesRepository.perId(hotelPriceId);
//        if (hotelPrices1 != null) {
//            BeanUtils.copyProperties(hotelPrices1, hotelPrices, "id");
//            hotelPrices1 = hotelPricesRegistrationService.add(hotelPrices1);
//            return ResponseEntity.ok(hotelPrices1);
//
//        }
//        return ResponseEntity.notFound().build();
//
//    }
//    @DeleteMapping("/{hotelPriceId}")
//    public ResponseEntity<HotelPrecos> remove(@PathVariable Long hotelPriceId) {
//        try {
//            hotelPricesRegistrationService.exclude(hotelPriceId);
//            return ResponseEntity.noContent().build();
//        } catch (EntityNotFound e) {
//            return ResponseEntity.notFound().build();
//        } catch (EntityInUse e) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
//    }
//}
