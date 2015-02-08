package pl.antipattern.goldenquotes.ui

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import pl.antipattern.goldenquotes.R
import pl.antipattern.goldenquotes.data.model.Author
import pl.antipattern.goldenquotes.data.model.Quote

import javax.inject.Inject

import static nl.qbusict.cupboard.CupboardFactory.cupboard

@CompileStatic
class MainActivity extends BaseActivity {

    @Inject
    @PackageScope
    SQLiteDatabase db

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .add(R.id.container, new RandomQuoteFragment())
                .commit();
    }
}
