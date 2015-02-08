package pl.antipattern.goldenquotes.data.model

import groovy.transform.CompileStatic

@CompileStatic
class Quote {
    Long _id
    Author author
    String text
}
