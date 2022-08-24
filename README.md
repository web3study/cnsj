# cnsj
Cnsj  for developers

DEMO get cns name：

```java
Web3j web3j = Web3j.build(new HttpService("https://evmtestnet.confluxrpc.com"));
CnsResolver cns = new CnsResolver(web3j, 30000000 /* sync threshold, seconds */);
String address = cns.resolve("rrr.cfx");
System.out.println(address);

String nftId = NameHash.nameHash("rrr.cfx");
System.out.println(nftId);
System.out.println(Numeric.toBigIntNoPrefix(nftId.replace("0x", "")));
```