package pl.antipattern.goldenquotes.ui

import android.app.Activity
import android.support.v4.app.Fragment
import groovy.transform.CompileStatic
import pl.antipattern.goldenquotes.dagger.Injector

@CompileStatic
abstract class BaseFragment extends Fragment {

    @Override
    void onAttach(Activity activity) {
        super.onAttach(activity)
        Injector.inject(this)
    }
}
