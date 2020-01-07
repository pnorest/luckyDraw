package com.example.luckDraw.service;

import com.example.luckDraw.mapper.ActivityMapper;
import com.example.luckDraw.model.Result;
import com.example.luckDraw.model.TkActivity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName ActivityService
 * @Description TODO
 * @Author Pnorest
 * @Date 2020/1/6 10:27
 * @Version 1.0
 **/
@Service
public class ActivityService {
    @Resource
    private ActivityMapper activityMapper;

    public List<TkActivity> findActivity() {
        return activityMapper.findActivity();
    }

    public void addActivity(TkActivity tkActivity) {
        activityMapper.addActivity(tkActivity);
    }

    public void updateActivity(TkActivity tkActivity) {
        activityMapper.updateActivity(tkActivity);
    }

//    public void deleteActivity(TkActivity tkActivity) {
//        activityMapper.updateActivity(tkActivity);
//    }
}
