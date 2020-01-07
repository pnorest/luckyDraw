package com.example.luckDraw.service;

import com.example.luckDraw.mapper.GroupMapper;
import com.example.luckDraw.mapper.PrizeMapper;
import com.example.luckDraw.model.TkGroup;
import com.example.luckDraw.model.TkPrize;
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
public class GroupService {
    @Resource
    private GroupMapper groupMapper;



    public List<TkGroup> findGroup() {
        return groupMapper.findGroup();
    }

    public void addGroup(TkGroup tkGroup) {
        groupMapper.addGroup(tkGroup);
    }

    public void updateGroup(TkGroup tkGroup) {
        groupMapper.updateGroup(tkGroup);
    }
}
