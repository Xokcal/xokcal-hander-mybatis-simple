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
        List<String> user = userMapper.getUser(0 , 10);
        user.forEach(System.out::println);
        List<Integer> userId = userMapper.getUserId(0, 5);
        userId.forEach(System.out::println);
    }

}
