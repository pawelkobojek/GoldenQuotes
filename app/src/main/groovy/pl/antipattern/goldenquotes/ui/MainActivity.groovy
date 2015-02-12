package pl.antipattern.goldenquotes.ui

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import groovy.transform.CompileStatic
import pl.antipattern.goldenquotes.R

@CompileStatic
class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar = findViewById(R.id.toolbar) as Toolbar

        supportFragmentManager.beginTransaction()
                .add(R.id.container, new RandomQuoteFragment())
                .commit();
    }
}
