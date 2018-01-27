package testapp

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class FooServiceIntegrationSpecSpec extends Specification {

  def setup() {
    Bar bar = new Bar(name: 'bar').save(flush: true, failOnError: true)
    Foo foo = new Foo(name: 'foo', bar: bar).save(flush: true, failOnError: true)
  }

  void "test something"() {
    expect:"fix me"
      Bar.count() == 1
      Foo.count() == 1
  }
}
