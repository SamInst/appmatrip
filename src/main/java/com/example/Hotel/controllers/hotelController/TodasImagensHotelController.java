//package com.example.Hotel.controllers.hotelController;
//
//import com.example.Hotel.controllers.hotelController.responses.hotelResponses.request.hotelRequest.PhotoRequest;
//import com.example.Hotel.model.hotel.HotelFotos;
//import com.example.Hotel.repositorys.hotelRepository.TodasImagensDoHotelRepository;
//import com.example.Hotel.services.hotelServices.TodasImagensDoHotelRegistrationService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.springframework.http.ResponseEntity.notFound;
//import static org.springframework.http.ResponseEntity.ok;
//
//@RestController
//@RequestMapping("/hotel/images")
//public class TodasImagensHotelController {
//
//
//    private TodasImagensDoHotelRepository allImagesRepository;
//
//
//    private TodasImagensDoHotelRegistrationService allImagesRegistrationService;
//
//    public TodasImagensHotelController(TodasImagensDoHotelRepository allImagesRepository, TodasImagensDoHotelRegistrationService allImagesRegistrationService) {
//        this.allImagesRepository = allImagesRepository;
//        this.allImagesRegistrationService = allImagesRegistrationService;
//    }
//
//    @GetMapping
//    public List<HotelFotos> list() {
//        return allImagesRepository.findAll();
//    }
//
//
//    @GetMapping("/{imagesId}") //----------------------------------------------------------------------------------
//    public ResponseEntity<List<PhotoRequest>> find(@PathVariable("imagesId") Long imagesId) {
//        final var image = allImagesRepository.findByHotels_Id(imagesId);
//        if (!image.isEmpty()){
//               List<PhotoRequest> response = new ArrayList<>();
//        image.forEach(
//                (img) -> {response.add(new PhotoRequest(img.getId(), img.getTitle(), img.getPath()));}
//        );
//            return ok(response);
//        } else {
//            return notFound().build();
//        }
//    }
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping //-----------------------------------------------------------------------------------------------
//    public HotelFotos add2(@RequestBody PhotoRequest photoRequest) {
//        return allImagesRegistrationService.add(photoRequest);
//    }
//
//}