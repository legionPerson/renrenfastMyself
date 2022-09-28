package io.renren.modules.walk.entity;

import lombok.Data;

import java.util.List;

@Data
public class ExcelData<T> {
    private String fileName;
    private List<T> data;
}
