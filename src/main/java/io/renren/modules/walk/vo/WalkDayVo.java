package io.renren.modules.walk.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@Data
public class WalkDayVo {
    @NotBlank(message = "获取用户失败")
    private String userId;
    /**
     * 步数
     */
    private Long steps;
    /**
     * 总步数
     */
    private Long sumSteps;
    /**
     * 平均步数
     */
    private Long avgSteps;
    /**
     * 当前页
     */
    private int page;
    /**
     * 每页记录数
     */
    private int pageSize;

    public WalkDayVo(Long steps, Long sumSteps, Long avgSteps) {
        this.steps = steps;
        this.sumSteps = sumSteps;
        this.avgSteps = avgSteps;
    }
}
