package XokcalMybatis.String.StringClass;

import XokcalMybatis.String.StringInterface.XokcalMybatisStringHander;

import java.util.Arrays;

//ORM 解析引擎！
public class XokcalMybatisStringSQL implements XokcalMybatisStringHander {

    private static final String PRAM_IS_EMPTY_ERROR = "调用XokcalMybatisStringSQL方法参数为空";

    //处理sql语句，将sql语句中的 #{...} 转化为 ? 并且获取括号里面的字段
    @Override
    public String sqlStringToPreStatementExecutable(String sql){
        if (sql == null || sql.equals(null))return null;
        int length = recodeSqlFollowCharLength(sql, 0, 0);
        return SQLStringToQuestionMark(sql , new String[(sql.length() - length)], 0 , 0);
    }

    //获得sql语句里#{...}括号里面的字符集合主方法
    @Override
    public String[] getSqlParamStringArray(String sql){
        if (sql == null || sql.isEmpty())throw new RuntimeException(PRAM_IS_EMPTY_ERROR);
        int sqlParamLength = getSqlParamLength(sql);
        String[] sqlParamString = getSqlParamString(sql, sqlParamLength);
        return sqlParamString;
    }

    private static int recodeSqlFollowCharLength(String sql , int startIndex , int num){
        if (startIndex >= sql.length() || startIndex == sql.length() - 1)return num;
        char[] chars = sql.toCharArray();
        for (int i = startIndex; i < chars.length; i++) {
            startIndex++;
            if ((chars[i] == '#' && chars[i + 1] == '{')) {
                for (int j = i; j < chars.length; j++) {
                    if (chars[j] == '}') {
                        num++;
                        return recodeSqlFollowCharLength(sql, j, num - 1);
                    } else {
                        num++;
                    }
                }
            }
        }
        return num;
    }

    private static String SQLStringToQuestionMark(String sql , String[] r , int curr , int index){
        if (curr >= sql.length())return StringArrToString(r);
        char[] sqlC = sql.toCharArray();
        for (int i = curr; i < sqlC.length; i++) {
            if (i + 1 >= sqlC.length){
                r[index] = String.valueOf(sqlC[i]);
                index++;
                return StringArrToString(r);
            }
            if ((sqlC[i] == '#' && sqlC[i + 1] == '{') ||
                    (sqlC[i] == '$' && sqlC[i + 1] == '{')){
                r[index] = "?";
                index++;
                while (i < sqlC.length){
                    if (sqlC[i] == '}'){
                        return SQLStringToQuestionMark(sql , r , i + 1, index);
                    }
                    i++;
                }
            }
            r[index] = String.valueOf(sqlC[i]);
            index++;
        }
        return null;
    }

    private static String StringArrToString(String[] arr){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
        }
        return sb.toString();
    }

    //select * from user where id = #{id} and name = #{name}

    //获得sql语句里#{...}括号里面的字符集合
    private String[] getSqlParamString(String sql , int count){
        if (sql == null || sql.isEmpty())throw new RuntimeException(PRAM_IS_EMPTY_ERROR);
        char[] sqlC = sql.toCharArray();
        String[] r = new String[count];
        String temp = "";
        int index = 0;
        boolean isStore = false;
        for (int i = 0; i < sqlC.length; i++) {
            if (sqlC[i] == '}'){
                isStore = false;
                r[index] = temp;
                temp = "";
                index++;
            }
            if (isStore == true){
                temp += sqlC[i];
            }
            if ((sqlC[i] == '#' && sqlC[i + 1] == '{') ||
                    (sqlC[i] == '$' && sqlC[i + 1] == '{')) {
                isStore = true;
                i = i + 1;
            }
        }
        return r;
    }

    //获得sql语句参数总数
    private int getSqlParamLength(String sql){
        if (sql == null || sql.isEmpty())throw new RuntimeException(PRAM_IS_EMPTY_ERROR);
        char[] sqlC = sql.toCharArray();
        int count = 0;
        for (int i = 0; i < sqlC.length; i++) {
            if ((sqlC[i] == '#' && sqlC[i + 1] == '{') ||
                    (sqlC[i] == '$' && sqlC[i + 1] == '{')){
                count++;
            }
        }
        return count;
    }

}
