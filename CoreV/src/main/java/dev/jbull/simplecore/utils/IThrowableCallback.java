package dev.jbull.simplecore.utils;

public interface IThrowableCallback<T, R> {

    R call(T t) throws Throwable;
}
