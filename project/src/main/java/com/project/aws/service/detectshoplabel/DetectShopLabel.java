//package com.project.aws.service.detectshoplabel;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.nio.ByteBuffer;
//import java.util.List;
//import com.amazonaws.services.rekognition.AmazonRekognition;
//import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
//import com.amazonaws.AmazonClientException;
//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.profile.ProfileCredentialsProvider;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
//import com.amazonaws.services.rekognition.model.DetectFacesRequest;
//import com.amazonaws.services.rekognition.model.DetectFacesResult;
//import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
//import com.amazonaws.services.rekognition.model.DetectLabelsResult;
//import com.amazonaws.services.rekognition.model.FaceDetail;
//import com.amazonaws.services.rekognition.model.Image;
//import com.amazonaws.services.rekognition.model.Label;
//import com.amazonaws.util.IOUtils;
//
//
//
//public class DetectShopLabel {
//
//    // Display sshop label detail
//    private static void displayFaceDetail(ByteBuffer imageBytes, AmazonRekognition rekognitionClient, String photo) {
//        DetectFacesRequest dfRequest = new DetectFacesRequest()
//                .withImage(new Image()
//                        .withBytes(imageBytes))
//                .withAttributes("ALL");
//
//        try {
//
//            DetectFacesResult result = rekognitionClient.detectFaces(dfRequest);
//            List<FaceDetail> faceDetails = result.getFaceDetails();
//
//
//            for (FaceDetail facedetail: faceDetails) {
//
//            }
//
//        } catch (AmazonRekognitionException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    // Display label
//    private static void displayLabel(ByteBuffer imageBytes, AmazonRekognition rekognitionClient, String photo) {
//        DetectLabelsRequest request = new DetectLabelsRequest()
//                .withImage(new Image()
//                        .withBytes(imageBytes))
//                .withMaxLabels(10)
//                .withMinConfidence(77F);
//
//        try {
//
//            DetectLabelsResult result = rekognitionClient.detectLabels(request);
//            List <Label> labels = result.getLabels();
//
//
//            for (Label label: labels) {
//
//
//
//
//
//            }
//
//        } catch (AmazonRekognitionException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//}
