package pl.antipattern.goldenquotes

import android.app.Application
import groovy.transform.CompileStatic
import pl.antipattern.goldenquotes.dagger.Injector

@CompileStatic
class QuotesApp extends Application {

    @Override
    void onCreate() {
        super.onCreate()
        Injector.init(this)
    }
}
