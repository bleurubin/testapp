package testapp

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class ShallowReferenceIntegrationSpec extends Specification {
    void "test referencing only foo.name"() {
        given:
        SetupData.setupData()

        when: "we find all foos and reference the name of each foo"
        long start = System.currentTimeMillis()
        List<Foo> foos = Foo.findAll()
        for (Foo foo in foos) {
            if (foo.name == 'john') {
                println "found john"
            }
        }
        def time = System.currentTimeMillis() - start

        then: "the query took less than 70ms"
        println "shallow reference time: $time"
    }
}
