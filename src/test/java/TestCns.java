import org.web3j.ens.NameHash;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;
import com.github.yzb.cns.CnsResolver;

import java.io.IOException;


public class TestCns {
    public static void main(String[] args) throws IOException {
        Web3j web3j = Web3j.build(new HttpService("https://evmtestnet.confluxrpc.com"));
        CnsResolver cns = new CnsResolver(web3j, 30000000 /* sync threshold, seconds */);
        String address = cns.resolve("0xares.cfx");
        System.out.println(address);

        String node = cns.reverseResolve("0xf00079382099f609dbc37f5a7ea04f14d4ead67c");
        System.out.println(node);
    }
}
