package br.edu.ifsp.spo.bulls.users.api.controller;

import br.edu.ifsp.spo.bulls.users.api.enums.CDNEnum;
import br.edu.ifsp.spo.bulls.users.api.service.BookService;
import br.edu.ifsp.spo.bulls.users.api.service.ProfileService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/cdn")
@CrossOrigin(origins = "*")
public class CDNController {
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ProfileService profileService;

    @Autowired
    private BookService bookService;

    private Storage storage = StorageOptions.getDefaultInstance().getService();

//    Storage storage = StorageOptions.newBuilder()
//            .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("C:/Users/AndreNascimentodeFre/Downloads/key.json")))
//            .build()
//            .getService();

    public CDNController() throws IOException {
    }

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public HttpStatus upload(
            @RequestHeader(value = "AUTHORIZATION") String token,
            @RequestPart(value = "info", required = true) String info,
            @RequestPart(value = "file", required = true) MultipartFile file) throws IOException {
        Map<String, Object> infoMap = mapper.readValue(info, new TypeReference<Map<String, Object>>() {
        });
        String fileName = file.getOriginalFilename();

        try {
            BlobInfo blobInfo = storage.create(
                    BlobInfo.newBuilder("cdn-bbooks", file.getOriginalFilename()).build(), //get original file name
                    file.getBytes(), // the file
                    Storage.BlobTargetOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ) // Set file permission
            );
            return this.handleUpload(blobInfo.getMediaLink(), StringUtils.removeStart(token, "Bearer").trim(), infoMap); // Return file url
        }catch(IllegalStateException e){
            throw new RuntimeException(e);
        }
    }

    private HttpStatus handleUpload(String url, String token, Map<String, Object>infoMap) {
        CDNEnum objectType = CDNEnum.getByString((String) infoMap.get("objectType"));
        switch (objectType) {
            case profile_image :
                return profileService.updateProfileImage(url, token);
            case book_image :
                return bookService.updateBookImage(url, (int) infoMap.get("bookId"));
            default:
                return HttpStatus.CONFLICT;
        }
    }
}
