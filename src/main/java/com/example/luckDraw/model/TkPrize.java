package com.example.luckDraw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName TkPrize
 * @Description TODO
 * @Author Pnorest
 * @Date 2020/1/2 10:42
 * @Version 1.0
 **/
//抽奖奖品设置
public class TkPrize {
    private Integer id;
    private String prizeName;//奖品名称
    private String prize;//奖项名称
    private String prizeImage;//奖品图片
    private Integer prizeCount;//奖品数量
    private Integer drawCount;//抽奖轮次
    private Integer activityId;//活动Id
    private String groupNum;//可抽奖分组 以逗号分隔
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;//创建时间
    private Integer status;//删除标记


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getPrizeImage() {
        return prizeImage;
    }

    public void setPrizeImage(String prizeImage) {
        this.prizeImage = prizeImage;
    }

    public Integer getPrizeCount() {
        return prizeCount;
    }

    public void setPrizeCount(Integer prizeCount) {
        this.prizeCount = prizeCount;
    }

    public Integer getDrawCount() {
        return drawCount;
    }

    public void setDrawCount(Integer drawCount) {
        this.drawCount = drawCount;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
