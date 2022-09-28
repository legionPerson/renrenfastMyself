package io.renren.common.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelSheet {

    /**
     * Sheet 标题
     *
     * @return
     */
    String title() default "";

    /**
     * Sheet下标
     *
     * @return
     */
    int index() default 0;
}
