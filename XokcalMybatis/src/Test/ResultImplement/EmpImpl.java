package Test.ResultImplement;

import Test.Mapper.EmpMapper;
import XokcalMybatis.Proxy.MapperProxy;

import java.util.List;

public class EmpImpl {

    private static EmpMapper empMapper = MapperProxy.getProxy(EmpMapper.class);

    public static void main(String[] args) {
        List<String> empName = empMapper.getEmpName(0 , 10);
        empName.forEach(System.out::println);
    }
}
