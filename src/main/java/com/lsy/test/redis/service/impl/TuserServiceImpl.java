package com.lsy.test.redis.service.impl;


import com.lsy.test.redis.mapper.TuserMapper;
import com.lsy.test.redis.model.Tuser;
import com.lsy.test.redis.service.TuserService;
import com.lsy.test.redis.utils.MD5Utils;
import com.lsy.test.redis.utils.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/**
 * Created by liangsongying on 2018/3/6.
 */
@Service
public class TuserServiceImpl implements TuserService {
    @Autowired
    private TuserMapper tuserServiceMapper;
    @Override
    public List<Tuser> getAll() {
        return tuserServiceMapper.selectAll();
    }

    @Override
    public boolean add(Tuser tuser) {
        String password = MD5Utils.getMD5(tuser.getPassword());
        tuser.setPassword(password);
        return tuserServiceMapper.insert(tuser)>0;
    }

    @Override
    public boolean delete(List<Integer> ids) {
        if (ids==null || ids.size()==0) return false;
        return tuserServiceMapper.delete(ids)>0;
    }

    @Override
    public Tuser doLogin(String userName, String password) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) return null;
        password = MD5Utils.getMD5(password);
        Tuser tuser = tuserServiceMapper.selectOne(userName, password);
        if (tuser != null) {
            //放到内存
            SecurityContext.setCurrentUser(tuser);
        }
        return tuser;
    }

}
