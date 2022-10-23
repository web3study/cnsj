package io.github.web3study.cns.cfx;

import com.google.common.io.BaseEncoding;
import com.google.common.primitives.Bytes;

public class CfxAddress {

    private static final byte[] CHECKSUM_TEMPLATE = new byte[8];

    public static String getHexAddress(String address) throws AddressException {
        return decode(address);
    }

    public static String getCfxAddress(String hexAddress) throws AddressException {
        return encode(hexAddress, 1029);
    }

    public static String encode(byte[] hexBuf, int netId) throws AddressException {
        if (hexBuf != null && hexBuf.length == 20) {
            StringBuilder strBuilder = new StringBuilder();
            String chainPrefix = encodeNetId(netId);
            String payload = ConfluxBase32.encode(encodePayload(hexBuf));
            strBuilder.append(chainPrefix);
            strBuilder.append(":");
            strBuilder.append(payload);
            strBuilder.append(createCheckSum(chainPrefix, payload));
            return strBuilder.toString();
        } else {
            throw new AddressException("hexBuf is null or length is not 20");
        }
    }

    private static byte[] encodePayload(byte[] addressBuf) {
        return Bytes.concat(new byte[][]{{0}, addressBuf});
    }

    public static String encode(String hexAddress, int netId) throws AddressException {
        if (hexAddress == null) {
            throw new AddressException("Invalid argument");
        } else {
            return encode(addressBufferFromHex(hexAddress), netId);
        }
    }

    private static String encodeNetId(int netId) throws AddressException {
        if (netId <= 0) {
            throw new AddressException("chainId should be passed as in range [1, 0xFFFFFFFF]");
        } else {
            switch(netId) {
                case 1:
                    return "cfxtest";
                case 1029:
                    return "cfx";
                default:
                    return "net" + netId;
            }
        }
    }

    private static byte[] addressBufferFromHex(String hexAddress) throws AddressException {
        hexAddress = hexAddress.toUpperCase();
        if (hexAddress.startsWith("0X")) {
            hexAddress = hexAddress.substring(2);
        }

        byte[] buf = BaseEncoding.base16().decode(hexAddress);
        if (buf.length != 20) {
            throw new AddressException("hex buffer length should be 20");
        } else {
            return buf;
        }
    }


    public static String decode(String cfxAddress) {
        if (cfxAddress != null && haveNetworkPrefix(cfxAddress)) {
            cfxAddress = cfxAddress.toLowerCase();
            String[] parts = cfxAddress.split(":");
            if (parts.length < 2) {
                throw new AddressException("Address should have at least two part");
            } else {
                String network = parts[0];
                String payloadWithSum = parts[parts.length - 1];
                if (!ConfluxBase32.isValid(payloadWithSum)) {
                    throw new AddressException("Input contain invalid base32 chars");
                } else if (payloadWithSum.length() != 42) {
                    throw new AddressException("Address payload should have 42 chars");
                } else {
                    String sum = payloadWithSum.substring(payloadWithSum.length() - 8);
                    String payload = payloadWithSum.substring(0, payloadWithSum.length() - 8);
                    if (!sum.equals(createCheckSum(network, payload))) {
                        throw new AddressException("Invalid checksum");
                    } else {
                        byte[] raw = ConfluxBase32.decode(payload);
                        String hexAddress = "0X" + BaseEncoding.base16().encode(raw).substring(2);
                        return hexAddress.toLowerCase();
                    }
                }
            }
        } else {
            throw new AddressException("Invalid argument");
        }
    }

    public static boolean haveNetworkPrefix(String cfxAddressStr) {
        cfxAddressStr = cfxAddressStr.toLowerCase();
        return cfxAddressStr.startsWith("cfx") || cfxAddressStr.startsWith("cfxtest") || cfxAddressStr.startsWith("net");
    }

    private static String createCheckSum(String chainPrefix, String payload) throws AddressException {
        byte[] prefixBuf = prefixToWords(chainPrefix);
        byte[] delimiterBuf = new byte[]{0};
        byte[] payloadBuf = ConfluxBase32.decodeWords(payload);
        long n = polyMod(Bytes.concat(new byte[][]{prefixBuf, delimiterBuf, payloadBuf, CHECKSUM_TEMPLATE}));
        return ConfluxBase32.encode(checksumBytes(n));
    }

    private static byte[] prefixToWords(String prefix) {
        byte[] result = prefix.getBytes();

        for (int i = 0; i < result.length; ++i) {
            result[i] = (byte) (result[i] & 31);
        }

        return result;
    }

    private static long polyMod(byte[] data) {
        long c = 1L;
        byte[] var3 = data;
        int var4 = data.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            byte datum = var3[var5];
            byte c0 = (byte) ((int) (c >> 35));
            c = (c & Long.decode("0x07ffffffff")) << 5 ^ (long) datum;
            if ((c0 & 1) != 0) {
                c ^= Long.decode("0x98f2bc8e61");
            }

            if ((c0 & 2) != 0) {
                c ^= Long.decode("0x79b76d99e2");
            }

            if ((c0 & 4) != 0) {
                c ^= Long.decode("0xf33e5fb3c4");
            }

            if ((c0 & 8) != 0) {
                c ^= Long.decode("0xae2eabe2a8");
            }

            if ((c0 & 16) != 0) {
                c ^= Long.decode("0x1e4f43e470");
            }
        }

        return c ^ 1L;
    }

    private static byte[] checksumBytes(long data) {
        return new byte[]{(byte) ((int) (data >> 32 & 255L)), (byte) ((int) (data >> 24 & 255L)), (byte) ((int) (data >> 16 & 255L)), (byte) ((int) (data >> 8 & 255L)), (byte) ((int) (data & 255L))};
    }
}
