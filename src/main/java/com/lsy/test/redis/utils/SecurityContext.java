package com.lsy.test.redis.utils;


import com.lsy.test.redis.model.Tuser;

/**
 * Created by  liangsongying on 2018/2/05.
 */
public class SecurityContext {
    private static  ThreadLocal<Tuser> userThreadLocal=new ThreadLocal<>();

    public static Tuser getCurrentUser(){
        Tuser user = userThreadLocal.get();
        return user;
    }
    public static void setCurrentUser(Tuser user){
        userThreadLocal.set(user);
    }
    public static int getCurrentUserId()
    {
        Tuser user=getCurrentUser();
        return user == null ? -1 : user.getId();
    }
    public static void clearCurrentUser(){
        userThreadLocal.remove();
    }


}
