package HanderProxyMybatisVersion.text;

import HanderProxyMybatisVersion.annotation.Param;
import HanderProxyMybatisVersion.annotation.Select;

import java.util.List;

public interface WordMapper {

    @Select("select * from public_vocabulary where part_of_speech = #{p}")
    List<String> selectWordById(@Param("p") String p);

}
