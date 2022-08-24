import org.web3j.ens.NameHash;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;
import org.yzb.cns.CnsResolver;

import java.io.IOException;


public class TestCns {
    public static void main(String[] args) throws IOException {
        Web3j web3j = Web3j.build(new HttpService("https://evmtestnet.confluxrpc.com"));
        CnsResolver cns = new CnsResolver(web3j, 30000000 /* sync threshold, seconds */);
        String address = cns.resolve("rrr.cfx");
        System.out.println(address);

        String nftId = NameHash.nameHash("rrr.cfx");
        System.out.println(nftId);
        System.out.println(Numeric.toBigIntNoPrefix(nftId.replace("0x", "")));
    }
}
