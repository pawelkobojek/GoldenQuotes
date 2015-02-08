package pl.antipattern.goldenquotes.dagger

import android.content.Context
import dagger.ObjectGraph
import groovy.transform.CompileStatic

@CompileStatic
final class Injector {

    private static Context context
    private static ObjectGraph graph

    static void init(Context context) {
        this.context = context
    }

    static void inject(Object o) {
        if (graph == null) {
            graph = ObjectGraph.create(new QuotesModule(context))
        }
        graph.inject(o)
    }
}
