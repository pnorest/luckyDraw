package com.example.luckDraw.service;

import com.example.luckDraw.mapper.ActivityMapper;
import com.example.luckDraw.mapper.PrizeMapper;
import com.example.luckDraw.model.Result;
import com.example.luckDraw.model.TkActivity;
import com.example.luckDraw.model.TkPrize;
import com.example.luckDraw.model.TkPrizeGroup;
import com.example.luckDraw.utils.DateUtils;
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
public class PrizeService {
    @Resource
    private PrizeMapper prizeMapper;

    public List<TkPrize> findPrize(TkPrize tkPrize) {
        return prizeMapper.findPrize(tkPrize.getActivityId());
    }

    public void addPrize(TkPrize tkPrize) {
        prizeMapper.addPrize(tkPrize);
    }

    public void updatePrize(TkPrize tkPrize) {
        prizeMapper.updatePrize(tkPrize);
    }


    public void addPrizeGroup(TkPrizeGroup tkPrizeGroup) {
        String groupNum= DateUtils.splitString(tkPrizeGroup.getGroupNum());
        tkPrizeGroup.setGroupNum(groupNum);//将可抽分组内容做更改
        prizeMapper.addPrizeGroup(tkPrizeGroup);
    }

    public void updatePrizeGroup(TkPrizeGroup tkPrizeGroup) {
        prizeMapper.updatePrizeGroup(tkPrizeGroup);
    }

    public List<TkPrizeGroup> findPrizeGroup(TkPrizeGroup tkPrizeGroup) {
        return prizeMapper.findPrizeGroup(tkPrizeGroup);
    }
}
