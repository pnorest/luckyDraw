package com.example.luckDraw.controller;

import com.example.luckDraw.model.Result;
import com.example.luckDraw.model.TkActivity;
import com.example.luckDraw.model.TkPriority;
import com.example.luckDraw.service.ActivityService;
import com.example.luckDraw.service.PriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName PriorityController
 * @Description TODO
 * @Author Pnorest
 * @Date 2020/1/6 10:40
 * @Version 1.0
 **/
@RestController
@RequestMapping("/priority")
public class PriorityController {
    @Autowired
    private PriorityService priorityService;


    //查询奖项
    @RequestMapping("/findPriority")
    public Result findPriority(TkPriority tkPriority){//查询所有奖品
        try{
            List<TkPriority> prizeList=priorityService.findPriority(tkPriority);
            return new Result(Result.CODE.SUCCESS.getCode(),"查询成功",prizeList);
        }catch (Exception e){
            return new Result(Result.CODE.FAIL.getCode(),"查询失败",e.getMessage());
        }
    }

    //新增奖项
    @RequestMapping("/addPriority")
    public Result addPriority(TkPriority tkPriority){//groupNum一定要有个下拉框，选择群组
        try{
            priorityService.addPriority(tkPriority);
            return new Result(Result.CODE.SUCCESS.getCode(),"新增成功");
        }catch (Exception e){
            return new Result(Result.CODE.FAIL.getCode(),"新增失败",e.getMessage());
        }

    }


    //更改奖项
    @RequestMapping("/updatePriority")
    public Result updatePriority(TkPriority tkPriority){//groupNum一定要有个下拉框，选择群组
        try{
            priorityService.updatePriority(tkPriority);
            return new Result(Result.CODE.SUCCESS.getCode(),"更新成功");
        }catch (Exception e){
            return new Result(Result.CODE.FAIL.getCode(),"更新失败",e.getMessage());
        }


    }



    //删除奖项
    @RequestMapping("/deletePriority")
    public Result deletePriority(TkPriority tkPriority){//groupNum一定要有个下拉框，选择群组
        try{
            priorityService.updatePriority(tkPriority);
            return new Result(Result.CODE.SUCCESS.getCode(),"删除成功");
        }catch (Exception e){
            return new Result(Result.CODE.FAIL.getCode(),"删除失败",e.getMessage());
        }
    }
}
