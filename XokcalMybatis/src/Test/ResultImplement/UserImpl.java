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
        String a ="<select id=\"selectSetmealPage\" resultType=\"com.example.cangqiong.Pojo.Setmeal.SetmealAndDishBody\">\n" +
                "        select * from setmeal\n" +
                "            <where>\n" +
                "                <if test=\"param.categoryId != null and param.categoryId != ''\">\n" +
                "                    categoryId = #{param.categoryId}\n" +
                "                </if>\n" +
                "                <if test=\"param.name != null and param.name != ''\">\n" +
                "                    and name = #{param.name}\n" +
                "                </if>\n" +
                "                <if test=\"param.status != null \">\n" +
                "                    and status = #{param.status}\n" +
                "                </if>\n" +
                "            </where>\n" +
                "        limit #{start} , #{param.pageSize}\n" +
                "    </select>           ";
        String s = stringHander.sqlStringToPreStatementExecutable(a);
        System.out.println(s);
        String[] sqlParamStringArray = stringHander.getSqlParamStringArray(a);
        System.out.println(Arrays.toString(sqlParamStringArray));
    }

}
