package HanderProxyMybatisVersion;

import HanderProxyMybatisVersion.proxy.MyProxy;
import HanderProxyMybatisVersion.text.WordMapper;

public class Main {
    private static MyProxy myProxy = new MyProxy(WordMapper.class);

    public static void main(String[] args) throws Throwable {
        Object r = myProxy.invoke("selectWordById" , new Object[]{"v."});
        System.out.println(r);
    }
}