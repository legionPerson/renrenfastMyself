package io.renren.common.annotation;

import io.renren.modules.walk.entity.ExcelType;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {

    /**
     * 标题
     *
     * @return
     */
    String title();

    /**
     * 下标
     *
     * @return
     */
    int index() default 0;

    /**
     * 标题行坐标
     *
     * @return
     */
    int rowIndex() default 0;

    /**
     * 类型
     *
     * @return
     */
    ExcelType type() default ExcelType.STRING;

    /**
     * 格式
     *
     * @return
     */
    String format() default "";
}
