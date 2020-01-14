package com.example.luckDraw.controller;

import com.example.luckDraw.model.Result;
import com.example.luckDraw.model.TkActivity;
import com.example.luckDraw.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName ActivityController
 * @Description TODO
 * @Author Pnorest
 * @Date 2020/1/6 10:26
 * @Version 1.0
 **/

@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;



    //查询活动列表
    @RequestMapping("/findActivity")
    public Result findPrize(){//查询所有奖品
        try{
            List<TkActivity> tkActivities=activityService.findActivity();
            return new Result(Result.CODE.SUCCESS.getCode(),"查询成功",tkActivities);
        }catch (Exception e){
            return new Result(Result.CODE.FAIL.getCode(),"查询失败",e.getMessage());
        }
    }



    //新增活动
    @RequestMapping("/addActivity")
    public Result addActivity(TkActivity tkActivity){//查询所有奖品
        try{
            activityService.addActivity(tkActivity);
            return new Result(Result.CODE.SUCCESS.getCode(),"新增成功",tkActivity);
        }catch (Exception e){
            return new Result(Result.CODE.FAIL.getCode(),"更新失败",e.getMessage());
        }

    }


    //更改活动
    @RequestMapping("/updateActivity")
    public Result updateActivity(TkActivity tkActivity){//查询所有奖品
        try{
            activityService.updateActivity(tkActivity);
            return new Result(Result.CODE.SUCCESS.getCode(),"更新成功");
        }catch (Exception e){
            return new Result(Result.CODE.FAIL.getCode(),"更新失败",e.getMessage());
        }
    }



    //删除活动(当活动已经结束时，系统将不再展示关于该活动的全部数据)  与更新接口公用一个接口  只需传id和status其他的不传
    @RequestMapping("/deleteActivity")
    public Result deleteActivity(TkActivity tkActivity){//查询所有奖品
        try{
            activityService.updateActivity(tkActivity);
            return new Result(Result.CODE.SUCCESS.getCode(),"删除成功");
        }catch (Exception e){
            return new Result(Result.CODE.FAIL.getCode(),"删除失败",e.getMessage());
        }
    }



}
