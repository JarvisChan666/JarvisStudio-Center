package com.jarvis.usercenterbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jarvis.usercenterbackend.model.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
    * @author JarvisChan
    * @description 针对表【user(⽤户表)】的数据库操作Mapper
    * @createDate 2023-07-04 20:59:24
    * @Entity generator.domain.User
    */

}