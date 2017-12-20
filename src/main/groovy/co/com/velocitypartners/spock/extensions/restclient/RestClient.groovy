package co.com.velocitypartners.spock.extensions.restclient

import org.spockframework.runtime.extension.ExtensionAnnotation

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ExtensionAnnotation(RestClientExtension)
@interface RestClient {

    boolean hardCoded() default true
    String baseUrl()

}