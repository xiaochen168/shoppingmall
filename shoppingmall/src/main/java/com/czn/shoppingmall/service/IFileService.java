package com.czn.shoppingmall.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface IFileService {

    public String upload(MultipartFile file, String uploadPath);
}
