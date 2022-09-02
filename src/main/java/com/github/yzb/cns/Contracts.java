/*
 * Copyright 2019 Web3 Labs Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.github.yzb.cns;

/** CNS registry contract addresses. */
public class Contracts {

    public static final String SPACE_TEST = "0xBf536C5042317B257BDf29e62CA80794238A9dFD";
    public static final String SPACE_MAIN = "0xBf536C5042317B257BDf29e62CA80794238A9dFD";

    public static String resolveRegistryContract(String chainId) {
        final Long chainIdLong = Long.parseLong(chainId);
        if (chainIdLong.equals(71L)) {
            return SPACE_TEST;
        } else if (chainIdLong.equals(1030L)) {
            return SPACE_MAIN;
        } else {
            throw new CnsResolutionException(
                    "Unable to resolve CNS registry contract for network id: " + chainId);
        }
    }
}
