package com.example.springredis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by wangqinggang on 2020/9/25.
 */
@Data
public class User implements Serializable {
    private Long id;

    @TableField(value = "username")
    private String userName;
    @TableField(value = "password")
    private String passWord;
}
