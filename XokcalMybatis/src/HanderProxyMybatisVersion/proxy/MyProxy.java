package HanderProxyMybatisVersion.proxy;

import HanderProxyMybatisVersion.annotation.Param;
import HanderProxyMybatisVersion.annotation.Select;
import HanderProxyMybatisVersion.parser.XokcalParser;
import HanderProxyMybatisVersion.parser.XokcalSqlParser;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyProxy implements Proxy {
    String url = "jdbc:mysql://localhost:3306/小卡单词管理文档?useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
    private static final String username = "root";
    private static final String password = "qwe1233112233";
    private XokcalParser xokcalParser = new XokcalSqlParser();

    public Class<?> target;

    public MyProxy(Class<?> target) {
        this.target = target;
    }

    @Override
    public void setTarget(Class<?> target) {
        this.target = target;
    }

    @Override
    public Class<?> getTarget() {
        return target;
    }

    @Override
    public Object invoke(String methodName, Object[] params) throws NoSuchMethodException, SQLException {
        Method[] methods = target.getMethods();
        Method targetMethod = null;
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals(methodName)) {
                targetMethod = methods[i];
                break;
            }
        }
        if (targetMethod == null) return null;

        Parameter[] parameters = targetMethod.getParameters();
        String[] parameterNameArray = new String[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            parameterNameArray[i] = parameters[i].getAnnotation(Param.class).value();
        }
        Map<String, Object> paramNameKParamValueV = new HashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            paramNameKParamValueV.put(
                    parameters[i].getAnnotation(Param.class).value()
                    , params[i]
            );
        }
        Select selectAnno = targetMethod.getAnnotation(Select.class);
        String originSql = selectAnno.value();
        String sql = xokcalParser.sqlStringToPreStatementExecutable(originSql);
        String[] statementParams = xokcalParser.getSqlParamStringArray(originSql);
        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int parameterIndex = 1;
        for (String parameterName : parameterNameArray){
            preparedStatement.setObject(parameterIndex
                    , paramNameKParamValueV.get(parameterName));
            parameterIndex++;
        }
        ResultSet rs = preparedStatement.executeQuery();
        int cols = rs.getMetaData().getColumnCount();
        List<Object> list = new ArrayList<>();

        while (rs.next()) {
            for (int i = 1; i <= cols; i++) {
                list.add(rs.getObject(i));
            }
        }

        rs.close();
        preparedStatement.close();
        connection.close();
        return list;
    }

    @Override
    public Object xokcalMapper(String methodName, Object[] params) throws SQLException, NoSuchMethodException {
        Object r = invoke(methodName, params);
        Method[] methods = target.getMethods();
        Type returnType = null;
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals(methodName)) {
                returnType = methods[i].getGenericReturnType();
            }
        }
        if (returnType.equals(List.class)){
            return (List) r;
        }
        return null;
    }
}
