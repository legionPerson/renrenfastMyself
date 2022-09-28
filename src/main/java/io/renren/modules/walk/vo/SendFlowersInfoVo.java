package io.renren.modules.walk.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class SendFlowersInfoVo {
    /**
     * 用户id
     */
    @NotBlank(message = "获取用户失败")
    private String userId;
    /**
     * 接收人id
     */
    private String acceptUserId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 送出数量
     */
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private int sendCount;
    /**
     * 送出日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sendDate;
    /**
     * 当前页
     */
    private int page;
    /**
     * 每页记录数
     */
    private int pageSize;
}
