package com.project.aws.service.s3;


import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.apache.commons.codec.binary.Base64;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

@Service("s3service")
public class S3ServiceImpl {

    // S3 : bucket name - url -
    @Value("${amazon.bucketName}")
    private String bucketName;

    @Value("${amazon.bucketURL}")
    private String bucketURL;

    public String getBucketURL() {
        return bucketURL;
    }

    @Autowired
    private AmazonS3 s3client;


    /**
     * Upload Image
     *
     * cai dau tui nay thu doan vai chuong
     *
     * @param fileName
     * @param uploadFilePath
     */
    public void uploadImage1(String fileName, String uploadFilePath) {

        if(this.isFilenameValid(fileName)) {
            File file = new File(uploadFilePath);
        } else {
            // throw exception
        }
        // Uplodate image
        // Get file
        File file = new File(uploadFilePath);


        s3client.putObject(new PutObjectRequest(bucketName, fileName, file));
    }


    /**
     * Check if file name is valid
     * @param file
     * @return
     */
    public static boolean isFilenameValid(String file) {
        File f = new File(file);
        try {
            f.getCanonicalPath();
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }


    /**
     * Upload product image
     * @param fileName
     * @param imageString
     */

    public void uploadImage(String fileName, String imageString) {

        // File name - image string
        //Convert base64 imagestring to byte data
        byte[] byteImage = Base64.decodeBase64(
                (imageString.substring(imageString.indexOf(",")+1)).getBytes());

        InputStream inputStream = new ByteArrayInputStream(byteImage);



        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(byteImage.length);
        objectMetadata.setContentType(imageString.split(";")[0].split(":")[1]);
//        metadata.setCacheControl("public, max-age=31536000");
        PutObjectResult result = s3client.putObject(bucketName, fileName, inputStream, objectMetadata);
        // s3client - set object
        s3client.setObjectAcl(bucketName, fileName, CannedAccessControlList.PublicRead);
    }
}

