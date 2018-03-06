package com.lsy.test.redis.service;



import com.lsy.test.redis.model.Tuser;

import java.util.List;

/**
 * Created by liangsongying on 2017/10/26.
 */
public interface TuserService {
    List<Tuser> getAll();

    boolean add(Tuser tuser);

    boolean delete(List<Integer> ids);

    Tuser doLogin(String userName, String password);



}
