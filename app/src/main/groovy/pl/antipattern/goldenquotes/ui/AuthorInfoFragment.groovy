package pl.antipattern.goldenquotes.ui

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import pl.antipattern.goldenquotes.R
import pl.antipattern.goldenquotes.data.QuotesData

import javax.inject.Inject

import static rx.android.schedulers.AndroidSchedulers.mainThread

@CompileStatic
class AuthorInfoFragment extends BaseFragment {

    @Inject
    @PackageScope
    QuotesData data

    static Fragment newInstance(String authorName) {
        def fragment = new AuthorInfoFragment()
        def args = new Bundle()
        args.putString("author_name", authorName)
        fragment.arguments = args
        fragment
    }

    @Override
    View onCreateView(LayoutInflater inflater,
                      @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        def root = inflater.inflate(R.layout.fragment_author_info, container, false)
        def tvAuthor = root.findViewById(R.id.tvAuthor) as TextView

        bind(data.authorByName(arguments.getString("author_name")))
                .observeOn(mainThread())
                .subscribe({
            tvAuthor.text = it.name
        })

        root
    }
}
