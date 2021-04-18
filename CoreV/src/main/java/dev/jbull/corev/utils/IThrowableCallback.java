package dev.jbull.corev.utils;

public interface IThrowableCallback<T, R> {

    R call(T t) throws Throwable;
}
