package com.example.luckDraw.mapper;

import com.example.luckDraw.model.TkActivity;
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
public interface ActivityMapper {
    List<TkActivity> findActivity();

    void addActivity(TkActivity tkActivity);

    void updateActivity(TkActivity tkActivity);

//    void deleteActivity(TkActivity tkActivity);
}
