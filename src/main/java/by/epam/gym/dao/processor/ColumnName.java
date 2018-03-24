package by.epam.gym.dao.processor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to mark getter method of entity.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnName {
    String name();

  int parameterIndex() default -1;
}
