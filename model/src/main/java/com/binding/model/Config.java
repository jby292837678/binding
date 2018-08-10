package com.binding.model;

/**
 * Created by pc on 2017/8/12.
 */

public interface Config {
    String title = "title";
    String user = "user";
    String path = "path";
    String bundle = "bundle";
    String phone  = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
    String identity  = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$";
}
