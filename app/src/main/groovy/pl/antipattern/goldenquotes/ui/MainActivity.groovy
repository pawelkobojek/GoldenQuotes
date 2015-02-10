package pl.antipattern.goldenquotes.ui

import android.os.Bundle
import groovy.transform.CompileStatic
import pl.antipattern.goldenquotes.R

@CompileStatic
class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .add(R.id.container, new RandomQuoteFragment())
                .commit();
    }
}
