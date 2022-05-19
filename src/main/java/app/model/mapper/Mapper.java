package app.model.mapper;

import java.lang.annotation.*;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Component
public @interface Mapper {

    @AliasFor(
        annotation = Component.class
    )
    String value() default "";

    Class<?> entity();

    Class<?> dto();
}
