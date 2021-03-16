package br.edu.ifsp.spo.bulls.feed.api.controller;

import br.edu.ifsp.spo.bulls.common.api.enums.CDNEnum;
import br.edu.ifsp.spo.bulls.feed.api.feign.CompetitionCommonFeign;
import br.edu.ifsp.spo.bulls.feed.api.service.PostService;
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

    private final Storage storage = StorageOptions.getDefaultInstance().getService();

    @Autowired
    private CompetitionCommonFeign competitionCommonFeign;

    @Autowired
    private PostService postService;

//    Storage storage = StorageOptions.newBuilder()
//            .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("/home/feloureiro/Downloads/key.json")))
//            .build()
//            .getService();


    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public HttpStatus upload(
            @RequestHeader(value = "AUTHORIZATION") String token,
            @RequestPart(value = "info", required = true) String info,
            @RequestPart(value = "file", required = true) MultipartFile file) throws IOException {
        Map<String, Object> infoMap = mapper.readValue(info, new TypeReference<Map<String, Object>>() {
        });
        String fileName = file.getOriginalFilename();

        System.out.println("opts " +StorageOptions.getDefaultInstance().getCredentials());

        try {
            BlobInfo blobInfo = storage.create(
                    BlobInfo.newBuilder("cdn-bbooks", fileName).build(), //get original file name
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
            case book_ad_id :
                return competitionCommonFeign.uploadImage(url, UUID.fromString(infoMap.get("bookAdId").toString()), token);
            case post_image :
                return postService.setImage(token, UUID.fromString(infoMap.get("postId").toString()), url);
            default:
                return HttpStatus.CONFLICT;
        }
    }
}