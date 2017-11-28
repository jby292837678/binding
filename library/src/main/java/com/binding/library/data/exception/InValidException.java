package com.binding.library.data.exception;

import java.io.IOException;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：20:14
 * modify developer：  admin
 * modify time：20:14
 * modify remark：
 *
 * @version 2.0
 */


public class InValidException extends IOException {
    public InValidException(String s) {
        super(s);
    }
}