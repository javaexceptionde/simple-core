package dev.jbull.corev.api;

import dev.jbull.corev.api.utils.NameUUID;
import lombok.Data;

@Data
public class CoreV {
    private static CoreV instance;
    private NameUUID nameUUID;

    public static CoreV getInstance(){
        if (instance == null) instance = new CoreV();
        return instance;
    }
}
