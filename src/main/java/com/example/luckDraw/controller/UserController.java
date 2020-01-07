package com.example.luckDraw.controller;

import com.example.luckDraw.model.Result;
import com.example.luckDraw.model.TkPrize;
import com.example.luckDraw.model.TkUser;
import com.example.luckDraw.service.PrizeService;
import com.example.luckDraw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName PriorityController
 * @Description TODO
 * @Author Pnorest
 * @Date 2020/1/6 10:40
 * @Version 1.0
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    //查询抽奖用户
    @RequestMapping("/findUser")
    public Result findUser(){//查询所有奖品
        try{
            List<TkUser> users=userService.findUser();
            return new Result(Result.CODE.SUCCESS.getCode(),"查询成功",users);
        }catch (Exception e){
            return new Result(Result.CODE.FAIL.getCode(),"查询失败",e.getMessage());
        }
    }

    //新增抽奖用户
    @RequestMapping("/addUser")
    public Result addUser(TkUser tkUser){//groupNum一定要有个下拉框，选择群组
        try{
            userService.addUser(tkUser);
            return new Result(Result.CODE.SUCCESS.getCode(),"新增成功");
        }catch (Exception e){
            return new Result(Result.CODE.FAIL.getCode(),"新增失败",e.getMessage());
        }

    }


    //更改抽奖用户
    @RequestMapping("/updateUser")
    public Result updateUser(TkUser tkUser){//groupNum一定要有个下拉框，选择群组
        try{
            userService.updateUser(tkUser);
            return new Result(Result.CODE.SUCCESS.getCode(),"更新成功");
        }catch (Exception e){
            return new Result(Result.CODE.FAIL.getCode(),"更新失败",e.getMessage());
        }


    }



    //删除抽奖用户
    @RequestMapping("/deleteUser")
    public Result deleteUser(TkUser tkUser){//groupNum一定要有个下拉框，选择群组
        try{
            userService.updateUser(tkUser);
            return new Result(Result.CODE.SUCCESS.getCode(),"删除成功");
        }catch (Exception e){
            return new Result(Result.CODE.FAIL.getCode(),"删除失败",e.getMessage());
        }
    }


/*-----------------------------------------------Excel文件解析--------------------------------------*/

    //上传
    @RequestMapping("/uploadFile")
    public Result uploadFile(@RequestParam("file")MultipartFile file) throws IOException {
        try {
            return userService.uploadFile(file);
        } catch (Exception e) {
            return new Result(Result.CODE.FAIL.getCode(), "上传文件失败", e.getMessage());
        }


    }

    //执行接口
    @RequestMapping("/saveExcelInfo")
    public Result saveExcelInfo(String fileUrl, TkUser tkUser) {//需要参考联络易系统的代码查看参数传入
        //Integer contactType;//关键词回复范围(0联系人/1群/2全部开启)
        try {
            return userService.saveExcelInfo(fileUrl, tkUser);
        } catch (Exception e) {
            return new Result(Result.CODE.FAIL.getCode(), "解析文件失败", e.getMessage());
        }
    }


    //模板下载接口
    @RequestMapping(value = "/downloadTemplate")
    public Result downloadTemplate(HttpServletResponse response, HttpServletRequest request) throws IOException {
        try {
            String templeteName = "模板.xlsx";
            userService.downloadTemplate(response, request, templeteName);  //调用业务层方法
            return new Result(Result.CODE.SUCCESS.getCode(), "下载文件模版成功");
        } catch (Exception e) {
            return new Result(Result.CODE.FAIL.getCode(), "下载文件模版失败",e.getMessage());

        }
    }


}
