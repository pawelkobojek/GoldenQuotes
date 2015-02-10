package pl.antipattern.goldenquotes.ui

import android.os.Bundle
import android.support.v7.app.ActionBarActivity
import groovy.transform.CompileStatic
import pl.antipattern.goldenquotes.dagger.Injector

@CompileStatic
abstract class BaseActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        Injector.inject(this)
    }
}
