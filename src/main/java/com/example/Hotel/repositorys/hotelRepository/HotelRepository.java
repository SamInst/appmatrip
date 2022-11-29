package com.example.Hotel.repositorys.hotelRepository;

import com.example.Hotel.model.hotel.Hotels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotels, Long> {
    @Query(value = "select u from Hotels u where upper(trim(u.name)) like %?1%")
    List<Hotels> findByName (String name);
    List<Hotels> queryHotelsByHotelPrices_PriceOneBetween(Optional<Float> price1, Optional<Float> price2);
    List<Hotels> queryHotelsByHotelPrices_PriceTwoBetween(Optional<Float> price1, Optional<Float> price2);
    List<Hotels> queryHotelsByHotelPrices_PriceThreeBetween(Optional<Float> price1, Optional<Float> price2);
    List<Hotels> queryHotelsByHotelPrices_PriceFourBetween(Optional<Float> price1, Optional<Float> price2);
    List<Hotels> queryHotelsByHotelPrices_PriceFiveBetween(Optional<Float> price1, Optional<Float> price2);

    //------------------------------------------------------------------------------------------------------------------
//    @Query("select u from Hotels u where upper(trim(u.name)) like %?1% and u.city.id = :city_id and u.city.state.id = :state_id and u.hotelPrices.priceOne between :price1 and :price2")
    List<Hotels> queryHotelsByCity_IdAndCity_State_IdAndNameAndHotelPrices_PriceOneBetween(Long city_id, Long state_id, String name,  Float price1, Float price2);
    List<Hotels> queryHotelsByCity_IdAndCity_State_IdAndNameAndHotelPrices_PriceTwoBetween(Long city_id, Long state_id, String name,  Float price1, Float price2);
    List<Hotels> queryHotelsByCity_IdAndCity_State_IdAndNameAndHotelPrices_PriceThreeBetween(Long city_id, Long state_id, String name,  Float price1, Float price2);
    List<Hotels> queryHotelsByCity_IdAndCity_State_IdAndNameAndHotelPrices_PriceFourBetween(Long city_id, Long state_id, String name,  Float price1, Float price2);
    List<Hotels> queryHotelsByCity_IdAndCity_State_IdAndNameAndHotelPrices_PriceFiveBetween(Long city_id, Long state_id, String name,  Float price1, Float price2);

    List<Hotels> queryHotelsByCity_IdAndCity_State_Id (Long city_id, Long state_id);
    List<Hotels> queryHotelsByCity_IdAndCity_State_IdAndHotelPrices_PriceOneBetween (Long city_id, Long state_id, Float price1, Float price2);

    @Query(value = "select u from Hotels u where u.Destaque = 1 ")
    List<Hotels> queryHotelsByDestaque (Integer destaque);
}