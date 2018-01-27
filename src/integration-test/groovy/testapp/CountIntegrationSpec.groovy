package testapp

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class CountIntegrationSpec extends Specification {
    void "test count Foos and Bars"() {
        given:
        SetupData.setupData()

        when: "we count the Foo and Bar collections"
        long start = System.currentTimeMillis()
        int numFoos = Foo.count()
        int numBars = Bar.count()

        then:"the queries take less than 50ms"
        numFoos == 1000
        numBars == 1000
        def time = System.currentTimeMillis() - start
        println "count time: $time"
    }
}
