package dev.jbull.corev.config;

import com.sun.org.apache.xpath.internal.operations.Bool;
import dev.jbull.corev.Core;
import dev.jbull.corev.utils.Callback;
import dev.jbull.corev.utils.ExecuteScheduler;
import jdk.nashorn.internal.codegen.CompilerConstants;

import java.io.StringReader;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public interface IConfig {
    ExecuteScheduler scheduler = Core.getInstance().getScheduler();
    ConcurrentHashMap<String, Object> defaults = new ConcurrentHashMap<>();

    public void load(Callback<IConfig> callback);

    public void addDefault(String path, Object value);

    public void setDefaults();

    public void save(Callback<IConfig> callback);

    public void set(String key, Object value);

    public boolean exists(String key);

    public void get(String key, Callback<Object> callback);

    public void getString(String key, Callback<String> callback);

    public void getInt(String key, Callback<Integer> callback);

    public void getBoolean(String key, Callback<Boolean> callback);

    public void getDouble(String key, Callback<Double> callback);

    public void getKeys(String key, boolean deep, Callback<Set<String>> callback);

    public Object get(String key);

    public String getString(String key);

    public Integer getInt(String key);

    public Boolean getBoolean(String key);

    public Double getDouble(String key);

    public Set<String> getKeys(String key, boolean deep);

    public void awaitReady(Callback<IConfig> callback);
}
