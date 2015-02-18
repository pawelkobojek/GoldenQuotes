package pl.antipattern.goldenquotes.ui

import android.os.Bundle
import android.support.v7.widget.Toolbar
import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import pl.antipattern.goldenquotes.R
import pl.antipattern.goldenquotes.event.AuthorInfoClickedEvent
import pl.antipattern.goldenquotes.event.bus.EventBus

import javax.inject.Inject

@CompileStatic
class MainActivity extends BaseActivity {

    @Inject
    @PackageScope
    EventBus bus

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar = findViewById(R.id.toolbar) as Toolbar

        bus.register(this)

        supportFragmentManager.beginTransaction()
                .add(R.id.container, new RandomQuoteFragment())
                .commit();
    }

    @Override
    protected void onDestroy() {
        bus.unregister(this)
        super.onDestroy()
    }

    @SuppressWarnings("unused")
    void onEvent(AuthorInfoClickedEvent event) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, AuthorInfoFragment.newInstance(event.authorName))
                .addToBackStack(null)
                .commit()
    }
}
