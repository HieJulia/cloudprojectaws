package com.project.aws.service.s3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("uploadService")
public class UploadService {

    // Init AmazonS3
    private AmazonS3 amazonS3;

    @Value("${awsProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${awsProperties.bucketName}")
    private String bucketName;
    @Value("${awsProperties.accessKey}")
    private String accessKey;
    @Value("${awsProperties.secretKey}")
    private String secretKey;
    @Value("${awsProperties.region}")
    private String region;

    @PostConstruct
    private void initializeAmazon() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        // Credentials
        this.amazonS3 = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
    }

    public String uploadFile(MultipartFile multipartFile) {
        String fileUrl = "";
        try {
            String fileName = generateCustomFileNameWithDate(multipartFile);
            File file = convertMultiPartFileToFile(multipartFile);
            fileUrl = endpointUrl + "/" + this.bucketName + "/" + fileName;
            uploadToS3(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    private File convertMultiPartFileToFile(MultipartFile multipartFile) throws IOException {
        File convertedFile = new File(multipartFile.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();
        return convertedFile;
    }

    private String generateCustomFileNameWithDate(MultipartFile multipartFile) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        return date + "-" + multipartFile.getOriginalFilename().replace(" ", "_");
    }



    // Upload file to S3 -
    private void uploadToS3(String fileName, File file) {
        this.amazonS3.putObject(
                new PutObjectRequest(this.bucketName, fileName, file).withCannedAcl(
                        CannedAccessControlList.PublicRead));
    }
}

