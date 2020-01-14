package com.example.luckDraw.mapper;

import com.example.luckDraw.model.TkActivity;
import com.example.luckDraw.model.TkPrize;
import com.example.luckDraw.model.TkPrizeGroup;
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
public interface PrizeMapper {
    List<TkPrize> findPrize(Integer activityId);

    void addPrize(TkPrize tkPrize);

    void updatePrize(TkPrize tkPrize);

    void addPrizeGroup(TkPrizeGroup tkPrizeGroup);
    void updatePrizeGroup(TkPrizeGroup tkPrizeGroup);

    List<TkPrizeGroup> findPrizeGroup(TkPrizeGroup tkPrizeGroup);
}
