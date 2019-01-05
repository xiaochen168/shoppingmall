package com.czn.shoppingmall.controller.portal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/buyer/")
public class BuyerController {

    @RequestMapping("test")
    @ResponseBody
    public String login(){
        return "success";
    }
}
