package com.velocity.caferrer.test.cases

import org.junit.experimental.categories.Category
import spock.lang.Shared
import spock.lang.Specification
import groovyx.net.http.RESTClient

@Category(util.UnitTest)
class ApiSpec extends  Specification{

    @Shared
    def client


    def setupSpec() {
        client = new RESTClient('http://35.192.53.219:9000/persona/')
    }

    def "test create person"(){

        setup:
            println "setup"


        when:
            println "when"
            def resp = client.post(path:"crear", body:[nombre:nombre, apellido:apellido,cedula:cedula,edad:edad],
                requestContentType:'application/json')

            def respAssert = client.get(path: "buscar/${cedula}")


        then:
            println "then"

        with (resp) {
            status == 200
            data.codigo == '00'
            data.mensaje == 'OK'

        }

        with (respAssert) {
                status == 200
                data.codigo == '00'
                data.mensaje == 'OK'
                data.obj.nombre == nombre
                data.obj.cedula == cedula
                data.obj.apellido == apellido
                (data.obj.edad+"")==edad
            }


        where:
            nombre<<['claudia','camilo']
            apellido<<['fernandez','ferrer']
            cedula<<['1234','4321']
            edad<<['27','34']


    }

    def cleanup(){
       println("limpiando test....")
        client.get(path:'deleteall' )

    }



}
