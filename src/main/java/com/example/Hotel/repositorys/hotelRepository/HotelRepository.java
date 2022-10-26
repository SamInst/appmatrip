package com.example.Hotel.repositorys.hotelRepository;

import com.example.Hotel.model.hotel.Hotels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotels, Long> {
    @Query(value = "select u from Hotels u where upper(trim(u.name)) like %?1%")
    List<Hotels> findByName (String name);
    List<Hotels>findHotelsByCityId(Long id);
    @Query(value = "select u from Hotels u join u.city  where upper(trim(u.city.name)) like %?1%")
    List<Hotels> findHotelsByCity_Name(String name);
    //------------------------------------------------------------------------------------------------------------------
    List<Hotels> queryByHotelPrices_PriceOneBetween(Float price1, Float price2);
    List<Hotels> queryByHotelPrices_PriceTwoBetween (Float price1, Float price2);
    List<Hotels> queryByHotelPrices_PriceThreeBetween(Float price1, Float price2);
    List<Hotels> queryByHotelPrices_PriceFourBetween(Float price1, Float price2);
    List<Hotels> queryByHotelPrices_PriceFiveBetween(Float price1, Float price2);
    //------------------------------------------------------------------------------------------------------------------
    List<Hotels> queryByHotelPrices_PriceOneBetweenAndCityId (Float price1, Float price2, Long Id);
    List<Hotels> queryByHotelPrices_PriceTwoBetweenAndCityId (Float price1, Float price2, Long Id);
    List<Hotels> queryByHotelPrices_PriceThreeBetweenAndCityId (Float price1, Float price2, Long Id);
    List<Hotels> queryByHotelPrices_PriceFourBetweenAndCityId (Float price1, Float price2, Long Id);
    List<Hotels> queryByHotelPrices_PriceFiveBetweenAndCityId (Float price1, Float price2, Long Id);
    //------------------------------------------------------------------------------------------------------------------
    @Query(value = "select u from Hotels u join u.city  where u.hotelPrices.priceOne between :price1 and :price2 and upper(trim(u.city.name)) like %:name%")
    List<Hotels> queryByHotelPrices_PriceOneBetweenAndCity_Name (Float price1, Float price2, String name);
    @Query(value = "select u from Hotels u join u.city  where u.hotelPrices.priceTwo between :price1 and :price2 and upper(trim(u.city.name)) like %:name%")
    List<Hotels> queryByHotelPrices_PriceTwoBetweenAndCity_Name (Float price1, Float price2, String name);
    @Query(value = "select u from Hotels u join u.city  where u.hotelPrices.priceThree between :price1 and :price2 and upper(trim(u.city.name)) like %:name%")
    List<Hotels> queryByHotelPrices_PriceThreeBetweenAndCity_Name (Float price1, Float price2, String name);
    @Query(value = "select u from Hotels u join u.city  where u.hotelPrices.priceFour between :price1 and :price2 and upper(trim(u.city.name)) like %:name%")
    List<Hotels> queryByHotelPrices_PriceFourBetweenAndCity_Name (Float price1, Float price2, String name);
    @Query(value = "select u from Hotels u join u.city  where u.hotelPrices.priceFive between :price1 and :price2 and upper(trim(u.city.name)) like %:name%")
    List<Hotels> queryByHotelPrices_PriceFiveBetweenAndCity_Name (Float price1, Float price2, String name);

//    @Query(value = "select a from tb_hotels as t join tb_city as c on t.city_id = c.id / join tb_states as u on c.state_id = u.id where t.categoria = 1 and u.id = 1")
    List<Hotels> queryHotelsByCity_State_IdAndCategoria_Id (Long state_id, Long categoria_id);
}
