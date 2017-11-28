package com.binding.demo.base.agree;

import com.binding.library.data.encrypt.aes.AESCrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.binding.demo.base.agree.AgreementUtil.b;


/**
 * Created by arvin on 2017/11/23.
 */

public class AgreeEntity {
    private int mac;
    private int logic_id;
    private int userId;
    private byte[] secretKey;

    public AgreeEntity(int mac, int logic_id, int userId) {
        this.mac = mac;
        this.logic_id = logic_id;
        this.userId = userId;
        secretKey = encryptMD5();
    }

    private byte[] encryptMD5() {
        if(userId == 0)return AESCrypt.AES_EN_KEY_FIXED;
        try {
            String key = "IKE 00010001";
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(AgreementUtil.joint(b(userId), key.getBytes("UTF-8")));
            secretKey = mDigest.digest();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return secretKey;
    }

    public int getMac() {
        return mac;
    }

    public void setMac(int mac) {
        this.mac = mac;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLogic_id() {
        return logic_id;
    }

    public void setLogic_id(int logic_id) {
        this.logic_id = logic_id;
    }

    public byte[] userSecretKey() {
        return secretKey;
    }
}
