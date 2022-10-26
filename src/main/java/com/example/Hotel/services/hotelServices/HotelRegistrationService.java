package com.example.Hotel.services.hotelServices;

import com.example.Hotel.model.hotel.Hotels;
import com.example.Hotel.exceptions.EntityInUse;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.repositorys.hotelRepository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class HotelRegistrationService {
    @Autowired
    private HotelRepository hotelRepository;

    public Hotels add(Hotels hotels) {
        return hotelRepository.save(hotels);
    }
    private void converteEsalva(String photo) {
//        public static String converteESalva(DiarioFrequenciaRecordRequest diarioRequest) {
//            String base64 = diarioRequest.imageDetento();
//
//            final var servidor = new StringBuilder()
//                    .append(ServidorUtil.servidor);
//            final var detentoPath = new StringBuilder()
//                    .append(ServidorUtil.getDiretorioDetentoServidor(diarioRequest))
//                    .append(tipoEntrada(diarioRequest));
//
//            String pathFoto = servidor.toString() + detentoPath;
//
//            final var documentarArquivo = new File(pathFoto);
//            byte[] bytesDaFoto = Base64.getDecoder().decode(base64);
//            String nomeArquivo = ServidorUtil.getRandomFileName() +
//                    LocalDateTime.now() +
//                    "." +
//                    "png";
//
//            if (!documentarArquivo.exists()) {
//                documentarArquivo.mkdirs();
//            }
//
//            File arquivoImagem = new File(pathFoto, nomeArquivo);
//
//            try {
//                FileOutputStream fluxoDeImagem = new FileOutputStream(arquivoImagem);
//                fluxoDeImagem.write(bytesDaFoto);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            String caminhoDaImagem =  detentoPath + "/" + nomeArquivo;
//            LOGGER.info("Caminho da foto:" + caminhoDaImagem);
//
//            return caminhoDaImagem;
//        }
    }

    public void exclude(Long hotelId) {
       try {
           hotelRepository.deleteById(hotelId);
       } catch (EmptyResultDataAccessException e){
           throw new EntityNotFound("Hotel code % not found" + hotelId);
       } catch (DataIntegrityViolationException e) {
           throw new EntityInUse("Hotel code % could be not removed," + hotelId);
       }
   }
}