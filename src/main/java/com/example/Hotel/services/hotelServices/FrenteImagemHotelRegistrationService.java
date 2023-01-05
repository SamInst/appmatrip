//package com.example.Hotel.services.hotelServices;
//
//import com.example.Hotel.controllers.hotelController.responses.hotelResponses.request.hotelRequest.PhotoRequest;
//import com.example.Hotel.exceptions.EntityInUse;
//import com.example.Hotel.exceptions.EntityNotFound;
//import com.example.Hotel.model.hotel.HotelFrenteFoto;
//import com.example.Hotel.model.hotel.Hotels;
//import com.example.Hotel.repositorys.hotelRepository.HotelRepository;
//import com.example.Hotel.services.outrosServices.ServerUtil;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class FrenteImagemHotelRegistrationService {
//    //@Autowired
//    private FrenteImageHotelRepository frontalImageHotelRepository;
//
//    private HotelRepository hotelRepository;
//
//    public FrenteImagemHotelRegistrationService(FrenteImageHotelRepository frontalImageHotelRepository, HotelRepository hotelRepository) {
//        this.frontalImageHotelRepository = frontalImageHotelRepository;
//        this.hotelRepository = hotelRepository;
//    }
//
//    public HotelFrenteFoto add(PhotoRequest photoRequest) {
//        Hotels hotels = hotelRepository.findById(photoRequest.idHotel()).orElseThrow(()->
//                new EntityNotFound("Hotel not found"));
//
//
//
//
//        HotelFrenteFoto hotelPhotos = frontalImageHotelRepository.save(
//                new HotelFrenteFoto(photoRequest.title(), ServerUtil.convertAndSave(photoRequest), hotels));
//
//        return hotelPhotos;
//
//    }
//    public void exclude(Long booksId) {
//       try {
//           frontalImageHotelRepository.deleteById(booksId);
//       } catch (EmptyResultDataAccessException e){
//           throw new EntityNotFound("image not found" + booksId);
//       } catch (DataIntegrityViolationException e) {
//           throw new EntityInUse("image could be not removed," + booksId);
//       }
//   }
//}