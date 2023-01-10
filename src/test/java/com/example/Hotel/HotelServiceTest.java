package com.example.Hotel;

import com.example.Hotel.controllers.hotelController.responses.hotelResponses.request.hotelRequest.HotelsListInCityResponse2;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.model.hotel.HotelFrenteFoto;
import com.example.Hotel.model.hotel.HotelPrecos;
import com.example.Hotel.model.hotel.Hotels;
import com.example.Hotel.model.hotel.Quartos;
import com.example.Hotel.model.outros.Categoria;
import com.example.Hotel.model.outros.Cidade;
import com.example.Hotel.model.outros.Estado;
import com.example.Hotel.repositorys.hotelRepository.HotelRepository;
import com.example.Hotel.services.hotelServices.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class HotelServiceTest {
    Float price;

    @InjectMocks
    private HotelService hotelService;

    @Mock
    private HotelRepository hotelRepository;
    private Estado estado = new Estado(1L, "Maranhão");

    private Categoria categoria = new Categoria("Hotel");
    private Cidade cidade = new Cidade(1L,"Viana", estado);
    private HotelPrecos hotelPrecos = new HotelPrecos(100F, 200F, 300F,400F,500F);
    List<Hotels> hotels = new ArrayList<>();
    Integer quantidadePessoa = 1;
    LocalDate dataEntry = LocalDate.now();
    LocalDate dataOut = LocalDate.now().plusDays(2);
    Integer rooms = 25;
    Integer star = 5;
    HotelFrenteFoto hotelFrenteFoto;
    Hotels hotels2;
    List<HotelsListInCityResponse2> hotelsListInCityResponse2List = new ArrayList<>();






    private void quantidadeDePessoas(Integer quantidadePessoa, Hotels hotel1) {
        if (this.quantidadePessoa == 1){
            price = hotel1.getHotelPrices().getPriceOne();
        } else if (this.quantidadePessoa == 2) {
            price = hotel1.getHotelPrices().getPriceTwo();
        } else if (this.quantidadePessoa == 3) {
            price = hotel1.getHotelPrices().getPriceThree();
        }else if (this.quantidadePessoa == 4) {
            price = hotel1.getHotelPrices().getPriceFour();
        }else if (this.quantidadePessoa == 5) {
            price = hotel1.getHotelPrices().getPriceFive();
        } else {
            throw new EntityNotFound("Tamanho não suportado");
        }
    }
    @BeforeEach
    public void algo(){


        hotels.add(new Hotels(
                new Categoria("dfghgh"),
                "Pousada teste",
                "000.000.000/000",
                "rua teste ",
                "(00)0 0000-0000",
                "descrição teste",
                new Cidade(1L,"Viana", new Estado(1L, "Maranhão")),
                new HotelPrecos(100F,200F,300F,400F,500F),
                new Quartos(23),
                star,
                1
                ));
        hotels.add(new Hotels(
                new Categoria("dfghgh 2"),
                "Pousada teste 2",
                "000.000.000/000",
                "rua teste ",
                "(00)0 0000-0000",
                "descrição teste",
                new Cidade(1L,"Viana", new Estado(1L, "Maranhão")),
                new HotelPrecos(100F,200F,300F,400F,500F),
                new Quartos(23),
                star,
                1
        ));
        hotels.forEach(hotels1 ->
        {
            quantidadeDePessoas(quantidadePessoa, hotels1);

            Integer p1 = Period.between(dataEntry, dataOut).getDays();
            float total = price * p1;

            hotelsListInCityResponse2List.add(new HotelsListInCityResponse2(
                    new HotelsListInCityResponse2.Categoria(
                            hotels1.getCategoria().getName()),
                    new HotelsListInCityResponse2.City(
                            hotels1.getCity().getName(),
                            hotels1.getCity().getState().getName()),
                    hotels1.getName(),
                    hotels1.getHotelDescription(),
                    hotels1.getAddress(),
                    new HotelsListInCityResponse2.HotelPrices(
                            price),
                    hotels1.getStar(),
                    p1,
                    total
            ));
        });
    }


    @Test
    @DisplayName("Retorna sem erro QueryBy_CityId_StateId_DataEntry_DataOut")
    public void teste(){
        when(hotelRepository.queryHotelsByCity_IdAndCity_State_Id(1L,1L)).thenReturn(hotels);
        hotelService.QueryBy_CityId_StateId_DataEntry_DataOut(LocalDate.now(), LocalDate.now().plusDays(2), 2, 1L, 1L);
    }

    @Test
    @DisplayName("retorna sem erro queryHotelsByName")
    public void teste2(){
        when(hotelRepository.findHotelsByNameAndCity_IdAndCity_State_Id("Pousada", 1L, 1L)). thenReturn(hotels);
        hotelService.queryHotelsByName(LocalDate.now(), LocalDate.now().plusDays(2),quantidadePessoa, 1L, 1L, "Pousada");
    }



}
