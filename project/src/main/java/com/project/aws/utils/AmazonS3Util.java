package com.project.aws.utils;
import com.amazonaws.AmazonClientException;
import java.io.File;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.MultiObjectDeleteException;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.UploadPartRequest;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AmazonS3Util {

    // Ta biet cai kieu con nho nay roi - bay tro vui vui thoi -

    // Day la con nguoi tap trung cho su nghiep


    //AmazonS3Client
    private AmazonS3 s3Client;

    private String accessKey="";

    private String secretKey="";

    String bucketName="";



    public AmazonS3Util(){}
    private void AmazonS3Setup() {
        if (s3Client == null) {
            AWSCredentials credentials = null;
            try {
                // Load config
                accessKey = "";
                secretKey = "";
                bucketName = "";

                // Tinh cach no la tinh cach cua 1 cai thang ay - No bo la no bo xuong luon a nghe may


                credentials = new BasicAWSCredentials(accessKey, secretKey);
                s3Client = new AmazonS3Client(credentials);
            } catch (Exception e) {
                e.printStackTrace();
                // "Cannot load the credentials from the credential profiles file. ",e);
            }
        }
    }


    /**
     * downLoadToLocaServer
     * @param targetPath
     * @param keyName => the file name
     */
    public boolean downLoadToLocaServer(String targetPath,String keyName, String fileName) {
        try {
            AmazonS3Setup();


            S3Object object = s3Client.getObject(new GetObjectRequest(bucketName, keyName));

            object.getObjectContent();
            String targetFilePath=targetPath+fileName;
            File targetFile = new File(targetFilePath);
            FileUtils.copyInputStreamToFile(object.getObjectContent(), targetFile);

            // Check if it's PDF file => create thumnail file
            String fileExtension=fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cha biet con lin nay no dang suy nghi gi nua - No dua dua vay thoi

    // Chu khong co y gi dau



    /**
     * getURLDownload
     * @param keyName
     * @return
     */
    public String getURLDownload(String keyName) {
        String urlString = "";
        try {
            AmazonS3Setup();
            // Ngay day no thich thiet - Day ne nhin di - Kieu nua ha - Tu nao den gio no biet het - Chu no khong ngay tho dau
            java.util.Date expiration = new java.util.Date();
            long milliSeconds = expiration.getTime();
            milliSeconds += 3000 * 60 * 60; // Add 3 hours.
            expiration.setTime(milliSeconds);
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, keyName);
            generatePresignedUrlRequest.setMethod(HttpMethod.GET);
            generatePresignedUrlRequest.setExpiration(expiration);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
            urlString = url.toString();

        } catch (Exception e) {

            e.printStackTrace();
        }
        return urlString;
    }


    // Kieu bi dien - Tui bay kieu hoang tuong qua - Thoai mai ma duoi viec tao day nay


    /**
     * uploadFile
     * @param filePath
     * @param keyName: keyName=teamId + "/" + uploadFile.getFilePath();
     */
    // Set part size to 5 MB.
    // Fixed issue https://forums.aws.amazon.com/thread.jspa?threadID=91771
    long partSize = 5 * 1024 * 1024;
    public boolean uploadFile(String filePath,String keyName) {
        try {
            AmazonS3Setup();
            // Create a list of UploadPartResponse objects. You get one of these
            // for each part upload.
            List<PartETag> partETags = new ArrayList<PartETag>();
            // Step 1: Initialize.
            InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(bucketName, keyName);
            InitiateMultipartUploadResult initResponse = s3Client.initiateMultipartUpload(initRequest);

            File file = new File(filePath);
            long contentLength = file.length();

            try {
                // Step 2: Upload parts.
                long filePosition = 0;
                for (int i = 1; filePosition < contentLength; i++) {
                    // Last part can be less than 5 MB. Adjust part size.
                    long _partSize = Math.min(partSize, (contentLength - filePosition));

                    // Create request to upload a part.
                    UploadPartRequest uploadRequest = new UploadPartRequest()
                            .withBucketName(bucketName)
                            .withKey(keyName)
                            .withUploadId(initResponse.getUploadId())
                            .withPartNumber(i)
                            .withFileOffset(filePosition)
                            .withFile(file)
                            .withPartSize(_partSize);

                    // Upload part and add response to our list.
                    partETags.add(s3Client.uploadPart(uploadRequest).getPartETag());
                    filePosition += _partSize;
                }

                // Step 3: Complete.
                CompleteMultipartUploadRequest compRequest = new CompleteMultipartUploadRequest(
                        bucketName,
                        keyName,
                        initResponse.getUploadId(),
                        partETags);

                s3Client.completeMultipartUpload(compRequest);

                return true;
            } catch (Exception e) {
                s3Client.abortMultipartUpload(new AbortMultipartUploadRequest(
                        bucketName, keyName, initResponse.getUploadId()));

                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }

    }

    /**
     *
     * @param keyName: keyName=teamId + "/" + uploadFile.getFilePath();
     * @return
     */
    public boolean deleteFile(String keyName) {

        try
        {
            AmazonS3Setup();
            s3Client.deleteObject(new DeleteObjectRequest(bucketName, keyName));

            return true;
        }catch (AmazonClientException ace)
        {
            return  false;
        }
    }

    // No gia vo day mi oi - Mat no kieu - Tui dau co biet cai me gi dau -

    /**
     *
     * @param keyNames
     * @return
     */
    public boolean deleteFiles(List<String> keyNames) {
        AmazonS3Setup();
        DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest(bucketName);
        List<KeyVersion> keys = new ArrayList<KeyVersion>();
        for (String keyItem : keyNames) {
            keys.add(new KeyVersion(keyItem));
        }
        multiObjectDeleteRequest.setKeys(keys);

        try {
            // S3 client - delete objects

            DeleteObjectsResult delObjRes = s3Client.deleteObjects(multiObjectDeleteRequest);

        } catch (MultiObjectDeleteException e) {
            return false;
        }
        return true;
    }

}

