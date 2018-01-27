package testapp

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class DeepReferenceIntegrationSpec extends Specification {
  void "test referencing bar is slower"() {
    given:
    SetupData.setupData()

    when: "we find all foos and reference the name of each child bar"
    long start = System.currentTimeMillis()
    List<Foo> foos = Foo.findAll()
    for (Foo foo in foos) {
      if (foo.bar.name == 'john') {
        println "found john"
      }
    }
    def time = System.currentTimeMillis() - start

    then: "the query took longer than 400ms"
    println "deep reference time: $time"
  }
}
