package co.com.velocitypartners.spock.extensions.restclient

import co.com.velocitypartners.spock.extensions.parameter.Parameter
import co.com.velocitypartners.spock.extensions.util.PropertiesUtil
import groovyx.net.http.RESTClient
import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.extension.AbstractMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation
import org.spockframework.runtime.model.FieldInfo
import org.spockframework.runtime.model.SpecInfo
import spock.lang.Specification

class RestClientExtension extends AbstractAnnotationDrivenExtension<RestClient> {

    @Override
    void visitFieldAnnotation(RestClient annotation, FieldInfo field) {
            def interceptor

            interceptor = new RestClientInterceptor( field,annotation)

            interceptor.install(field.parent.getTopSpec())
    }
}


class RestClientInterceptor extends AbstractMethodInterceptor{

    protected final FieldInfo field
    protected final RestClient annotation

    RestClientInterceptor() {
    }

    RestClientInterceptor(FieldInfo fieldName, RestClient annotation) {
        this.field = fieldName
        this.annotation=annotation
    }

    @Override
    void interceptSetupMethod(IMethodInvocation invocation) {
        def specInstance=getSpec(invocation)
        def url=null

            if(field.isAnnotationPresent(Parameter)){
                Parameter param=field.getAnnotation(Parameter)
                String file=param.file()
                String key=annotation.baseUrl()

                url= PropertiesUtil.get(file,key)

            }else{

                if(annotation.hardCoded()){
                    url=annotation.baseUrl()
                }else{
                    throw new RuntimeException("Url mal configurada, debe usar @Parameter o el hardCoded debe ser true")
                }
            }


        specInstance."$field.name"=new RESTClient(url)


        invocation.proceed()
    }

    @Override
    void interceptCleanupMethod(IMethodInvocation invocation) {
        try {
            invocation.proceed()
        } finally {

        }
    }

    @Override
    void install(SpecInfo spec) {
        spec.setupMethod.addInterceptor this
        spec.cleanupMethod.addInterceptor this
    }

    protected final Specification getSpec(IMethodInvocation invocation )
    {
        invocation.instance?:invocation.sharedInstance
    }

}


