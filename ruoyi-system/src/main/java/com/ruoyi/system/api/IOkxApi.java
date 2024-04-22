package com.ruoyi.system.api;

import com.ruoyi.system.api.entity.okx.OkxResponse;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface IOkxApi {
    OkxResponse getSingleTickerOkxResponse() throws NoSuchAlgorithmException, InvalidKeyException;
    OkxResponse getSingleTickerOkxResponse2() throws NoSuchAlgorithmException, InvalidKeyException, IOException;
}
