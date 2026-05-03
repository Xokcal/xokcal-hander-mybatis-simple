package HanderProxyMybatisVersion.parser;

public interface XokcalParser {
    public String sqlStringToPreStatementExecutable(String sql);
    public String[] getSqlParamStringArray(String sql);
}
