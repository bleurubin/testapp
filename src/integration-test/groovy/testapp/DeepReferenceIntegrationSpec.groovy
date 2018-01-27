package testapp

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class DeepReferenceIntegrationSpec extends Specification {

    def setup() {
        for(int i=0; i< 1000; i++) {
            Bar bar = new Bar(name: 'bar').save(flush: true, failOnError: true)
            new Foo(name: 'foo', bar: bar).save(flush: true, failOnError: true)
        }
    }

    void "test referencing bar is slower"() {
        when: "we find all foos and reference the name of each child bar"
        long start = System.currentTimeMillis()
        List<Foo> foos = Foo.findAll()
        for (Foo foo in foos) {
            if (foo.bar.name == 'john') {
                println "found john"
            }
        }
        def time = System.currentTimeMillis() - start

        then:
        println "deep reference time: $time"
    }
}
