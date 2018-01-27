package testapp

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class ShallowReferenceIntegrationSpec extends Specification {

    def setup() {
        for(int i=0; i< 1000; i++) {
            Bar bar = new Bar(name: 'bar').save(flush: true, failOnError: true)
            new Foo(name: 'foo', bar: bar).save(flush: true, failOnError: true)
        }
    }

    void "test referencing only foo.name"() {
        when: "we find all foos and reference the name of each foo"
        long start = System.currentTimeMillis()
        List<Foo> foos = Foo.findAll()
        for (Foo foo in foos) {
            if (foo.name == 'john') {
                println "found john"
            }
        }
        def time = System.currentTimeMillis() - start

        then:
        println "shallow reference time: $time"
    }
}
