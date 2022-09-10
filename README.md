# cnsj
Cnsj  for developers

```xml
<dependency>
  <groupId>io.github.web3study.cns</groupId>
  <artifactId>cnsj</artifactId>
  <version>0.0.3</version>
</dependency>
```

DEMO get cns name：

```java
import io.github.web3study.cns.CnsResolver;

//...

// conflux space test net example
Web3j web3j = Web3j.build(new HttpService("https://evmtestnet.confluxrpc.com"));
CnsResolver cns = new CnsResolver(web3j, 30000000 /* sync threshold, seconds */);
String address = cns.resolve("rrr.cfx");
System.out.println(address);

String nftId = NameHash.nameHash("rrr.cfx");
System.out.println(nftId);
System.out.println(Numeric.toBigIntNoPrefix(nftId.replace("0x", "")));


// conflux space main net example
Web3j web3j1030 = Web3j.build(new HttpService("https://evm.confluxrpc.com"));
CnsResolver cns1030 = new CnsResolver(web3j1030, 30000000 /* sync threshold, seconds */);
String address1030 = cns1030.resolve("99999.cfx");
System.out.println(address1030);

String node1030 = cns1030.reverseResolve("0xd847ed72649A39aad263aC7a1Dcae6Eeb9d51E16");
System.out.println(node1030);

//...

```