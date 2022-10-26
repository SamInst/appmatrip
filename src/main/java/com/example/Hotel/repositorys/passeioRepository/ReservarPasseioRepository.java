package com.example.Hotel.repositorys.passeioRepository;

import com.example.Hotel.model.passeios.ReservarPasseio;
import javax.transaction.Transactional;
import java.util.List;

public interface ReservarPasseioRepository {
    List<ReservarPasseio> all();

    ReservarPasseio perId(Long id);

    @Transactional
    ReservarPasseio add(ReservarPasseio reservarPasseio);

    @Transactional
    void remove(Long reservarPasseio);
}
