package com.czn.shoppingmall.service.impl;

import com.czn.shoppingmall.common.Const;
import com.czn.shoppingmall.common.ServerResponse;
import com.czn.shoppingmall.dao.CategoryMapper;
import com.czn.shoppingmall.dao.ProductMapper;
import com.czn.shoppingmall.dao.StoreMapper;
import com.czn.shoppingmall.domain.Category;
import com.czn.shoppingmall.domain.Product;
import com.czn.shoppingmall.domain.Store;
import com.czn.shoppingmall.service.ICategoryService;
import com.czn.shoppingmall.service.IFileService;
import com.czn.shoppingmall.service.IProductService;
import com.czn.shoppingmall.util.PropertiesUtil;
import com.czn.shoppingmall.vo.ProductVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ICategoryService iCategoryService;
    @Autowired
    IFileService iFileService;


    public ServerResponse addOrUpdate(Product product) {
        if (StringUtils.isNotBlank(product.getSubImagesUrl())) {
            String[] urlArray = product.getSubImagesUrl().split(",");
            product.setMainImageUrl(urlArray[0]);
        }
        if (null == product.getId()) {
            product.setStatus(1);
            int rowCount = productMapper.insert(product);
            if (rowCount > 0) {
                return ServerResponse.createBySuccessMessage("添加商品成功");
            }
            return ServerResponse.createByErrorMessage("添加商品失败");
        }
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("修改商品成功");
        }
        return ServerResponse.createByErrorMessage("修改商品失败");
    }

    public ServerResponse setStatus(Integer productId, Integer status,Integer sellerId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        if (null == product) {
            return ServerResponse.createByErrorMessage("要修改的商品不存在");
        }
        Integer sellerId2 = storeMapper.selectSellerIdByStoreId(product.getStoreId());
        if (null == sellerId2 || sellerId2 != sellerId) {
            return ServerResponse.createByErrorMessage("该商品不属于您的店铺，不具有修改权限");
        }
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("商品状态修改成功");
        }
        return ServerResponse.createByErrorMessage("商品状态修改失败");
    }

    public ServerResponse getProductDetail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        if (null == productId) {
            return ServerResponse.createByErrorMessage("不存该与该id对应的商品");
        }
        return ServerResponse.createBySuccessData(product);
    }

    public ServerResponse upload(MultipartFile multipartFile, String uploadPath) {
       String fileName =  iFileService.upload(multipartFile, uploadPath);
       Map resultMap = Maps.newHashMap();
       if (StringUtils.isBlank(fileName)) {
            resultMap.put("success",false);
            resultMap.put("msg","上传文件失败");
            return ServerResponse.createBySuccessData(resultMap);
        }
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + fileName;
       resultMap.put("success",true);
       resultMap.put("url",url);
       resultMap.put("uri",fileName);
       return ServerResponse.createBySuccessData(resultMap);
    }

    public ServerResponse productImageUpload(Integer productId, MultipartFile mainImage, List<MultipartFile> subImage, String uploadPath) {
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByErrorMessage("此productId对应的商品不存在");
        }
        String mainImageUrl =  iFileService.upload(mainImage, uploadPath);
        if (StringUtils.isBlank(mainImageUrl)) {
           return ServerResponse.createByErrorMessage("上传商品图片失败");
        }
        String subImageUrl = "";
        for (int i = 0; i < subImage.size(); i++) {
            MultipartFile image = subImage.get(i);
            String imageUrl =  iFileService.upload(image, uploadPath);
            if (StringUtils.isBlank(imageUrl)) {
                return ServerResponse.createByErrorMessage("上传商品图片失败");
            }
            if (i == 0) {
                subImageUrl += imageUrl;
            } else {
                subImageUrl += "," + imageUrl;
            }
        }
        product.setMainImageUrl(mainImageUrl);
        product.setSubImagesUrl(subImageUrl);
        productMapper.updateByPrimaryKeySelective(product);
        return ServerResponse.createBySuccessMessage("上传商品图片成功");
    }

    public ServerResponse list(Integer sellerId) {
        List<Integer> storeIdList = storeMapper.selectStoreIdBySellerId(sellerId);
        List<Product> productList = productMapper.selectByStoreIdList(storeIdList);
        return ServerResponse.createBySuccessData(productList);
    }

    public ServerResponse getProduct(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        if (null == product) {
            return ServerResponse.createByErrorMessage("没有找到相应的商品");
        }
        ProductVo productVo = this.assembleProductVo(product);
        return ServerResponse.createBySuccessData(productVo);
    }

    public ProductVo assembleProductVo(Product product) {
        Store store = storeMapper.selectByPrimaryKey(product.getStoreId());
        ProductVo productVo = new ProductVo();
        productVo.setId(product.getId());
        productVo.setCategoryId(product.getCategoryId());
        productVo.setStoreId(product.getStoreId());
        productVo.setStoreName(store.getStoreName());
        productVo.setSubtitle(product.getSubtitle());
        productVo.setDetail(product.getDetail());
        productVo.setMainImageUrl(product.getMainImageUrl());
        productVo.setSubImagesUrl(product.getSubImagesUrl());
        productVo.setPrice(product.getPrice());
        productVo.setStock(product.getStock());
        productVo.setStatus(product.getStatus());
        productVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return productVo;
    }

    public ServerResponse searchByCategoryIdAndKeyWord(String keyword,Integer categoryId, Integer pageNum, Integer pageSize,String orderBy) {
        List<ProductVo> productVoList = Lists.newArrayList();
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        PageHelper.startPage(pageNum,pageSize);
        if (null == category) {
            PageInfo pageInfo = new PageInfo(productVoList);
            ServerResponse.createBySuccessData(pageInfo);
        }
        List<Integer> categoryIdList = (List<Integer>)iCategoryService.getAllChildCategoryIdByParentId(categoryId).getData();
        if (StringUtils.isNotBlank(keyword)) {
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }
        if (StringUtils.isNotBlank(orderBy) && Const.ProductOrder.PRICE_ASC_DESC.contains(orderBy)) {
            String[] orderArray = orderBy.split("_");
            PageHelper.orderBy(orderArray[0] + " " + orderArray[1]);
        }
        if (categoryIdList.size() == 0) {
            categoryIdList = null;
        }
        if (StringUtils.isBlank(keyword)) {
            keyword = null;
        }
        List<Product> productList = productMapper.selectByNameAndCategoryIdList(keyword, categoryIdList);
        for (Product product : productList) {
            ProductVo productVo = this.assembleProductVo(product);
            productVoList.add(productVo);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productVoList);
        return ServerResponse.createBySuccessData(pageInfo);
    }

    public ServerResponse searchByStoreName(String storeName, Integer pageNum, Integer pageSize,String orderBy) {
        storeName = new StringBuilder().append("%").append(storeName).append("%").toString();
        List<Integer> storeIdList = storeMapper.selectStoreIdByName(storeName);
        List<ProductVo> productVoList = Lists.newArrayList();
        PageHelper.startPage(pageNum,pageSize);
        if (CollectionUtils.isEmpty(storeIdList)) {
            PageInfo pageInfo = new PageInfo(productVoList);
            return ServerResponse.createBySuccessData(pageInfo);
        }
        if (StringUtils.isNotBlank(orderBy) && Const.ProductOrder.PRICE_ASC_DESC.contains(orderBy)) {
            String[] orderArray = orderBy.split("_");
            PageHelper.orderBy(orderArray[0] + " " + orderArray[1]);
        }
        List<Product> productList = productMapper.selectByStoreIdList(storeIdList);
        for (Product product : productList) {
            ProductVo productVo = this.assembleProductVo(product);
            productVoList.add(productVo);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productVoList);
        return ServerResponse.createBySuccessData(pageInfo);
    }






}
