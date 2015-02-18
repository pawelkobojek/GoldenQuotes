package pl.antipattern.goldenquotes.event

import groovy.transform.CompileStatic
import groovy.transform.Immutable;

@CompileStatic
@Immutable
class AuthorInfoClickedEvent {
    String authorName
}
