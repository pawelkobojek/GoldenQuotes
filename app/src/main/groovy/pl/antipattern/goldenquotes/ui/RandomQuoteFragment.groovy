package pl.antipattern.goldenquotes.ui

import android.os.Bundle
import android.os.Looper
import android.support.annotation.Nullable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import pl.antipattern.goldenquotes.R
import pl.antipattern.goldenquotes.data.QuotesData

import javax.inject.Inject

import static rx.android.schedulers.AndroidSchedulers.mainThread

@CompileStatic
class RandomQuoteFragment extends BaseFragment {

    @Inject
    @PackageScope
    QuotesData data

    @Override
    View onCreateView(LayoutInflater inflater,
                      @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        def root = inflater.inflate(R.layout.fragment_random_quote, container, false)
        def tvQuote = root.findViewById(R.id.tvQuote) as TextView
        def tvAuthor = root.findViewById(R.id.tvAuthor) as TextView
        def btnRandomize = root.findViewById(R.id.btnRandomize) as Button

        btnRandomize.onClickListener = {
            data.randomQuote().observeOn(mainThread()).subscribe({
                tvQuote.text = it.content
                tvAuthor.text = it.author.name
            }, {
                tvQuote.text = "ERROR :("
            })
        }

        data.randomQuote().observeOn(mainThread()).subscribe({
            tvQuote.text = it.content
            tvAuthor.text = it.author.name
        }, {
            tvQuote.text = "ERROR :("
        })

        root
    }
}
