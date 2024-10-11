package com.ecommerce.chomoi.controller;
import com.ecommerce.chomoi.dto.api.ApiResponse;
import com.ecommerce.chomoi.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/uploads")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    //upload 1 image
    @PostMapping("/image")
    public ResponseEntity<ApiResponse<String>> uploadFile(@RequestParam("image") MultipartFile image) {
        try {
            String imageUrl = uploadService.uploadFile(image);
            ApiResponse<String> response = ApiResponse.<String>builder()
                    .success(true)
                    .code("upload-s-01")
                    .message("Upload successfully!")
                    .data(imageUrl)
                    .build();
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            ApiResponse<String> response = ApiResponse.<String>builder()
                    .success(false)
                    .code("upload-e-01")
                    .message("Upload failed!")
                    .build();
            return ResponseEntity.status(500).body(response);
        }
    }

    //upload many images
    @PostMapping("/images")
    public ResponseEntity<ApiResponse<List<String>>> uploadFiles(@RequestParam("images") MultipartFile[] images) {
        try {
            List<String> imageUrls = uploadService.uploadFiles(images);
            ApiResponse<List<String>> response = ApiResponse.<List<String>>builder()
                    .success(true)
                    .code("upload-s-02")
                    .message("Upload successfully!")
                    .data(imageUrls)
                    .build();
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            ApiResponse<List<String>> response = ApiResponse.<List<String>>builder()
                    .success(false)
                    .code("upload-e-02")
                    .message("Upload failed!")
                    .build();
            return ResponseEntity.status(500).body(response);
        }
    }

    //upload one video
    @PostMapping("/video")
    public ResponseEntity<ApiResponse<String>> uploadVideo(@RequestParam("video") MultipartFile video){
        if(video.getSize() > 50 * 1024 * 1024){ //MB -> bit
            ApiResponse<String> response = ApiResponse.<String>builder()
                    .success(false)
                    .code("upload-e-03")
                    .message("File size must be less 50MB")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        }
        try {
            String videoUrl = uploadService.uploadVideo(video);
            ApiResponse<String> response = ApiResponse.<String>builder()
                    .success(true)
                    .code("upload-s-03")
                    .message("Video upload successful")
                    .data(videoUrl)
                    .build();
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            ApiResponse<String> response = ApiResponse.<String>builder()
                    .success(false)
                    .code("upload-e-04")
                    .message("Video upload failed!")
                    .build();
            return ResponseEntity.status(500).body(response);
        }
    }

    //Delete File (image or video)
    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> deleteFile(@RequestParam("file_url") String fileUrl) {
        try {

            uploadService.deleteFile(fileUrl);
            System.out.println("URL: " + fileUrl);
            ApiResponse<String> response = ApiResponse.<String>builder()
                    .success(true)
                    .code("upload-s-04")
                    .message("File deleted successfully!")
                    .build();
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            ApiResponse<String> response = ApiResponse.<String>builder()
                    .success(false)
                    .code("upload-e-05")
                    .message("File deletion failed!")
                    .build();
            return ResponseEntity.status(500).body(response);
        }
    }
}
