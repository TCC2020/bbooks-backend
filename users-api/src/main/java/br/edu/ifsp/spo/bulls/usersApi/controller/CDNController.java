package br.edu.ifsp.spo.bulls.usersApi.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
public class CDNController {
    private final ObjectMapper mapper = new ObjectMapper();

    private static Storage storage = StorageOptions.getDefaultInstance().getService();
    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public String upload(
            @RequestPart(value = "info", required = true) String info,
            @RequestPart(value = "file", required = true) MultipartFile file) throws IOException {
        Map<String, Object> map = mapper.readValue(info, new TypeReference<Map<String, Object>>() {
        });
        String fileName = file.getOriginalFilename();
        String type = (String) map.get("type");

        try {
            BlobInfo blobInfo = storage.create(
                    BlobInfo.newBuilder("cdn-bbooks", file.getOriginalFilename()).build(), //get original file name
                    file.getBytes(), // the file
                    Storage.BlobTargetOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ) // Set file permission
            );
            return blobInfo.getMediaLink(); // Return file url
        }catch(IllegalStateException e){
            throw new RuntimeException(e);
        }
    }
}
