package dev.jbull.corev.utils;

public interface IThrowableHandler<T> {

    void handle(T t) throws Throwable;
}
