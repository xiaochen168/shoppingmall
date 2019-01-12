package com.czn.shoppingmall.controller.backend.seller;

import com.czn.shoppingmall.common.Const;
import com.czn.shoppingmall.common.ServerResponse;
import com.czn.shoppingmall.domain.Product;
import com.czn.shoppingmall.domain.User;
import com.czn.shoppingmall.service.IProductService;
import com.czn.shoppingmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/seller/product/")
public class SellerProductController {

    @Autowired
    private IProductService iProductService;
    @Autowired
    private IUserService iUserService;

    @RequestMapping("add_or_update")
    public ServerResponse addOrUpdate(Product product, HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_SELLER)) {
            return ServerResponse.createByErrorMessage("您不是商家，无法添加或者修改商品");
        }
        if (null == product) {
            return ServerResponse.createByIllegalArgument();
        }
        return iProductService.addOrUpdate(product);
    }

    @RequestMapping("set_status")
    public ServerResponse setStatus(Integer productId, Integer status, HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_SELLER)) {
            return ServerResponse.createByErrorMessage("您不是商家，不能更改商品状态");
        }
        if (null == status || null == productId) {
            return ServerResponse.createByIllegalArgument();
        }
        return iProductService.setStatus(productId,status,currentUser.getId());
    }

    @RequestMapping("get_detail")
    public ServerResponse list(Integer productId, HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_SELLER)) {
            return ServerResponse.createByErrorMessage("您不是商家，不具有查看商品详情权限");
        }
        if (null == productId) {
            return ServerResponse.createByIllegalArgument();
        }
        return iProductService.getProductDetail(productId);
    }

    @RequestMapping("list")
    public ServerResponse list(HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_SELLER)) {
            return ServerResponse.createByErrorMessage("您不是商家，不具有查看商品详情权限");
        }
        return iProductService.list(currentUser.getId());
    }

    @RequestMapping("upload")
    public ServerResponse upload(@RequestParam(value = "upload_file", required = false)MultipartFile file,
                                 HttpSession session, HttpServletRequest request) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_SELLER)) {
            return ServerResponse.createByErrorMessage("您不是商家，不具有上传文件权限");
        }
        String uploadDir = request.getSession().getServletContext().getRealPath("upload");
        return iProductService.upload(file, uploadDir);
    }

    @RequestMapping("rich_text_upload")
    public ServerResponse richTextUpload(@RequestParam(value = "upload_file", required = false)MultipartFile file,
                                         HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_SELLER)) {
            return ServerResponse.createByErrorMessage("您不是商家，不具有上传文件权限");
        }
        String uploadDir = request.getSession().getServletContext().getRealPath("upload");
        response.addHeader("Access-Control-Allow-Headers","X-File-Name");
        return iProductService.upload(file, uploadDir);
    }


}
