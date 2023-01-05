//package com.example.Hotel.services.hotelServices;
//
//import com.example.Hotel.controllers.hotelController.responses.hotelResponses.request.hotelRequest.PhotoRequest;
//import com.example.Hotel.exceptions.EntityInUse;
//import com.example.Hotel.exceptions.EntityNotFound;
//import com.example.Hotel.model.hotel.HotelFotos;
//import com.example.Hotel.model.hotel.Hotels;
//import com.example.Hotel.repositorys.hotelRepository.HotelRepository;
//import com.example.Hotel.services.outrosServices.ServerUtil;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class TodasImagensDoHotelRegistrationService {
//    private final TodasImagensDoHotelRepository allImagesRepository;
//
//    private final HotelRepository hotelRepository;
//
//    public TodasImagensDoHotelRegistrationService(TodasImagensDoHotelRepository allImagesRepository, HotelRepository hotelRepository) {
//        this.allImagesRepository = allImagesRepository;
//        this.hotelRepository = hotelRepository;
//    }
//
//    public HotelFotos add(PhotoRequest photoRequest) {
//        Hotels hotels = hotelRepository.findById(photoRequest.idHotel()).orElseThrow(()->
//                new EntityNotFound("Hotel not found"));
//
//        return allImagesRepository.save(
//                new HotelFotos(photoRequest.title(), ServerUtil.convertAndSave(photoRequest), hotels));
//
//    }
//    public void exclude(Long booksId) {
//       try {
//           allImagesRepository.deleteById(booksId);
//       } catch (EmptyResultDataAccessException e){
//           throw new EntityNotFound("image code % not found" + booksId);
//       } catch (DataIntegrityViolationException e) {
//           throw new EntityInUse("image code % could be not removed," + booksId);
//       }
//   }
//}