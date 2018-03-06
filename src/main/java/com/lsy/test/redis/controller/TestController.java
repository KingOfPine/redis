package com.lsy.test.redis.controller;

import com.lsy.test.redis.model.Tuser;
import com.lsy.test.redis.service.TuserService;
import com.lsy.test.redis.utils.PagingQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by liangsongying on 2017/10/25.
 */
@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private TuserService tuserService;



    @RequestMapping(value = "tt", method = RequestMethod.GET)
    public String test() {
        return "hahh";
    }

    @RequestMapping(value = "/test")
    public ModelAndView access(){
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }


    @RequestMapping(value = "user",method = RequestMethod.GET)
    public PagingQueryResult getAll(@RequestParam Integer limit, @RequestParam Integer offset,
                                    @RequestParam String order){

        List<Tuser> list = tuserService.getAll();
//        if (list.size()!=0) {
        //自定义异常测试
//            throw new MyException();
//        }
        //触发事件
//        manager.fire(EventType.AFTER_CREATED,"User",list);
        return new PagingQueryResult(list, 4);
    }


    @RequestMapping(value = "add",method = RequestMethod.POST)
    public boolean add(@RequestBody Tuser tuser) {
        return tuserService.add(tuser);
    }

    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public boolean delete(@RequestBody List<Integer> ids) {
        return tuserService.delete(ids);
    }
}
