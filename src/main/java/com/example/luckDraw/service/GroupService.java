package com.example.luckDraw.service;

import com.example.luckDraw.mapper.GroupMapper;
import com.example.luckDraw.mapper.PrizeMapper;
import com.example.luckDraw.model.TkGroup;
import com.example.luckDraw.model.TkPrize;
import org.apache.commons.lang3.StringUtils;
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



    public List<TkGroup> findGroup(TkGroup tkGroup) {
        return groupMapper.findGroup(tkGroup);
    }

    public void addGroup(TkGroup tkGroup) {
        groupMapper.addGroup(tkGroup);
//        tkGroup.setGroupId(asciiToString(String.valueOf(tkGroup.getId()+64)));
//        tkGroup.setStatus(0);//设置status为默认值
//        groupMapper.updateGroup(tkGroup);
    }

    public void updateGroup(TkGroup tkGroup) {
        groupMapper.updateGroup(tkGroup);
    }



    private String AZ(int num){//65-90 A~Z的ASCII
        String tcMsg = "";
        char sl = 0;
        if (-1 < num && num < 10) {
            tcMsg = "" + num;
        } else if (9 < num && num < 36) {
            sl = (char) (num - 10 + (int) 'A');
            tcMsg = "" + sl;
        } else if (35 < num && num < 62) {
            sl = (char) (num - 36 + (int) 'a');
            tcMsg = "" + sl;
        }
        return tcMsg;
    }


    /**
     * Ascii转换为字符串
     * @param value
     * @return
     */
    public static String asciiToString(String value)
    {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }


}
