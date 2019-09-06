package com.czn.shoppingmall.service.impl;

import com.czn.shoppingmall.common.Const;
import com.czn.shoppingmall.common.ServerResponse;
import com.czn.shoppingmall.dao.CartMapper;
import com.czn.shoppingmall.dao.ProductMapper;
import com.czn.shoppingmall.domain.Cart;
import com.czn.shoppingmall.domain.Product;
import com.czn.shoppingmall.service.ICartService;
import com.czn.shoppingmall.util.BigDecimalUtil;
import com.czn.shoppingmall.util.PropertiesUtil;
import com.czn.shoppingmall.vo.CartListVo;
import com.czn.shoppingmall.vo.CartProductVo;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("iCartService")
public class CartServiceImpl implements ICartService {

    Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    public ServerResponse add(Integer productId, Integer count, Integer buyerId) {
        Cart cart = cartMapper.selectByProductIdAndBuyerId(productId, buyerId);
        if (null != cart) {
            Cart updateCart = new Cart();
            updateCart.setId(cart.getId());
            updateCart.setQuantity(cart.getQuantity() + count);
            updateCart.setBuyerId(buyerId);
            updateCart.setProductId(productId);
            cartMapper.updateByPrimaryKeySelective(updateCart);
        } else {
            Product product = productMapper.selectByPrimaryKey(productId);
            if (null == product) {
                return ServerResponse.createByErrorMessage("添加购物车失败，该商品不存在或者已经下架");
            }
            Cart addCart = new Cart();
            addCart.setBuyerId(buyerId);
            addCart.setStoreId(product.getStoreId());
            addCart.setProductId(productId);
            addCart.setPrice(product.getPrice());
            addCart.setQuantity(count);
            addCart.setCheckStatus(Const.CartCheck.CART_CHECK);
            cartMapper.insert(addCart);
        }
       return this.list(buyerId);
    }

    public ServerResponse delete(Integer cartId, Integer buyerId) {
        cartMapper.deleteByCartIdAndBuyerId(cartId,buyerId);
        return this.list(buyerId);
    }

    @Override
    public ServerResponse deleteSelected(List<Integer> deleteIds, Integer buyerId) {
        try {
            deleteIds.stream().forEach(id -> {
                cartMapper.deleteByCartIdAndBuyerId(id,buyerId);
            });
        } catch (Exception e) {
            logger.error("delete cart error:{}", e);
            return ServerResponse.createByErrorMessage("删除购物车失败");
        }
        return ServerResponse.createBySuccessMessage("购物车商品删除成功");
    }


    public ServerResponse list(Integer buyerId) {
        CartListVo cartListVo = this.getCartListVo(buyerId);
        return ServerResponse.createBySuccessData(cartListVo);
    }


    public ServerResponse setCheckStatus(Integer cartId, Integer buyerId, Integer checkStatus) {
        cartMapper.setCheckStatus(cartId, buyerId, checkStatus);
        return this.list(buyerId);
    }

    public ServerResponse setAllCheckStatus(Integer buyerId, Integer checkStatus) {
        cartMapper.setAllCheckStatus(buyerId, checkStatus);
        return this.list(buyerId);
    }

    public ServerResponse count(Integer buyerId) {
        int count = 0;
        List<Cart> cartList = cartMapper.selectByBuyerId(buyerId);
        for (Cart cart : cartList) {
            count += cart.getQuantity();
        }
        return ServerResponse.createBySuccessData(count);
    }

    public CartListVo getCartListVo(Integer buyerId) {
        CartListVo cartListVo = new CartListVo();
        List<Cart> cartList = cartMapper.selectByBuyerId(buyerId);
        BigDecimal cartTotalPrice = new BigDecimal("0");
        Integer allChecked = Const.CartCheck.CART_CHECK;
        List<CartProductVo> cartProductVoList = Lists.newArrayList();
        for (Cart cart : cartList) {
            CartProductVo cartProductVo = this.assembleCartProductVo(cart);
            if (null == cartProductVo) {
                continue;
            }
            if (cartProductVo.getCheckStatus() != Const.CartCheck.CART_CHECK) {
                allChecked = Const.CartCheck.CART_UNCHECK;
            } else {
                cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVo.getTotalPrice().doubleValue());
            }
            cartProductVoList.add(cartProductVo);
        }
        cartListVo.setAllChecked(allChecked);
        cartListVo.setCartProductVoList(cartProductVoList);
        cartListVo.setCartTotalPrice(cartTotalPrice);
        cartListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return cartListVo;
    }

    public CartProductVo assembleCartProductVo(Cart cart) {
        Product product = productMapper.selectByPrimaryKey(cart.getProductId());
        if (null == product || product.getStatus() != Const.ProductStatus.ON_SALE) {
            return null;
        }
        CartProductVo cartProductVo = new CartProductVo();
        cartProductVo.setId(cart.getId());
        cartProductVo.setBuyerId(cart.getBuyerId());
        cartProductVo.setStoreId(cart.getStoreId());
        cartProductVo.setProductId(cart.getProductId());
        cartProductVo.setSubtitle(product.getSubtitle());
        cartProductVo.setMainImageUrl(product.getMainImageUrl());
        cartProductVo.setPrice(product.getPrice());
        Integer quantity = 0;
        if (product.getStock() >= cart.getQuantity()) {
            quantity = cart.getQuantity();
            cartProductVo.setQuantityLimit(Const.CartLimit.LIMIT_SUCCESS);
        } else {
            quantity = product.getStock();
            cartProductVo.setQuantityLimit(Const.CartLimit.LIMIT_FAIL);
        }
        cartProductVo.setQuantity(quantity);
        cartProductVo.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), quantity));
        cartProductVo.setCheckStatus(cart.getCheckStatus());
        return cartProductVo;
    }

}
