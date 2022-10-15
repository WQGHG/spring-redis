package com.example.springredis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springredis.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by wangqinggang on 2020/9/25.
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
