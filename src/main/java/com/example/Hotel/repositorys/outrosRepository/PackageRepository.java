package com.example.Hotel.repositorys.outrosRepository;


import com.example.Hotel.model.hotel.Packages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PackageRepository extends JpaRepository <Packages, Long> {

    List<Packages> queryPackagesById (Long id);


}
