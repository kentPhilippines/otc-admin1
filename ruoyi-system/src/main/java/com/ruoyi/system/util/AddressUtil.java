package com.ruoyi.system.util;

import com.google.protobuf.ByteString;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.utils.Base58Check;

public class AddressUtil {
    public static String hexToBase58(String address) {
        ByteString bytes = ApiWrapper.parseAddress(address);
        return Base58Check.bytesToBase58(bytes.toByteArray());
    }
}
