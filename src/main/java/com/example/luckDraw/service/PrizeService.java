package com.example.luckDraw.service;

import com.example.luckDraw.mapper.ActivityMapper;
import com.example.luckDraw.mapper.PrizeMapper;
import com.example.luckDraw.model.TkActivity;
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
public class PrizeService {
    @Resource
    private PrizeMapper prizeMapper;

    public List<TkPrize> findPrize() {
        return prizeMapper.findPrize();
    }

    public void addPrize(TkPrize tkPrize) {
        prizeMapper.addPrize(tkPrize);
    }

    public void updatePrize(TkPrize tkPrize) {
        prizeMapper.updatePrize(tkPrize);
    }


}
