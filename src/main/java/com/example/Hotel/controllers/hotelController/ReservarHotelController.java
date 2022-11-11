package com.example.Hotel.controllers.hotelController;

import com.example.Hotel.controllers.hotelController.responses.hotelResponses.request.hotelRequest.ReservarHotelResponse;
import com.example.Hotel.exceptions.EntityDates;
import com.example.Hotel.exceptions.EntityInUse;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.model.hotel.ReservarHotel;
import com.example.Hotel.repositorys.hotelRepository.ReservasHotelRepository;
import com.example.Hotel.services.hotelServices.ReservaRegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@RestController
@RequestMapping("/booksHotel")
public class ReservarHotelController {
    @Autowired
    private ReservasHotelRepository booksRepository;
    @Autowired
    private ReservaRegistrationService booksRegistrationService;
    @GetMapping
    public List<ReservarHotel> list() {
        return booksRepository.all();
    }
    @GetMapping("/{booksId}")
    public ResponseEntity<ReservarHotelResponse> find(@PathVariable("booksId") Long id) {
        ReservarHotel books = booksRepository.perId(id);
        int amountPeople = books.getAdults() + books.getChilds();

        switch (amountPeople) {
            case 1 -> {
                Float hotelPrice = books.getHotels().getHotelPrices().getPriceOne();
                LocalDate dataEntry = books.getDataEntry();
                LocalDate dataOut = books.getDataOut();
                return getBooksResponseResponseEntity(books, amountPeople, dataEntry, dataOut, hotelPrice);
            }
            case 2 -> {
                LocalDate dataEntry = books.getDataEntry();
                LocalDate dataOut = books.getDataOut();
                Float hotelPrice = books.getHotels().getHotelPrices().getPriceTwo();
                return getBooksResponseResponseEntity(books, amountPeople, dataEntry, dataOut, hotelPrice);
            }
            case 3 ->{
                LocalDate dataEntry = books.getDataEntry();
                LocalDate dataOut = books.getDataOut();
                Float hotelPrice = books.getHotels().getHotelPrices().getPriceThree();
                return getBooksResponseResponseEntity(books, amountPeople, dataEntry, dataOut, hotelPrice);
            }
            case 4 -> {
                LocalDate dataEntry = books.getDataEntry();
                LocalDate dataOut = books.getDataOut();
                Float hotelPrice = books.getHotels().getHotelPrices().getPriceFour();
                return getBooksResponseResponseEntity(books, amountPeople, dataEntry, dataOut, hotelPrice);
            }
            case 5 ->{
                LocalDate dataEntry = books.getDataEntry();
                LocalDate dataOut = books.getDataOut();
                Float hotelPrice = books.getHotels().getHotelPrices().getPriceFive();
                return getBooksResponseResponseEntity(books, amountPeople, dataEntry, dataOut, hotelPrice);
            }
    } return ResponseEntity.ok().build();
}
    private ResponseEntity<ReservarHotelResponse> getBooksResponseResponseEntity(ReservarHotel books, int amountPeople, LocalDate dataEntry, LocalDate dataOut, Float hotelPrice) {
        Integer p1 = Period.between(dataEntry, dataOut).getDays();
        Float count = p1 * hotelPrice;
        System.out.println(p1);
        System.out.println(hotelPrice);
        System.out.println(count);
        System.out.println(amountPeople);

        final var response = new ReservarHotelResponse(
                books.getHotels().getName(),
                new ReservarHotelResponse.City(
                        books.getHotels().getCity().getName(),
                        books.getHotels().getCity().getState().getName()),
                new ReservarHotelResponse.Client(
                        books.getClient().getUsername(),
                        books.getClient().getCpf(),
                        books.getClient().getPhone(),
                        books.getClient().getEmail(),
                        books.getClient().getAddress(),
                        books.getDataEntry(),
                        books.getDataOut()
                ),
                books.getAdults(),
                books.getChilds(),
                new ReservarHotelResponse.Hotel((float) p1, hotelPrice, count)
        );
        return ResponseEntity.ok(response);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<?> add(@RequestBody ReservarHotel books){
            try {
                LocalDate today = LocalDate.now();
                LocalDate dataEntry = books.getDataEntry();
                LocalDate dataOut = books.getDataOut();
// se a data ja esta reservada em determinado quarto, bloquear os campos
                        if ((dataEntry.isBefore(today)) || dataOut.isBefore(today) || dataEntry.equals(dataOut)){
                            throw new EntityDates("The date entered cannot be less than today, or in the same day");
                        } else {
                            books = booksRegistrationService.add(books);
                        }
                return ResponseEntity.status(HttpStatus.CREATED).body(books);
            } catch (EntityNotFound e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
    }
    @PutMapping("/{booksId}")
    public ResponseEntity<ReservarHotel> update(@PathVariable Long booksId, @RequestBody ReservarHotel books) {
       try {
        ReservarHotel books1 = booksRepository.perId(booksId);
        if (books1 != null) {
            BeanUtils.copyProperties(books, books1, "id", "hotels","hotelPrices","client");
            return ResponseEntity.ok(booksRegistrationService.add(books1));
        }
        return ResponseEntity.notFound().build();
    } catch (EntityNotFound e){
           return ResponseEntity.notFound().build();
       }
    }
    @DeleteMapping("/{booksId}")
    public ResponseEntity<ReservarHotel> remove(@PathVariable Long booksId) {
        try {
            booksRegistrationService.exclude(booksId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFound e) {
            return ResponseEntity.notFound().build();
        } catch (EntityInUse e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}