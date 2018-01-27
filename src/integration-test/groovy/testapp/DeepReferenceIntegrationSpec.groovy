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
            Foo foo = new Foo(name: 'foo', bar: bar, car: car).save(flush: true, failOnError: true)
            // println "car name: ${car.name}"
            // println "bar name: ${bar.name} car name: ${bar.car.name}"
            // println "foo name: ${foo.name} bar name: ${foo.bar.name}  car name: ${foo.bar.car.name}"
        }
    }

    void "test referencing bar is slower"() {
        when: "we find all foos and reference the name of each child bar"
        long start = System.currentTimeMillis()
        List<Foo> foos = Foo.findAll()
        for (Foo foo in foos) {
            Bar bar = foo.bar
            Car car = bar.car
            if (car?.name == 'john') {
                println "found john"
            }
        }
        def time = System.currentTimeMillis() - start

        then:
        println "deep reference time: $time"
    }
}
