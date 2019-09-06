package com.czn.shoppingmall.controller.portal;

import com.czn.shoppingmall.common.Const;
import com.czn.shoppingmall.common.ServerResponse;
import com.czn.shoppingmall.domain.User;
import com.czn.shoppingmall.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/buyer/product/")
public class BuyerProductController {

    @Autowired
    private IProductService iProductService;

    @RequestMapping("get_product")
    public ServerResponse getProduct(Integer productId, HttpSession session) {
//        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
//        if (null == currentUser) {
//            return ServerResponse.createByNeedLogin();
//        }
        if (null == productId) {
            return ServerResponse.createByIllegalArgument();
        }
        return iProductService.getProduct(productId);
    }

    @RequestMapping("search_product/categoryId_keyword")
    public ServerResponse searchByCategoryIdAndKeyWord(@RequestParam(value = "keyword",required = false) String keyword,
                                                     @RequestParam(value = "categoryId",required = false) Integer categoryId,
                                                     @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                     @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                                     @RequestParam(value = "order_by",defaultValue = "") String orderBy)
    {

        if (StringUtils.isBlank(keyword) && null == categoryId) {
            return ServerResponse.createByIllegalArgument();
        }
        return iProductService.searchByCategoryIdAndKeyWord(keyword,categoryId,pageNum,pageSize,orderBy);
    }

    @RequestMapping("search_product/store")
    public ServerResponse searchByStoreName(@RequestParam(value = "storeName",required = false) String storeName,
                                            @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                            @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                            @RequestParam(value = "order_by",defaultValue = "") String orderBy)
    {
        if (StringUtils.isBlank(storeName)) {
            return ServerResponse.createByIllegalArgument();
        }
        return iProductService.searchByStoreName(storeName,pageNum,pageSize,orderBy);
    }


}
