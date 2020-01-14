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
import java.util.*;

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
    public String startDraw(Integer activityId) {
        // 1.先抽奖，再划分轮次  从大奖到小奖，先抽出优先抽奖的部分，再抽出除优先分组以外能抽奖的部分
        List<TkPrize> prizeList=prizeMapper.findPrize(activityId);
        for(TkPrize tkPrize:prizeList){//查询所有奖项，循环处理，对每个单独对奖项(多个同奖项也需设计)进行设置
            Integer prizeCount=tkPrize.getPrizeCount();//这个奖品数量
            List<TkPriority> priorityList=priorityMapper.findPriorityByPrizeId(tkPrize.getId());//一个奖项对应多个分组,优先级按照分组id从上往下
            if(priorityList.size()>0){//这个奖品如果设置了优先分组，则走优先分组的逻辑
                for (TkPriority tkPriority:priorityList){
                    //优先设置的分组id，多组以逗号分隔
//                    logger.info("这个奖项的分组id"+tkPriority.getGroupId());
                    prizeCount=prizeCount-tkPriority.getNum();//奖品剩余数量
                    List<TkDrawVo> tkUsers=userMapper.findUserByGroupId(tkPriority.getGroupId(),activityId);//这里的分组不一定单一
                    String result=drawPrize(tkUsers,tkPriority.getPrizeId(),tkPriority.getNum(),activityId);
                    if (!"".equals(result)){
                        return result;
                    }
                }

            }
            //优先抽完正常抽
            List<TkDrawVo> tkDrawVos=userMapper.findUserByGroupId(tkPrize.getGroupNum(),activityId);//可抽奖分组的人员集合(已排除中过奖的用户)
            if(prizeCount<1){//如果当前奖品抽完，则进入下一个循环
                continue;
            }
            String result=drawPrize(tkDrawVos,tkPrize.getId(),prizeCount,activityId);//抽剩余的奖品 这时候可能没可能抽奖的人了
            if (!"".equals(result)){
                return result;
            }
        }
        return "抽奖成功";

    }

    public List<Map<String,List<TkDrawVo>>> findDrawResult(Integer activityId) {
        List<Map<String, List<TkDrawVo>>> mapList=new ArrayList<>();
        List<TkPrize> prizeIdList=drawMapper.findPrizeIdList(activityId);
        for (TkPrize prize:prizeIdList){//遍历
            List<TkDrawVo> tkDrawVoList=drawMapper.findDrawResult(activityId,prize.getId());//查询所有对应中奖结果列表
            TreeMap<String,List<TkDrawVo>> treeMap=new TreeMap<>();
            treeMap.put(prize.getPrize(),tkDrawVoList);
            mapList.add(treeMap);
        }
        return mapList;
    }





    private String drawPrize(List<TkDrawVo> tkUserList,Integer prizeId,Integer num,Integer activityId){//抽奖人，奖项id,抽奖数量
        String result="";

        if(tkUserList.size()<1){
            return "数据有误，请核对奖品数量和抽奖人数,抽奖事人数不能为空,奖比人多？";
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
            tkUser.setActivityId(activityId);
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
