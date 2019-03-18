package com.project.aws.controller;



import com.amazonaws.services.s3.transfer.Upload;
import com.project.aws.service.s3.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/services/upload")
public class AwsS3ClientController {

    private UploadService uploadService;

    @Autowired
    AwsS3ClientController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @RequestMapping
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        // Validate the file

        if(file.getSize() == 0 ){
            // Log error
            return "File is not valid";
       } else {
            this.uploadService.uploadFile(file);
            return "Successful";
        }
    }
}





