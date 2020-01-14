package com.example.luckDraw.service;

import com.example.luckDraw.mapper.PriorityMapper;
import com.example.luckDraw.mapper.PrizeMapper;
import com.example.luckDraw.model.TkPriority;
import com.example.luckDraw.model.TkPrize;
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
public class PriorityService {
    @Resource
    private PriorityMapper priorityMapper;



    public List<TkPriority> findPriority(TkPriority tkPriority) {
        return priorityMapper.findPriority(tkPriority);
    }

    public void addPriority(TkPriority tkPriority) {
        String groupId= DateUtils.splitString(tkPriority.getGroupId());
        tkPriority.setGroupId(groupId);
        priorityMapper.addPriority(tkPriority);
    }

    public void updatePriority(TkPriority tkPriority) {
        priorityMapper.updatePriority(tkPriority);
    }


}
