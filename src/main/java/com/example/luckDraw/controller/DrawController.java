package com.example.luckDraw.controller;

import com.example.luckDraw.model.Result;
import com.example.luckDraw.model.TkDraw;
import com.example.luckDraw.model.TkDrawVo;
import com.example.luckDraw.model.TkPrize;
import com.example.luckDraw.service.DrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName DrawController
 * @Description TODO
 * @Author Pnorest
 * @Date 2020/1/2 10:20
 * @Version 1.0
 **/
@RestController
@RequestMapping("/draw")
public class DrawController {
    @Autowired
    private DrawService drawService;

    //开始抽奖接口
    @RequestMapping("/startDraw")
    public Result startDraw(){//开始抽奖的逻辑
        try{
            String result=drawService.startDraw();
            return new Result(Result.CODE.SUCCESS.getCode(),result);
        }catch (Exception e){
            return new Result(Result.CODE.FAIL.getCode(),"抽奖失败",e.getMessage());
        }


    }

    @RequestMapping("/findDrawResult")
    public Result findDrawResult(){//查询抽奖结果
        try{
            List<TkDrawVo> tkDrawList=drawService.findDrawResult();
            return new Result(Result.CODE.SUCCESS.getCode(),"查询抽奖结果成功",tkDrawList);
        }catch (Exception e){
            return new Result(Result.CODE.FAIL.getCode(),"查询抽奖结果失败",e.getMessage());
        }
    }







}
