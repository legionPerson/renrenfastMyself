package io.renren.modules.walk.entity.dto;

import io.renren.common.annotation.ExcelColumn;
import io.renren.common.annotation.ExcelSheet;
import io.renren.common.annotation.ExcelTemplate;
import lombok.*;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wp
 * @since 2022-05-28
 */
@Data
@ExcelSheet(title = "白名单")
@ExcelTemplate(value = "用户列表", rowspan = 0, colIndex = 0, colspan = 8)
public class UserWhiteDto {

    /**
     * 手机号
     */
    @ExcelColumn(title = "手机号", index = 0, rowIndex = 3)
    private String phone;

    /**
     * 姓名
     */
    @ExcelColumn(title = "姓名", index = 1, rowIndex = 3)
    private String name;

    /**
     * 生日（YYYYMMDD）
     */
    @ExcelColumn(title = "生日", index = 2, rowIndex = 3)
    private String birthDate;

    /**
     * 是否单位管理员（0否，1是）
     */
    @ExcelColumn(title = "是否管理员", index = 3, rowIndex = 3)
    private String isAdmin;

    /**
     * 最小级别ID
     */
    @ExcelColumn(title = "最小级别", index = 4, rowIndex = 3)
    private String orgId;

    /**
     * 创建时间
     */
    @ExcelColumn(title = "创建时间", index = 5, rowIndex = 3)
    private Date createTime;

    /**
     * 更新时间
     */
    @ExcelColumn(title = "更新时间", index = 6, rowIndex = 3)
    private Date updateTime;

    /**
     * 0正常用户，1无效用户
     */
    @ExcelColumn(title = "是否正常用户", index = 7, rowIndex = 3)
    private String status;

    /**
     * 创建人
     */
    @ExcelColumn(title = "创建人", index = 8, rowIndex = 3)
    private String createById;

}
