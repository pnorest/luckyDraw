package com.example.luckDraw.mapper;

import com.example.luckDraw.model.TkDrawVo;
import com.example.luckDraw.model.TkPrize;
import com.example.luckDraw.model.TkUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName ActivityMapper
 * @Description TODO
 * @Author Pnorest
 * @Date 2020/1/6 10:28
 * @Version 1.0
 **/
@Mapper
public interface UserMapper {


    List<TkUser> findUser();

    void addUser(TkUser tkUser);

    void updateUser(TkUser tkUser);

    void setAllUserStatus();

    void insertUserInfo(@Param("tkUsers") List<TkUser> tkUsers);

    List<TkDrawVo> findUserByGroupId(String groupId);//只需查到user_id复制到TkDrawVo到userId中就行
}
