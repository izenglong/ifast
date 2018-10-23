package com.ifast.wxmp.controller.mp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifast.common.base.AdminBaseController;
import com.ifast.wxmp.service.WeixinService;

/**
 * <pre>
 * </pre>
 *
 * @Author Aron
 * @Date 2018/5/3
 */
@RestController
@RequestMapping("/wx/mp/menu")
public class WxMenuController extends AdminBaseController {
    
    @Autowired
    private WeixinService wxService;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    
}
