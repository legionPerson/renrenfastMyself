package io.renren.common.ref;


/**
 * @author scc
 */

public enum BaseResultStatus {
    /**
     *  成功
     */
    SUCCESS(0, "success"),
    /**
     *  失败
     */
    ERROR(-1, "其他异常"),

    /**
     *  项目编号或项目名称为空
     */
    PROJ_NAME_NULL(600001, "项目编号或项目名称为空"),

    /**
     *  查询数据为空
     */
    DATA_IS_NULL(600011, "查询数据为空"),

    /**
     *  查询数据为空
     */
    COMMIT_DATA_IS_NULL(600012, "提交数据为空");


    private Integer code;
    private String message;

    BaseResultStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
