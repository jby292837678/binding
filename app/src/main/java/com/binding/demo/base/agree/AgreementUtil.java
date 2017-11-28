package com.binding.demo.base.agree;

import com.binding.library.data.encrypt.aes.AESCrypt;
import com.binding.library.data.encrypt.crc.MyCRC32;

/**
 * Created by arvin on 2017/11/23.
 */
public class AgreementUtil {
    private static final byte[] head = {0x7b};
    private static final byte[] tail = {0x7d};
    private final AgreeEntity entity;

    public AgreementUtil(AgreeEntity entity) {
        this.entity = entity;
    }

    public byte[] agree(int type,byte[] data){
        byte[] encryptData = AESCrypt.encryptAES(data,entity.userSecretKey());
        return intoData(type,encryptData);
    }

    /**
     * int transform byte array
     */
    public byte[] intoData(int type, byte[] data) {
        int length = 16 + data.length;
        byte[] array = joint(b(length), b(type), b(entity.getMac()), b(entity.getLogic_id()), b(entity.getUserId()), data);
        int crc32 = crcEncrypt(array);
        return joint(head, array, b(crc32), tail);
    }

    /**
     * crc加密生成crc字段
     */
    public int crcEncrypt(byte[] array) {
        byte[] arr = new byte[array.length];
        for (int i = 0; i < array.length; i++)
            arr[i] = (byte) (array[i] & 0xff);
        return MyCRC32.getCRC32(arr, array.length);
    }

    public static int jointLen(byte[]... bss) {
        int len = 0;
        for (byte[] bs : bss) {
            len += bs.length;
        }
        return len;
    }

    public static byte[] joint(byte[]... bss) {
        return joint(jointLen(bss), bss);
    }

    public static byte[] joint(int len, byte[]... bss) {
        byte[] bytes = new byte[len];
        int position = -1;
        for (byte[] bs : bss)
            for (byte b : bs)
                bytes[++position] = b;
        return bytes;
    }

    public static byte[] b(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }



    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static byte[] toBytes(String str) {
        if(str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }
}