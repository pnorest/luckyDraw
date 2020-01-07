package com.example.luckDraw.service;

import com.example.luckDraw.mapper.DrawMapper;
import com.example.luckDraw.mapper.PriorityMapper;
import com.example.luckDraw.mapper.PrizeMapper;
import com.example.luckDraw.mapper.UserMapper;
import com.example.luckDraw.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ClassName DrawService
 * @Description TODO
 * @Author Pnorest
 * @Date 2020/1/2 10:20
 * @Version 1.0
 **/
@Service
public class DrawService {
    private Logger logger = LoggerFactory.getLogger(DrawService.class);

    @Resource
    private DrawMapper drawMapper;

    @Resource
    private PrizeMapper prizeMapper;

    @Resource
    private PriorityMapper priorityMapper;

    @Resource
    private UserMapper userMapper;



    @Transactional
    public String startDraw() {
        // 1.先抽奖，再划分轮次  从大奖到小奖，先抽出优先抽奖的部分，再抽出除优先分组以外能抽奖的部分，在将结果分轮次
        List<TkPrize> prizeList=prizeMapper.findPrize();
        for(TkPrize tkPrize:prizeList){//查询所有奖项，循环处理，对每个单独对奖项进行设置
            Integer prizeCount=tkPrize.getPrizeCount();//这个奖品数量
            List<TkPriority> priorityList=priorityMapper.findPriorityByPrizeId(tkPrize.getId());//一个奖项对应多个分组,优先级按照分组id从上往下
            if(priorityList.size()>0){//这个奖品如果设置了优先分组，则走优先分组的逻辑
                for (TkPriority tkPriority:priorityList){
                    //优先设置的分组id，多组已逗号分隔 (这里设置为1个，不用逗号分开，优先级按照GroupId来)
                    logger.info("这个奖项的分组id，需要由1往后排"+tkPriority.getGroupId());
                    prizeCount=prizeCount-tkPriority.getNum();//奖品剩余数量
                    List<TkDrawVo> tkUsers=userMapper.findUserByGroupId(tkPriority.getGroupId());//这里的分组是单一的
                    String result=drawPrize(tkUsers,tkPriority.getPrizeId(),tkPriority.getNum());
                    if (!"".equals(result)){
                        return result;
                    }
                }

            }
            // String[] groupNum =tkPrize.getGroupNum().split(",");//可抽奖分组的集合
            List<TkDrawVo> tkDrawVos=userMapper.findUserByGroupId(tkPrize.getGroupNum());//可抽奖分组的人员集合(已排除中过奖的用户)
            String result=drawPrize(tkDrawVos,tkPrize.getId(),prizeCount);//抽剩余的奖品 这时候可能没可能抽奖的人了
            if (!"".equals(result)){
                return result;
            }
            //抽完所有这个奖品之后，还需区分这个奖的抽奖轮数
            Integer drawCount=tkPrize.getDrawCount();//抽奖轮数，若一轮抽奖则获奖者抽奖轮数直接设定1-1
            List<TkDrawVo> tkDrawVoList=drawMapper.findDrawResultByPrizeId(tkPrize.getId());
            List<List<TkDrawVo>> drawVoList=averageAssign(tkDrawVoList,drawCount);
            for (List<TkDrawVo> list:drawVoList){
                String drawId=tkPrize.getId()+"-"+drawCount;
                for (TkDrawVo tkDrawVo:list){
                    tkDrawVo.setDrawId(drawId);//第几个奖，第几轮
                }
                drawCount=drawCount-1;
                drawMapper.updateDrawId(list);//更新数据库drawId数据
            }
        }
        return "抽奖成功";

    }

    public List<TkDrawVo> findDrawResult() {
        return drawMapper.findDrawResult();
    }





    private String drawPrize(List<TkDrawVo> tkUserList,Integer prizeId,Integer num){//抽奖人，奖项id,抽奖数量
        String result="";

        if(tkUserList.size()<1){
            return result="数据有误，请核对奖品数量和抽奖人数,抽奖事人数不能为空";
        }
        List<TkDrawVo> drawVoList=new ArrayList<>();
        if(tkUserList.size()<num){//当奖比人多的时候
            num=tkUserList.size();
        }
        for(int i=0;i<num;i++){
            Random ran = new Random();
            int a = ran.nextInt(tkUserList.size());//当将比人多多时候报错
            TkDrawVo tkUser = tkUserList.get(a);
            tkUser.setPrizeId(prizeId);
            drawVoList.add(tkUser);
            tkUserList.remove(a);
        }
        drawMapper.draw(drawVoList);
      return result;

    }



    public static <T> List<List<T>> averageAssign(List<T> source,int n){
        List<List<T>> result=new ArrayList<List<T>>();
        int remaider=source.size()%n;  //(先计算出余数)
        int number=source.size()/n;  //然后是商
        int offset=0;//偏移量
        for(int i=0;i<n;i++){
            List<T> value=null;
            if(remaider>0){
                value=source.subList(i*number+offset, (i+1)*number+offset+1);
                remaider--;
                offset++;
            }else{
                value=source.subList(i*number+offset, (i+1)*number+offset);
            }
            result.add(value);
        }
        return result;
    }








}
