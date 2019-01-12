package com.czn.shoppingmall.service;

import com.czn.shoppingmall.common.ServerResponse;
import com.czn.shoppingmall.domain.Product;
import org.springframework.web.multipart.MultipartFile;

public interface IProductService {

    public ServerResponse addOrUpdate(Product product);

    public ServerResponse setStatus(Integer productId, Integer status,Integer sellerId);

    public ServerResponse getProductDetail(Integer productId);

    public ServerResponse upload(MultipartFile multipartFile, String uploadPath);

    public ServerResponse list(Integer sellerId);

    public ServerResponse getProduct(Integer productId);

    public ServerResponse searchByCategoryIdAndKeyWord(String keyword,Integer categoryId, Integer pageNum, Integer pageSize,String orderBy);

    public ServerResponse searchByStoreName(String storeName, Integer pageNum, Integer pageSize,String orderBy);
}
