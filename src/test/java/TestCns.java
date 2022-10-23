import io.github.web3study.cns.CnsResolver;
import io.github.web3study.cns.cfx.CfxAddress;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;


public class TestCns {

    public static void main(String[] args) throws IOException {
        // conflux space test net example
        Web3j web3j = Web3j.build(new HttpService("https://evmtestnet.confluxrpc.com"));
        CnsResolver cns = new CnsResolver(web3j, 30000000 /* sync threshold, seconds */);
        String address = cns.resolve("0xares.cfx");
        System.out.println(address);

        String node = cns.reverseResolve("0xf00079382099f609dbc37f5a7ea04f14d4ead67c");
        System.out.println("nodex:" + node);

        // conflux space main net example
        Web3j web3j1030 = Web3j.build(new HttpService("https://evm.confluxrpc.com"));
        CnsResolver cns1030 = new CnsResolver(web3j1030, 30000000 /* sync threshold, seconds */);
        String address1030 = cns1030.resolve("honey.cfx");
        String address1030cfx = cns1030.resolveCfx("honey.cfx");
        System.out.println("address1030:" + address1030);
        System.out.println("address1030cfx:" + address1030cfx);

        String node1030 = cns1030.reverseResolve("0xd847ed72649A39aad263aC7a1Dcae6Eeb9d51E16");
        System.out.println("cns1030:" + node1030);


    }
}
