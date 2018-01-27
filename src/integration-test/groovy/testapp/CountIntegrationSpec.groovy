package testapp

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class CountIntegrationSpec extends Specification {

    def setup() {
        for(int i=0; i< 1000; i++) {
            Bar bar = new Bar(name: 'bar').save(flush: true, failOnError: true)
            new Foo(name: 'foo', bar: bar).save(flush: true, failOnError: true)
        }
    }

    void "test count Foos and Bars"() {
        when: "we count the Foo and Bar collections"
        long start = System.currentTimeMillis()
        int numFoos = Foo.count()
        int numBars = Bar.count()

        then:
        numFoos == 1000
        numBars == 1000
        def time = System.currentTimeMillis() - start
        println "count time: $time"
    }
}
