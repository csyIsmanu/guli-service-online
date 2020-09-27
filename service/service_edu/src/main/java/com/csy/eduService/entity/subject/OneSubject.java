package com.csy.eduService.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OneSubject {

    private String id;

    private String title;

//    一个一级分类又多个二级分类
    private List<TwoSubject> children = new ArrayList<>();
}