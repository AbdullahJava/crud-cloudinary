package crudcloudinary.demo.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService( Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String getImageUrl(String publicId) {
        return cloudinary.url().generate(publicId);
    }

    public Map upload(MultipartFile multipartFile) throws IOException {
        File file = convertMultiPartToFile(multipartFile);
        Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.asMap("folder", "backend/"));
        file.delete();
        return uploadResult;
    }

    public Map delete(String publicId) throws IOException {
        Map deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        return deleteResult;
    }

    public Map update(String id, MultipartFile multipartFile) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(convertMultiPartToFile(multipartFile), ObjectUtils.asMap("public_id", id));
        return uploadResult;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}