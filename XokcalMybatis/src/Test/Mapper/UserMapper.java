package Test.Mapper;

import Test.Pojo.User;
import XokcalMybatis.Annotation.Param;
import XokcalMybatis.Annotation.Select;

import java.util.List;

public interface UserMapper {

    @Select("select name from user limit #{start} , #{pageSize}")
    List<String> getUser(@Param("start") Integer start , @Param("pageSize") Integer pageSize);

    @Select("select id from user limit #{start} , #{pageSize}")
    List<Integer> getUserId(@Param("start") Integer start , @Param("pageSize") Integer pageSize);
}
