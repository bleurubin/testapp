package testapp

class Bar {
    String name
    Car car

    static mapping = {
        car fetch: 'join'
    }

    static constraints = {
    }
}
