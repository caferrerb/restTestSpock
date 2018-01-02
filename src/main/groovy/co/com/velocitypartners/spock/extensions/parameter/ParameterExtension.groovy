package co.com.velocitypartners.spock.extensions.parameter

import groovy.transform.InheritConstructors
import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.extension.AbstractMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation
import org.spockframework.runtime.model.FieldInfo
import org.spockframework.runtime.model.SpecInfo
import spock.lang.Specification

class ParameterExtension extends AbstractAnnotationDrivenExtension<Parameter> {

    @Override
    void visitFieldAnnotation(Parameter annotation, FieldInfo field) {
            def interceptor

            interceptor = new ParameterInterceptor( field,annotation)

            interceptor.install(field.parent.getTopSpec())
    }
}


@InheritConstructors
class ParameterInterceptor extends AbstractMethodInterceptor{




    protected final FieldInfo field
    protected final Parameter annotation

    ParameterInterceptor() {
    }

    ParameterInterceptor(FieldInfo field, Parameter annotation) {
        this.field = field
        this.annotation=annotation
    }

    @Override
    void interceptSetupMethod(IMethodInvocation invocation) {
        def specInstance=getSpec(invocation)


        invocation.proceed()
    }


    public void metodoBranch(){
        System.out.println("metodo en branch")
    }


    public void metodoMaster(){
        System.out.println("metodo de master....")
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


