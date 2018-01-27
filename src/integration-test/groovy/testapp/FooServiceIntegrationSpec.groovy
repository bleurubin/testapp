package testapp

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class FooServiceIntegrationSpecSpec extends Specification {

  def setupData() {
    for(int i=0; i< 1000; i++) {
      Bar bar = new Bar(name: 'bar').save(flush: true, failOnError: true)
      Foo foo = new Foo(name: 'foo', bar: bar).save(flush: true, failOnError: true)
    }
  }

  void "test querying Foos and Bars"() {
    given:
    setupData()

    when: "we count the Foo and Bar collections"
    long start = System.currentTimeMillis()
    int numFoos = Foo.count()
    int numBars = Bar.count()

    then:"the queries took less than 100ms"
    numFoos == 1000
    numBars == 1000
    def time = System.currentTimeMillis() - start
    println "time: $time"
    time < 100

    when: "we find all foos"
    start = System.currentTimeMillis()
    List<Foo> foos = Foo.findAll()
    time = System.currentTimeMillis() - start

    then: "the query took less than 100ms"
    println "time: $time"
    time < 100

    when: "we call size() on foos"
    start = System.currentTimeMillis()
    foos.size() == 1000
    time = System.currentTimeMillis() - start
    println "time: $time"

    then: "it takes longer than you'd think"
    time > 30
  }

  void "test referencing only foo.name"() {
    given:
    setupData()

    when: "we find all foos and reference the name of each foo"
    long start = System.currentTimeMillis()
    List<Foo> foos = Foo.findAll()
    for (Foo foo in foos) {
      if (foo.name == 'john') {
        println "found john"
      }
    }
    def time = System.currentTimeMillis() - start

    then: "the query took less than 150ms"
    println "time: $time"
    time > 100

    when: "we call size() on foos"
    start = System.currentTimeMillis()
    foos.size() == 1000
    time = System.currentTimeMillis() - start
    println "time: $time"

    then: "it is quicker cuz the foos have been marshalled"
    time < 10
  }

  void "test referencing bar is slower"() {
    given:
    setupData()

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
    println "time: $time"
    time > 400
  }
}
