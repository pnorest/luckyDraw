package com.example.luckDraw.mapper;

import com.example.luckDraw.model.TkPriority;
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
public interface PriorityMapper {


    void updatePriority(TkPriority tkPriority);

    void addPriority(TkPriority tkPriority);

    List<TkPriority> findPriority();

    List<TkPriority> findPriorityByPrizeId(Integer id);
}
