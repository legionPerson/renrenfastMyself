package io.renren.modules.walk.vo;

import lombok.Data;

@Data
public class WellProductVo {
    private String id;
    private String userId;
    /**
     * 产品名
     */
    private String name;
    /**
     * 产品链接
     */
    private String url;
    /**
     * 产品详情
     */
    private String describe;
    /**
     * 产品图片
     */
    private String img;
    private String createById;
    private String updateById;
    /**
     * 当前页
     */
    private int page;
    /**
     * 每页记录数
     */
    private int pageSize;
}
