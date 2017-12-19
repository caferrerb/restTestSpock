package com.velocity.caferrer.test.cases

import spock.lang.Specification

class TestApi extends  Specification{


    def "test create person"(){

        setup:
            println "setup"
        when:
            println "when"

        then:
            println "then"

        cleanup:
            println "limpiando todo"

    }
}
