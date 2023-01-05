package com.example.Hotel.services.outrosServices;

import com.example.Hotel.controllers.PackageResponse;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.model.hotel.Packages;
import com.example.Hotel.repositorys.outrosRepository.PackageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class PackageService {
    Float priceHotel;
    Float pricePasseio;

    private final PackageRepository packageRepository;

    public PackageService(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    public ResponseEntity<List<PackageResponse>> JajaDecidoONome(LocalDate dataEntry, LocalDate dataOut, Integer quantidadePessoa, Long id){
        List<Packages> packageList = new ArrayList<>(packageRepository.queryPackagesById(id));
        return getListPackagePrice(dataEntry,dataOut, quantidadePessoa, packageList);
    }

    private ResponseEntity<List<PackageResponse>> getListPackagePrice(LocalDate dataEntry, LocalDate dataOut, Integer quantidadePessoa, List<Packages> packages) {
        List<PackageResponse> packageList = new ArrayList<>();
        packages.forEach(packages1 ->
        {
            if (quantidadePessoa == 1){
                priceHotel = packages1.getHotels().getHotelPrices().getPriceOne();
                pricePasseio = packages1.getPasseios().getPasseiosPrecos().getPriceOne();
            } else if (quantidadePessoa == 2) {
                priceHotel = packages1.getHotels().getHotelPrices().getPriceTwo();
                pricePasseio = packages1.getPasseios().getPasseiosPrecos().getPriceOne()*2;
            } else if (quantidadePessoa == 3) {
                priceHotel = packages1.getHotels().getHotelPrices().getPriceThree();
                pricePasseio = packages1.getPasseios().getPasseiosPrecos().getPriceOne()*3;
            }else if (quantidadePessoa == 4) {
                priceHotel = packages1.getHotels().getHotelPrices().getPriceFour();
                pricePasseio = packages1.getPasseios().getPasseiosPrecos().getPriceOne()*4;
            }else if (quantidadePessoa == 5) {
                priceHotel = packages1.getHotels().getHotelPrices().getPriceFive();
                pricePasseio = packages1.getPasseios().getPasseiosPrecos().getPriceOne()*5;
            } else {
                throw new EntityNotFound("Tamanho não suportado");
            }
            Integer p1 = Period.between(dataEntry, dataOut).getDays();
            var totalHotel = p1 * priceHotel;
            Float total = totalHotel + pricePasseio;

            packageList.add(new PackageResponse(
                    new PackageResponse.City(
                    packages1.getHotels().getCity().getName(),
                            packages1.getHotels().getCity().getState().getName()),
                    new PackageResponse.Passeio(
                            packages1.getPasseios().getNomePasseio(),
                            packages1.getPasseios().getDescricao(),
                            packages1.getPasseios().getEstrela()),
                    new PackageResponse.Hotels(
                            packages1.getHotels().getName(),
                            packages1.getHotels().getAddress(),
                            packages1.getHotels().getHotelDescription(),
                            packages1.getHotels().getStar()),
                    new PackageResponse.Informacoes(
                      quantidadePessoa,
                      p1,
                      packages1.getPasseios().getPasseiosPrecos().getPriceOne(),
                      priceHotel,
                      pricePasseio,
                      totalHotel
                    ),
                    total
            ));
        } );
        return ResponseEntity.ok(packageList);
    }
}
