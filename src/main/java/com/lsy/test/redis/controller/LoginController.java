package com.lsy.test.redis.controller;

import com.lsy.test.redis.model.Tuser;
import com.lsy.test.redis.service.IRedisCachePushService;
import com.lsy.test.redis.service.TuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liangsongying on 2018/3/6.
 */
@RestController
public class LoginController {
    @Autowired
    private IRedisCachePushService iRedisCachePushService;

    @Autowired
    private TuserService tuserService;


    @RequestMapping(value = {"/index",""},method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("html/login");
        return modelAndView;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Map<String, Object> login(String userName, String password, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> result = new HashMap();
        Tuser user = tuserService.doLogin(userName, password);
        if (user == null) {
            result.put("code", 2);
            return result;
        } else {
            iRedisCachePushService.putPassword(userName, password);
            session.setAttribute("user", user);
            result.put("code", 1);
        }
        return result;
    }
}
