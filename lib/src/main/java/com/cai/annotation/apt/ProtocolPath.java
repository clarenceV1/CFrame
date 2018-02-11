package com.cai.annotation.apt;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by clarence on 2018/2/5.
 */

@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface ProtocolPath {
    String value() default "";
}
