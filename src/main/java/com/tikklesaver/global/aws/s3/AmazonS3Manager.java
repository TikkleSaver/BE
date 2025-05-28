package com.tikklesaver.global.aws.s3;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.tikklesaver.global.common.Uuid;
import com.tikklesaver.global.config.AmazonConfig;
import com.tikklesaver.global.repository.UuidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Manager{

    private final AmazonS3 amazonS3;

    private final AmazonConfig amazonConfig;

    private final UuidRepository uuidRepository;

    public String generateMembersKeyName(Uuid uuid) {
        return amazonConfig.getMemebersPath() + '/' + uuid.getUuid();
    }
    public String generateProductsKeyName(Uuid uuid) {
        return amazonConfig.getProductsPath() + '/' + uuid.getUuid();
    }
    public String generateExpensesKeyName(Uuid uuid) {
        return amazonConfig.getExpensesPath() + '/' + uuid.getUuid();
    }
    public String generateChallengesKeyName(Uuid uuid) {
        return amazonConfig.getChallengesPath()+ '/' + uuid.getUuid();
    }

    public String generateChallengeMissionsKeyName(Uuid uuid) {
        return amazonConfig.getChallengeMissionPath()+ '/' + uuid.getUuid();
    }

    public void deleteFile(String imgUrl) {
        try {
            int index = imgUrl.indexOf(".com/");
            if(index != -1 &&index+5<imgUrl.length()) {
                imgUrl = imgUrl.substring(index+5);
            }
            amazonS3.deleteObject(new DeleteObjectRequest(amazonConfig.getBucket(), imgUrl));
        }catch (SdkClientException e){
            log.error("error at AmazonS3Manager.deleteFile: {}", e.getStackTrace());
        }
    }

    public String uploadFile(String keyName, MultipartFile file){
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        try {
            amazonS3.putObject(new PutObjectRequest(amazonConfig.getBucket(), keyName, file.getInputStream(), metadata));
        } catch (IOException e){
            log.error("error at AmazonS3Manager uploadFile : {}", (Object) e.getStackTrace());
        }

        return amazonS3.getUrl(amazonConfig.getBucket(), keyName).toString();
    }
}