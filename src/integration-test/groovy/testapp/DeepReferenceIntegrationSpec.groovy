package testapp

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class DeepReferenceIntegrationSpec extends Specification {

    def setup() {
        for(int i=0; i< 1000; i++) {
            Car car = new Car(name: 'car').save(flush: true, failOnError: true)
            Bar bar = new Bar(name: 'bar', car: car).save(flush: true, failOnError: true)
            new Foo(name: 'foo', bar: bar, car: car).save(flush: true, failOnError: true)
        }
    }

    def cleanup() {
        Foo.where { name == 'foo' }.deleteAll()
        Bar.where { name == 'bar' }.deleteAll()
        Car.where { name == 'car' }.deleteAll()
    }

    void "test referencing bar is slower"() {
        when: "we find all foos and reference the name of each child bar"
        long start = System.currentTimeMillis()
        List<Foo> foos = Foo.findAll()
        for (Foo foo in foos) {
            if (foo.bar.car.name == 'john') {
                println "found john"
            }
        }
        def time = System.currentTimeMillis() - start

        then:
        println "deep reference time: $time"
    }
}
