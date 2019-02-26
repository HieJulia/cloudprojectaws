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

        // Validation

            // fileName : is not null -

        // utf 8

        // No doc duoc het - a du con lin nay cung vai chuong

//        String validName = URLEncoder.encode( fileName , "UTF-8");
            // uploadFilePath : not null



        // Uplodate image
        // Get file
        File file = new File(uploadFilePath);


        s3client.putObject(new PutObjectRequest(bucketName, fileName, file));
    }

    // Function to check if file name is valid





    // Chet me may chua - no ngoi no buc mat o day luon - vao chieu cua no roi - no chui keu ko co dao duc nghe ngheip nay


    public void uploadImage(String fileName, String imageString) {
//        System.out.println(imageString.substring(0,imageString.indexOf(",")+1));
//        System.out.println(imageString.split(";")[0].split(":")[1]);
        //Convert base64 imagestring to byte data
        byte[] byteImage = Base64.decodeBase64(
                (imageString.substring(imageString.indexOf(",")+1)).getBytes());

        InputStream inputStream = new ByteArrayInputStream(byteImage);

//        System.out.println(s3client);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(byteImage.length);
        objectMetadata.setContentType(imageString.split(";")[0].split(":")[1]);
//        metadata.setCacheControl("public, max-age=31536000");
        PutObjectResult result = s3client.putObject(bucketName, fileName, inputStream, objectMetadata);
        System.out.println(result);
        s3client.setObjectAcl(bucketName, fileName, CannedAccessControlList.PublicRead);
    }
}