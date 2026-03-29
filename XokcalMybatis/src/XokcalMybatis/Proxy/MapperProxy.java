package XokcalMybatis.Proxy;

import XokcalMybatis.Annotation.Param;
import XokcalMybatis.Annotation.Select;
import XokcalMybatis.String.StringClass.XokcalMybatisStringSQL;
import XokcalMybatis.String.StringInterface.XokcalMybatisStringHander;

import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapperProxy implements InvocationHandler {

    private static XokcalMybatisStringHander stringHander = new XokcalMybatisStringSQL();

    public static final String URL =
    public static final String USERNAME = ;
    public static final String PASSWORD = ;

    public static <T> T getProxy(Class<T> mapperInterface){
        return (T) Proxy.newProxyInstance(
                mapperInterface.getClassLoader(),
                new Class[]{mapperInterface},
                new MapperProxy()
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Select annotation = method.getAnnotation(Select.class);
        String sql = annotation.value();

        Parameter[] parameters = method.getParameters();
        Map<Object , Object> map = new HashMap<>();
        if (parameters != null && parameters.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                Param param = parameter.getAnnotation(Param.class);

                if (param != null){
                    String value = param.value();
                    Object arg = args[i];
                    map.put(value , arg);
                }
            }
        }

        Connection connection = DriverManager.getConnection(URL , USERNAME , PASSWORD);
        String stateSql = stringHander.sqlStringToPreStatementExecutable(sql);
        PreparedStatement preparedStatement = connection.prepareStatement(stateSql);
        String[] sqlParamStringArray = stringHander.getSqlParamStringArray(sql);
        for (int i = 0; i < map.size(); i++) {
            preparedStatement.setObject(i+1 , map.get(sqlParamStringArray[i]));
        }
        ResultSet resultSet = preparedStatement.executeQuery();

        ParameterizedType genericExceptionTypes = (ParameterizedType) method.getGenericReturnType();
        Class<?> actualTypeArgument = (Class<?>)genericExceptionTypes.getActualTypeArguments()[0];

        List<Object> list = new ArrayList<>();
        while (resultSet.next()) {
            if (actualTypeArgument == Integer.class) {
                list.add(resultSet.getInt(1));
            } else if (actualTypeArgument == String.class) {
                list.add(resultSet.getString(1));
            } else if (actualTypeArgument == Double.class) {
                list.add(resultSet.getDouble(1));
            }
        }
        return list;
    }
}
