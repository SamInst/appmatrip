package com.example.Hotel.services;

import com.example.Hotel.controllers.hotelController.responses.request.hotelRequest.PhotoRequest;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
public class ServerUtil {

    //private final Logger log = LoggerFactory.getLogger(this.getClass());
    public static String server = SystemUtils.IS_OS_LINUX ? "/home/TCC" : System.getProperty("user.home") +
            "/TCC";
    public static String getDirectoryHotelServer(PhotoRequest photoRequest) {
        String dir = "/Matrip_Hotel/" + photoRequest.title() + "/";
        //log.info("Caminho da foto:{}",dir);
        return dir;
    }
    public static String getRandomFileName() {
        return UUID.randomUUID().toString();
    }
    public static String savePhotoReturnPath(PhotoRequest photoRequest) {
        return convertAndSave(photoRequest);
    }
    public static String convertAndSave(PhotoRequest photoRequest) {
        String base64 = photoRequest.photo();

        final var server = new StringBuilder()
                .append(ServerUtil.server);
        final var hotelPath = new StringBuilder()
                .append(ServerUtil.getDirectoryHotelServer(photoRequest));

        String pathPhoto = server.toString() + hotelPath;

        final var documentArchive = new File(pathPhoto);
        byte[] bytesPhoto = Base64.getDecoder().decode(base64);
        String fileName = ServerUtil.getRandomFileName() +
                LocalDateTime.now() +
                "." +
                "png";
        documentArchive.mkdirs();
        File ImageArchive = new File(pathPhoto, fileName);
        try {
            FileOutputStream imageStream = new FileOutputStream(ImageArchive);
            imageStream.write(bytesPhoto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String ImageWay = pathPhoto + fileName;
        //log.info("Caminho da foto:" + caminhoDaImagem);
        return ImageWay;
    }
}
