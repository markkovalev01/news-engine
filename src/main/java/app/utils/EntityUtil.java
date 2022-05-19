package app.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import app.model.entities.DefaultEntity;
import lombok.SneakyThrows;

public class EntityUtil {

    @SneakyThrows
    public static <T extends DefaultEntity> T copy(T s, T c) {
        for (Field field : s.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (Collection.class.isAssignableFrom(field.getType()) ||
                DefaultEntity.class.isAssignableFrom(field.getType())) {
                continue;
            }
            if (field.get(c) == null) {
                continue;
            }
            if (field.get(s) == null || !field.get(s).equals(field.get(c))) {
                field.set(s, field.get(c));
            }
        }
        return s;
    }
}
