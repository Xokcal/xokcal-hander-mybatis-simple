package HanderProxyMybatisVersion.proxy;

import java.sql.SQLException;

public interface Proxy {
    public void setTarget(Class<?> target);
    public Class<?> getTarget();
    public Object invoke(String methodName , Object[] params) throws NoSuchMethodException, SQLException;
    public Object xokcalMapper(String methodName , Object[] params) throws SQLException, NoSuchMethodException;
}
