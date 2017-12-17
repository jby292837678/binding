package com.binding.model.data.encrypt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by arvin on 2017/12/4.
 */



@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Params {
    String value();
}
