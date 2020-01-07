package com.example.luckDraw.controller;

import com.example.luckDraw.model.Result;
import com.example.luckDraw.model.TkActivity;
import com.example.luckDraw.model.TkGroup;
import com.example.luckDraw.model.TkPrize;
import com.example.luckDraw.service.ActivityService;
import com.example.luckDraw.service.GroupService;
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
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;


    //查询奖项
    @RequestMapping("/findGroup")
    public Result findGroup(){//查询所有奖品
        try{
            List<TkGroup> groupList=groupService.findGroup();
            return new Result(Result.CODE.SUCCESS.getCode(),"查询成功",groupList);
        }catch (Exception e){
            return new Result(Result.CODE.FAIL.getCode(),"查询失败",e.getMessage());
        }
    }

    //新增奖项
    @RequestMapping("/addGroup")
    public Result addGroup(TkGroup tkGroup){//groupNum一定要有个下拉框，选择群组
        try{
            groupService.addGroup(tkGroup);
            return new Result(Result.CODE.SUCCESS.getCode(),"新增成功");
        }catch (Exception e){
            return new Result(Result.CODE.FAIL.getCode(),"新增失败",e.getMessage());
        }

    }


    //更改奖项
    @RequestMapping("/updateGroup")
    public Result updateGroup(TkGroup tkGroup){//groupNum一定要有个下拉框，选择群组
        try{
            groupService.updateGroup(tkGroup);
            return new Result(Result.CODE.SUCCESS.getCode(),"更新成功");
        }catch (Exception e){
            return new Result(Result.CODE.FAIL.getCode(),"更新失败",e.getMessage());
        }


    }



    //删除奖项
    @RequestMapping("/deleteGroup")
    public Result deleteGroup(TkGroup tkGroup){//groupNum一定要有个下拉框，选择群组
        try{
            groupService.updateGroup(tkGroup);
            return new Result(Result.CODE.SUCCESS.getCode(),"删除成功");
        }catch (Exception e){
            return new Result(Result.CODE.FAIL.getCode(),"删除失败",e.getMessage());
        }
    }



}
