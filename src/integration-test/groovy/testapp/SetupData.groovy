package testapp

class SetupData {
    def static setupData() {
        for(int i=0; i< 1000; i++) {
            Bar bar = new Bar(name: 'bar').save(flush: true, failOnError: true)
            Foo foo = new Foo(name: 'foo', bar: bar).save(flush: true, failOnError: true)
        }
    }
}
