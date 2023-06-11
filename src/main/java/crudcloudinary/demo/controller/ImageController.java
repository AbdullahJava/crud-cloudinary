package crudcloudinary.demo.controller;

import crudcloudinary.demo.service.CloudinaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
public class ImageController {

    private final CloudinaryService cloudinaryService;

    public ImageController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/images")
    public ResponseEntity<Map> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        Map uploadResult = cloudinaryService.upload(file);
        return new ResponseEntity<>(uploadResult, HttpStatus.OK);
    }

    @DeleteMapping("/images/{publicId}")
    public ResponseEntity<Map> deleteImage(@PathVariable String publicId) throws IOException {
        Map deleteResult = cloudinaryService.delete(publicId);
        return new ResponseEntity<>(deleteResult, HttpStatus.OK);
    }

    @PutMapping("/images/{id}")
    public ResponseEntity<Map> updateImage(@PathVariable String id, @RequestParam("file") MultipartFile file) throws IOException {
        Map uploadResult = cloudinaryService.update(id, file);
        return new ResponseEntity<>(uploadResult, HttpStatus.OK);
    }

    @GetMapping("/images/{publicId}")
    public ResponseEntity<String> getImageUrl(@PathVariable String publicId) {
        String imageUrl = cloudinaryService.getImageUrl(publicId);
        return ResponseEntity.ok(imageUrl);
    }
}