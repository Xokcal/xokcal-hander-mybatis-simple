package Test.Mapper;

import XokcalMybatis.Annotation.Param;
import XokcalMybatis.Annotation.Select;

import java.util.List;

public interface EmpMapper {

    @Select("select name from employee limit #{start} , #{pageSize}")
    List<String> getEmpName(@Param("start") Integer start , @Param("pageSize") Integer pageSize);
}
