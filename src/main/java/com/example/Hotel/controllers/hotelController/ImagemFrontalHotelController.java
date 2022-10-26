package com.example.Hotel.controllers.hotelController;

import com.example.Hotel.controllers.hotelController.responses.request.hotelRequest.FrontalPhotoRequest;
import com.example.Hotel.controllers.hotelController.responses.request.hotelRequest.PhotoRequest;
import com.example.Hotel.model.hotel.HotelFrenteFoto;
import com.example.Hotel.repositorys.hotelRepository.FrenteImageHotelRepository;
import com.example.Hotel.services.hotelServices.FrenteImagemHotelRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/hotel/frontal_image")
public class ImagemFrontalHotelController {
    private final FrenteImageHotelRepository frontalImageHotelRepository;

   private final FrenteImagemHotelRegistrationService frontalImageHotelRegistrationService;

    public ImagemFrontalHotelController(FrenteImageHotelRepository frontalImageHotelRepository, FrenteImagemHotelRegistrationService frontalImageHotelRegistrationService) {
        this.frontalImageHotelRepository = frontalImageHotelRepository;
        this.frontalImageHotelRegistrationService = frontalImageHotelRegistrationService;
    }

    @GetMapping
    public List<HotelFrenteFoto> list() {return frontalImageHotelRepository.findAll();}

    @GetMapping("/{frontalImagesId}") //----------------------------------------------------------------------------------
    public ResponseEntity<List<FrontalPhotoRequest>> frontalFind(@PathVariable("frontalImagesId") Long frontalImagesId) {
        final var image = frontalImageHotelRepository.findByHotels_Id(frontalImagesId);
        if (!image.isEmpty()){
               List<FrontalPhotoRequest> response = new ArrayList<>();
        image.forEach(
                (img) -> {response.add(new FrontalPhotoRequest(img.getId(), img.getTitle(), img.getPath()));}
        );
            return ok(response);
        } else {
            return notFound().build();
        }
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping //-----------------------------------------------------------------------------------------------
    public HotelFrenteFoto add2(@RequestBody PhotoRequest photoRequest) {
        return frontalImageHotelRegistrationService.add(photoRequest);
    }

}