package com.czn.shoppingmall.service.impl;

import com.czn.shoppingmall.common.Const;
import com.czn.shoppingmall.common.ServerResponse;
import com.czn.shoppingmall.dao.*;
import com.czn.shoppingmall.domain.*;
import com.czn.shoppingmall.service.IOrderService;
import com.czn.shoppingmall.util.BigDecimalUtil;
import com.czn.shoppingmall.util.DateTimeUtil;
import com.czn.shoppingmall.util.PropertiesUtil;
import com.czn.shoppingmall.vo.OrderEntityVo;
import com.czn.shoppingmall.vo.OrderVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service("iOrderService")
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderEntityMapper orderEntityMapper;

    public ServerResponse createOrder(Integer addressId, Integer buyerId) {
        Address address = addressMapper.selectByIdAndUserId(addressId, buyerId);
        if (null == address) {
            return ServerResponse.createByErrorMessage("订单创建失败,未找到对应地址");
        }
        List<Cart> cartList = cartMapper.selectCheckCartByBuyerId(buyerId);
        if (CollectionUtils.isEmpty(cartList)) {
            return ServerResponse.createByErrorMessage("购物车中没有任何选中的商品");
        }
        ServerResponse response = this.getOrderEntityList(cartList);
        if (!response.isSuccess()) {
            return response;
        }
        List<OrderEntity> orderEntityList = (List<OrderEntity>)response.getData();
        BigDecimal orderTotalPrice = this.getOrderTotalPrice(orderEntityList);
        Long orderNo = this.generatorOrderNo();
        for (OrderEntity orderEntity : orderEntityList) {
            orderEntity.setOrderNo(orderNo);
        }
        Order order = this.generatorOrder(orderNo, buyerId, addressId, orderTotalPrice);
        orderMapper.insert(order);
        orderEntityMapper.batchInsert(orderEntityList);
        this.reduceProductStock(orderEntityList);
        OrderVo orderVo = this.assembleOrderVo(order, orderEntityList);
        return ServerResponse.createBySuccessMessageAndData("订单创建成功",orderVo);
    }

    public ServerResponse cancel(Long orderNo, Integer buyerId) {
        int rowCount = orderMapper.cancelOrderByOrderNoAndBuyerId(orderNo,buyerId, Const.OrderStatusEnum.CANCELED.getCode());
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("订单取消成功");
        }
        return ServerResponse.createByErrorMessage("订单取消失败");
    }

    public ServerResponse query(Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (null == order) {
            return ServerResponse.createByErrorMessage("不存在此订单");
        }
        List<OrderEntity> orderEntityList = orderEntityMapper.selectByOrderNo(orderNo);
        OrderVo orderVo = this.assembleOrderVo(order, orderEntityList);
        return ServerResponse.createBySuccessData(orderVo);
    }

    public ServerResponse list(Integer buyerId,Integer pageNum, Integer pageSize){
        List<Order> orderList = orderMapper.selectByBuyerId(buyerId);
        List<OrderVo> orderVoList = Lists.newArrayList();
        PageHelper.startPage(pageNum, pageSize);
        PageInfo pageInfo = new PageInfo(orderList);
        for (Order order : orderList) {
            List<OrderEntity> orderEntityList = orderEntityMapper.selectByOrderNo(order.getOrderNo());
            OrderVo orderVo = this.assembleOrderVo(order, orderEntityList);
            orderVoList.add(orderVo);
        }
        pageInfo.setList(orderVoList);
        return ServerResponse.createBySuccessData(pageInfo);
    }

    public void reduceProductStock(List<OrderEntity> orderEntityList) {
        for (OrderEntity orderEntity : orderEntityList) {
            Product product = productMapper.selectByPrimaryKey(orderEntity.getProductId());
            product.setStock(product.getStock() - orderEntity.getQuantity());
            productMapper.updateByPrimaryKeySelective(product);
        }
    }

    public OrderVo assembleOrderVo(Order order, List<OrderEntity> orderEntityList) {
        OrderVo orderVo = new OrderVo();

        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPayType(order.getPayType());
        orderVo.setPayTypeDesc(Const.PaymentTypeEnum.codeOf(order.getPayType()).getValue());
        orderVo.setPostage(order.getPostage());
        orderVo.setStatus(order.getStatus());
        orderVo.setStatusDesc(Const.OrderStatusEnum.codeOf(order.getStatus()).getValue());
        orderVo.setPayTime(DateTimeUtil.dateToStr(order.getPayTime()));
        orderVo.setSendTime(DateTimeUtil.dateToStr(order.getSendTime()));
        orderVo.setEndTime(DateTimeUtil.dateToStr(order.getEndTime()));
        orderVo.setCloseTime(DateTimeUtil.dateToStr(order.getCloseTime()));
        orderVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

        List<OrderEntityVo> orderEntityVoList = Lists.newArrayList();
        for (OrderEntity orderEntity : orderEntityList) {
            OrderEntityVo orderEntityVo = this.assembleOrderEntityVo(orderEntity);
            orderEntityVoList.add(orderEntityVo);
        }
        Address address = addressMapper.selectByPrimaryKey(order.getAddressId());
        orderVo.setAddress(address);
        orderVo.setOrderEntityVoList(orderEntityVoList);

        return orderVo;
    }


    public OrderEntityVo assembleOrderEntityVo(OrderEntity orderEntity) {
        OrderEntityVo orderEntityVo = new OrderEntityVo();

        orderEntityVo.setOrderNo(orderEntity.getOrderNo());
        orderEntityVo.setSellerId(orderEntity.getSellerId());
        orderEntityVo.setBuyerId(orderEntity.getBuyerId());
        orderEntityVo.setStoreId(orderEntity.getStoreId());
        orderEntityVo.setProductId(orderEntity.getProductId());
        orderEntityVo.setProductName(orderEntity.getProductName());
        orderEntityVo.setProductImageUrl(orderEntity.getProductImageUrl());
        orderEntityVo.setPrice(orderEntity.getPrice());
        orderEntityVo.setQuantity(orderEntity.getQuantity());
        orderEntityVo.setTotalPrice(orderEntity.getTotalPrice());
        orderEntityVo.setCreateTime(orderEntity.getCreateTime());

        return orderEntityVo;
    }

    public Long generatorOrderNo() {
        Long timeStamp = System.currentTimeMillis();
        return timeStamp + new Random().nextInt(10000);
    }

    public Order generatorOrder(Long orderNo, Integer buyerId, Integer addressId, BigDecimal totalPrice){
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setBuyerId(buyerId);
        order.setAddressId(addressId);
        order.setPayment(totalPrice);
        order.setPostage(0);
        order.setPayType(Const.PaymentTypeEnum.ONLINE_PAY.getCode());
        order.setStatus(Const.OrderStatusEnum.NO_PAY.getCode());
        order.setCreateTime(new Date());

        return order;
    }

    public BigDecimal getOrderTotalPrice(List<OrderEntity> orderEntityList) {
        BigDecimal totalPrice = new BigDecimal("0");
        for (OrderEntity orderEntity : orderEntityList) {
            totalPrice = BigDecimalUtil.add(totalPrice.doubleValue(), orderEntity.getTotalPrice().doubleValue());
        }
        return totalPrice;
    }

    public ServerResponse getOrderEntityList(List<Cart> cartList) {
        List<OrderEntity> orderEntityList = Lists.newArrayList();
        for (Cart cart : cartList) {
            Product product = productMapper.selectByPrimaryKey(cart.getProductId());
            if (product.getStatus() != Const.ProductStatus.ON_SALE) {
                return ServerResponse.createByErrorMessage("商品：" + product.getName() + "已下架");
            }
            if (product.getStock() < cart.getQuantity()) {
                return ServerResponse.createByErrorMessage("商品" + product.getName() +"库存不足,只剩下：" + product.getStock());
            }

            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setBuyerId(cart.getBuyerId());
            orderEntity.setStoreId(product.getStoreId());
            // 通过店铺获取商家Id
            Integer sellerId = storeMapper.selectSellerIdByStoreId(product.getStoreId());
            orderEntity.setSellerId(sellerId);
            orderEntity.setProductId(product.getId());
            orderEntity.setProductName(product.getName());
            orderEntity.setProductImageUrl(product.getMainImageUrl());
            orderEntity.setPrice(product.getPrice());
            orderEntity.setQuantity(cart.getQuantity());
            orderEntity.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cart.getQuantity()));
            orderEntity.setCreateTime(new Date());

            orderEntityList.add(orderEntity);
        }
        return ServerResponse.createBySuccessData(orderEntityList);
    }


    public ServerResponse  orderStatusByOrderNo(Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (null == orderNo) {
            return ServerResponse.createByErrorMessage("订单不存在");
        }
        Map<String,Object> resultMap = Maps.newHashMap();
        resultMap.put("order_status",Const.OrderStatusEnum.codeOf(order.getStatus()));
        return ServerResponse.createBySuccessData(resultMap);
    }

    public ServerResponse setOrderStatusByOrderNo(Long orderNo,Integer status) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (null == order) {
            return ServerResponse.createByErrorMessage("订单不存在");
        }
        order.setStatus(status);
        int rowCount = orderMapper.updateByPrimaryKeySelective(order);
        if (rowCount > 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }


}
