package testapp

import org.bson.types.ObjectId

class Foo {
    ObjectId id
    String name
    Bar bar
    Car car

    static constraints = {
    }
}
