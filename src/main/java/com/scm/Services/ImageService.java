package com.scm.Services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    String uploadImage(MultipartFile contactImage, String filename) throws IOException;

    String getUrlFromPublicId(String publicId);
}
