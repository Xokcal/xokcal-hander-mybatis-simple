package Test.ResultImplement;

import Test.Mapper.UserMapper;
import Test.Pojo.User;
import XokcalMybatis.Proxy.MapperProxy;
import XokcalMybatis.String.StringClass.XokcalMybatisStringSQL;
import XokcalMybatis.String.StringInterface.XokcalMybatisStringHander;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;

public class UserImpl {

    private static UserMapper userMapper = MapperProxy.getProxy(UserMapper.class);

    private static XokcalMybatisStringHander stringHander = new XokcalMybatisStringSQL();

    public static void main(String[] args) {
//        List<String> user = userMapper.getUser(0 , 10);
//        user.forEach(System.out::println);
//        List<Integer> userId = userMapper.getUserId(0, 5);
//        userId.forEach(System.out::println);
        String a = "<select id=\"selectWordByCondition\" resultType=\"com.example.wordmanagefilesystem.Pojo.Word\">\n" +
                "        select * from public_vocabulary\n" +
                "            <where>\n" +
                "                <if test=\"p.word != null and p.word != '' \">\n" +
                "                    word like concat('%',#{p.word},'%')\n" +
                "                </if>\n" +
                "                <if test=\"p.meaning != null and p.meaning != '' \">\n" +
                "                    and meaning like concat('%',#{p.meaning},'%')\n" +
                "                </if>\n" +
                "                <if test=\"p.wordClass != null and p.wordClass != '' \">\n" +
                "                    and part_of_speech like concat('%',#{p.wordClass},'%')\n" +
                "                </if>\n" +
                "                <if test=\"p.selectDegrad != null and p.selectDegrad != '' \">\n" +
                "                    and belong_grade like concat('%',#{p.selectDegrad},'%')\n" +
                "                </if>\n" +
                "            </where>\n" +
                "        limit #{start} , #{p.size}\n" +
                "    </select>";
        String s = stringHander.sqlStringToPreStatementExecutable(a);
        System.out.println(s);
        String[] sqlParamStringArray = stringHander.getSqlParamStringArray(a);
        System.out.println(Arrays.toString(sqlParamStringArray));
    }

}
