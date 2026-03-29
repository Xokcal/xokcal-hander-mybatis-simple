package XokcalMybatis.String.StringInterface;

public interface XokcalMybatisStringHander {
    public String sqlStringToPreStatementExecutable(String sql);
    public String[] getSqlParamStringArray(String sql);
}
