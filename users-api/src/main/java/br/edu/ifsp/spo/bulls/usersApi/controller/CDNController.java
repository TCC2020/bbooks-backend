package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.domain.CDNEnum;
import br.edu.ifsp.spo.bulls.usersApi.service.ProfileService;
import br.edu.ifsp.spo.bulls.usersApi.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/cdn")
@CrossOrigin(origins = "*")
public class CDNController {
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ProfileService profileService;

    Storage storage = StorageOptions.getDefaultInstance().getService();

//    Storage storage = StorageOptions.newBuilder()
//            .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("/home/feloureiro/Downloads/key.json")))
//            .build()
//            .getService();

    public CDNController() throws IOException {
    }

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public HttpStatus upload(
            @RequestHeader(value = "AUTHORIZATION") String token,
            @RequestPart(value = "info", required = true) String info,
            @RequestPart(value = "file", required = true) MultipartFile file) throws IOException {
        Map<String, Object> map = mapper.readValue(info, new TypeReference<Map<String, Object>>() {
        });
        String fileName = file.getOriginalFilename();
        CDNEnum objectType = CDNEnum.getByString((String) map.get("objectType"));

        try {
            BlobInfo blobInfo = storage.create(
                    BlobInfo.newBuilder("cdn-bbooks", file.getOriginalFilename()).build(), //get original file name
                    file.getBytes(), // the file
                    Storage.BlobTargetOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ) // Set file permission
            );
            return this.handleUpload(objectType, blobInfo.getMediaLink(), token); // Return file url
        }catch(IllegalStateException e){
            throw new RuntimeException(e);
        }
    }

    private HttpStatus handleUpload(CDNEnum objectType, String url, String token) {
        switch (objectType) {
            case profile_image :
                return profileService.updateProfileImage(url, token);
            default:
                return HttpStatus.CONFLICT;
        }
    }
}
