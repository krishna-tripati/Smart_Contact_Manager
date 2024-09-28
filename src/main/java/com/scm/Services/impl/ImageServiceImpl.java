package com.scm.Services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.Services.ImageService;
import com.scm.helpers.AppConstant;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

//image processing implemention
@Service
public class ImageServiceImpl implements ImageService {

    private Cloudinary cloudinary;
    public ImageServiceImpl(Cloudinary cloudinary) { //field injection cloudinary
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile contactImage, String filename) {
        //code send to the cloud // this project cloud is cloundary

        try {

            byte[] data=new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                    "public_id",filename ));

            //return img url
            return this.getUrlFromPublicId(filename);

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getUrlFromPublicId(String publicId) {
        return cloudinary.url().transformation(
                new Transformation<>().width(AppConstant.CONTACT_IMAGE_WIDTH)
                        .height(AppConstant.CONTACT_IMAGE_HEIGHT)
                        .crop(AppConstant.CONTACT_IMAGE_CROP)
        ).generate(publicId);
    }
}
