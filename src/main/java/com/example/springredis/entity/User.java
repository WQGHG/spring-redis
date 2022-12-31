package com.example.springredis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by wqg on 2020/9/25.
 */
@Data
@TableName("user")
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "username")
    private String userName;
    @TableField(value = "password")
    private String passWord;
}
