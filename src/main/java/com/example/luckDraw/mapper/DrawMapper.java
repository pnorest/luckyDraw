package com.example.luckDraw.mapper;

import com.example.luckDraw.model.TkDraw;
import com.example.luckDraw.model.TkDrawVo;
import com.example.luckDraw.model.TkPrize;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DrawMapper {


    List<TkDrawVo> findDrawResult();

    void draw(@Param("drawVoList") List<TkDrawVo> drawVoList);

    List<TkDrawVo> findDrawResultByPrizeId(Integer id);

    void updateDrawId(@Param("list") List<TkDrawVo> list);
}
