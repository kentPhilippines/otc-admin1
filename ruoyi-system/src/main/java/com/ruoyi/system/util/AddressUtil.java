package com.ruoyi.system.util;

import com.google.protobuf.ByteString;
import org.apache.commons.lang3.StringUtils;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.utils.Base58Check;

public class AddressUtil {
    public static String hexToBase58(String address) {
        if (StringUtils.isEmpty(address)){
            return null;
        }
        ByteString bytes = ApiWrapper.parseAddress(address);
        return Base58Check.bytesToBase58(bytes.toByteArray());
    }
}
