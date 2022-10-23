package io.github.web3study.cns.cfx;

import com.google.common.io.BaseEncoding;
import java.util.HashMap;

public class ConfluxBase32 {
    private static final String CONFLUX_CHARSET = "abcdefghjkmnprstuvwxyz0123456789";
    private static final String STANDARD_CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
    private static final char PADDING_CHAR = '=';
    private static final HashMap<Character, Integer> CONFLUX_CHAR_MAP = new HashMap();
    private static final HashMap<Character, Integer> STANDARD_CHAR_MAP;

    public ConfluxBase32() {
    }

    public static String encode(byte[] buffer) throws ConfluxBase32Exception {
        if (buffer == null) {
            throw new ConfluxBase32Exception("buffer is null or empty");
        } else {
            return fromStandard(BaseEncoding.base32().encode(buffer));
        }
    }

    public static byte[] decode(String base32Str) throws ConfluxBase32Exception {
        if (!isValid(base32Str)) {
            throw new ConfluxBase32Exception("include invalid char");
        } else {
            return BaseEncoding.base32().decode(toStandard(base32Str));
        }
    }

    public static byte[] decodeWords(String base32Words) throws ConfluxBase32Exception {
        if (!isValid(base32Words)) {
            throw new ConfluxBase32Exception("include invalid char");
        } else {
            byte[] result = new byte[base32Words.length()];

            for(int i = 0; i < base32Words.length(); ++i) {
                int num = (Integer)CONFLUX_CHAR_MAP.get(base32Words.charAt(i));
                result[i] = (byte)num;
            }

            return result;
        }
    }

    public static String encodeWords(byte[] words) throws ConfluxBase32Exception {
        if (words == null) {
            throw new ConfluxBase32Exception("buffer is null or empty");
        } else {
            StringBuilder result = new StringBuilder(words.length);
            byte[] var2 = words;
            int var3 = words.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                byte word = var2[var4];
                if (word < 0 || word > 31) {
                    throw new ConfluxBase32Exception("word should in range [0-31]");
                }

                result.append("abcdefghjkmnprstuvwxyz0123456789".charAt(word));
            }

            return result.toString();
        }
    }

    public static boolean isValid(String base32Str) {
        if (base32Str == null) {
            return false;
        } else {
            for(int i = 0; i < base32Str.length(); ++i) {
                if (!CONFLUX_CHAR_MAP.containsKey(base32Str.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    private static String toStandard(String base32Str) {
        StringBuilder result = new StringBuilder(base32Str.length());

        for(int i = 0; i < base32Str.length(); ++i) {
            char c = base32Str.charAt(i);
            int index = (Integer)CONFLUX_CHAR_MAP.get(c);
            result.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".charAt(index));
        }

        return result.toString();
    }

    private static String fromStandard(String standardBase32Str) {
        StringBuilder result = new StringBuilder(standardBase32Str.length());

        for(int i = 0; i < standardBase32Str.length(); ++i) {
            char c = standardBase32Str.charAt(i);
            if (c == '=') {
                break;
            }

            int index = (Integer)STANDARD_CHAR_MAP.get(c);
            result.append("abcdefghjkmnprstuvwxyz0123456789".charAt(index));
        }

        return result.toString();
    }

    static {
        int i;
        for(i = 0; i < "abcdefghjkmnprstuvwxyz0123456789".length(); ++i) {
            CONFLUX_CHAR_MAP.put("abcdefghjkmnprstuvwxyz0123456789".charAt(i), i);
        }

        STANDARD_CHAR_MAP = new HashMap();

        for(i = 0; i < "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".length(); ++i) {
            STANDARD_CHAR_MAP.put("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".charAt(i), i);
        }

    }
}

