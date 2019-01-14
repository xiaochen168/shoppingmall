package com.czn.shoppingmall.controller.portal;

import com.czn.shoppingmall.common.Const;
import com.czn.shoppingmall.common.ServerResponse;
import com.czn.shoppingmall.domain.User;
import com.czn.shoppingmall.service.IOrderService;
import com.czn.shoppingmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private IUserService iUserService;

    @RequestMapping("create")
    public ServerResponse create(Integer addressId, HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (null == addressId) {
            return ServerResponse.createByIllegalArgument();
        }
        return iOrderService.createOrder(addressId,currentUser.getId());
    }

    @RequestMapping("cancel")
    public ServerResponse cancel(Long orderNo, HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (null == orderNo) {
            return ServerResponse.createByIllegalArgument();
        }
        return iOrderService.cancel(orderNo, currentUser.getId());
    }

    @RequestMapping("query")
    public ServerResponse query(Long orderNo, HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (null == orderNo) {
            return ServerResponse.createByIllegalArgument();
        }
        return iOrderService.query(orderNo);
    }

    @RequestMapping("list")
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        return iOrderService.list(currentUser.getId(), pageNum, pageSize);
    }

    @RequestMapping("order_status")
    public ServerResponse getOrderStatus(Long orderNo, HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (null == orderNo) {
            return ServerResponse.createByIllegalArgument();
        }
        return iOrderService.orderStatusByOrderNo(orderNo);
    }


}
