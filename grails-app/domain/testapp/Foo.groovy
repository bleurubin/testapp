package testapp

class Foo {
    String name
    Bar bar
    Car car

    static mapping = {
        bar fetch: 'join'
        car fetch: 'join'
    }

    static constraints = {
    }
}
