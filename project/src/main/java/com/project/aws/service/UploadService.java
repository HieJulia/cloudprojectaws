package com.project.aws.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;


@Service
public class UploadService {


    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${AWSS3.region}")
    private String region;

    @Value("${AWSS3.endpointUrl}")
    private String endpointUrl;

    @Value("${AWSS3.bucketName}")
    private String bucketName;

    @Value("${AWSS3.accessKey}")
    private String accessKey;

    @Value("${AWSS3.secretKey}")
    private String secretKey;

    // AWS : region - url - bucket name - access key - secret key


    /**
     * Upload image to AWS S3 bucket
      * @param multipartFile
     * @return
     */
    public String uploadImage(MultipartFile multipartFile) {

        String imageUrl = "";
        // No choi vay day
        try {

            File file = convertMultiPartFileToFile(multipartFile);
            String imageName = generateImageName(multipartFile);
            imageUrl = this.endpointUrl + "/" + this.bucketName + "/" + imageName;
            uploadImageToS3Bucket(imageName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageUrl;
    }


    // Convert Multipart file to file
    private File convertMultiPartFileToFile(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertFile);
        fos.write(file.getBytes());
        fos.close();
        return convertFile;
    }

    private String generateImageName(MultipartFile multiPart) {
        return multiPart.getOriginalFilename().replace(" ", "_") + "-" + new Date().getTime();
    }


    // Upload image to AWS S3 bucket
    private void uploadImageToS3Bucket(String imageName, File file) {

        // aws s3 client
        amazonS3Client.putObject(
                new PutObjectRequest(this.bucketName, imageName, file).withCannedAcl(CannedAccessControlList.PublicRead));
    }


}
