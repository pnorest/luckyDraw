package com.example.luckDraw.mapper;

import com.example.luckDraw.model.TkGroup;
import com.example.luckDraw.model.TkPrize;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName ActivityMapper
 * @Description TODO
 * @Author Pnorest
 * @Date 2020/1/6 10:28
 * @Version 1.0
 **/
@Mapper
public interface GroupMapper {

    List<TkGroup> findGroup();

    void addGroup(TkGroup tkGroup);

    void updateGroup(TkGroup tkGroup);
}
