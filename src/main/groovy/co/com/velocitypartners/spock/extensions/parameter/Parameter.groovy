package co.com.velocitypartners.spock.extensions.parameter

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
//@ExtensionAnnotation(ParameterExtension)
public @interface Parameter {
    String  file()  default ""
    String key() default ""
}
