package com.cydeo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) //this method implemented where ?
@Retention(RetentionPolicy.RUNTIME)//run time it will be active
public @interface DefaultExceptionMessage {

    String defaultMessage() default "";

}