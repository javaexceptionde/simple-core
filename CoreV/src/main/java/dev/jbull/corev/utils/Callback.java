package dev.jbull.corev.utils;

import java.io.IOException;

public interface Callback<V extends Object> {
    public void call(V result);
}