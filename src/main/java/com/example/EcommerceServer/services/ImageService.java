
package com.example.EcommerceServer.services;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class ImageService {


    private final String PRODUCT_IMAGE_UPLOAD_PATH="C:/Users/Thakkallapally.Amrut/EcommerceServer/src/main/resources/assets/snapcart_products/";


    public List<String> uploadImages(MultipartFile[] files) throws IOException {
        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("Please select images to upload.");
        }


        List<String> filenames = new ArrayList<>();
        File uploadDir = new File(PRODUCT_IMAGE_UPLOAD_PATH);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }


        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                File destinationFile = new File(PRODUCT_IMAGE_UPLOAD_PATH + file.getOriginalFilename());
                file.transferTo(destinationFile);


                if (destinationFile.exists()) {
                    String filenameWithoutExt = file.getOriginalFilename().replaceAll("\\.[^.]+$", "");
                    filenames.add(filenameWithoutExt);
                } else {
                    throw new IOException("File upload failed for: " + file.getOriginalFilename());
                }
            }
        }
        return filenames;
    }
}
