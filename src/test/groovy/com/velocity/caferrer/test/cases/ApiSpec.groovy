package com.velocity.caferrer.test.cases

import co.com.velocitypartners.spock.extensions.parameter.Parameter

//import co.com.velocitypartners.spock.extensions.parameter.Parameter
import co.com.velocitypartners.spock.extensions.restclient.RestClient
import org.junit.experimental.categories.Category
import spock.lang.Shared
import spock.lang.Specification

@Category(util.UnitTest)
class ApiSpec extends  Specification{

    @Shared
    @Parameter
    @RestClient(baseUrl = "apiUrl",hardCoded = false)
    def client


    def setupSpec() {

    }

    def "create person"(){

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


    def "list persons"(){

       setup:
            def respsizeExpect=client.get(path:"listar")
            def sizeExpect=respsizeExpect.data.obj.size

       when:
            def respCrear = client.post(path:"crear", body:[nombre:'camilo', apellido:'ferrer',cedula:'12341234',edad:12],
                requestContentType:'application/json')

            def respsizeReal=client.get(path:"listar")
            def sizeReal=respsizeReal.data.obj.size
            


       then:
            respCrear.status==200
            respsizeReal.status==200

            sizeReal==sizeExpect+1
    }


    def "edit person"(){
        setup:
            def respCrear = client.post(path:"crear", body:[nombre:nombre, apellido:apellido,cedula:cedula,edad:edad],
                requestContentType:'application/json')
        when:

            def respEditar =client.put(path:"editar", body:[nombre:nombreeditar, apellido:apellidoeditar,cedula:cedula,edad:edadeditar],
                    requestContentType:'application/json')

            def respBuscar = client.get(path: "buscar/${cedula}")

        then:

            respCrear.status==200
            respEditar.status==200

            with (respBuscar) {
                status == 200
                data.codigo == '00'
                data.mensaje == 'OK'
                data.obj.nombre==nombreeditar
                data.obj.apellido==apellidoeditar
                data.obj.edad==Integer.valueOf(edadeditar)
            }



        where:

            nombre<<['claudia','camilo']
            apellido<<['fernandez','ferrer']
            cedula<<['1234','4321']
            edad<<['27','34']

            nombreeditar<<['claudiaeditado','camiloeditado']
            apellidoeditar<<['fernandezeditado','ferrereditado']
            edadeditar<<['34','28']

    }



    def "find person"(){

        setup:
            def respCrear = client.post(path:"crear", body:[nombre:nombre, apellido:apellido,cedula:cedula,edad:edad],
                    requestContentType:'application/json')

        when:
            def respBuscar = client.get(path: "buscar/${cedula}")

        then:

            with (respCrear) {
                status == 200
                data.codigo == '00'
                data.mensaje == 'OK'

            }

            with (respBuscar) {
                status == 200
                data.codigo == '00'
                data.mensaje == 'OK'
                data.obj.nombre==nombre
                data.obj.apellido==apellido
                data.obj.edad==Integer.valueOf(edad)
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


    def cleanupSpec(){
        client.shutdown()
    }



}
