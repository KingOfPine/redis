package com.lsy.test.redis.mapper;


import com.lsy.test.redis.model.Tuser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by liangsongying on 2017/10/27.
 */
public interface TuserMapper {
    @Select("select * from my_user")
    List<Tuser> selectAll();

    @Select("select * from my_user where username =#{userName} and password=#{password}")
    Tuser selectOne(@Param("userName") String userName, @Param("password") String password);

    @Insert("insert into my_user(username,password,nickname,status) values(#{username},#{password},#{nickname},1)")
    int insert(Tuser tuser);

    @Delete("<script>" +
            " delete from my_user where id in " +
            " <foreach collection=\"ids\" item=\"id\" index=\"no\" open=\"(\"" +
            "            separator=\",\" close=\")\">" +
            "            #{id} " +
            "        </foreach>" +
            " </script>")
    int delete(@Param("ids") List<Integer> ids);
}
