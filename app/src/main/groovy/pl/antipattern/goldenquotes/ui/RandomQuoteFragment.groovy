package pl.antipattern.goldenquotes.ui

import android.os.Bundle
import android.support.annotation.Nullable
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

    private TextView tvQuote
    private Button btnRandomize

    @Override
    View onCreateView(LayoutInflater inflater,
                      @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_random_quote, container, false)

        tvQuote = root.findViewById(R.id.tvQuote) as TextView
        btnRandomize = root.findViewById(R.id.btnRandomize) as Button
        btnRandomize.onClickListener = {
            data.randomQuote().observeOn(mainThread()).subscribe({
                tvQuote.text = it.text
            }, {
                tvQuote.text = "ERROR :("
            })
        }

        data.randomQuote().observeOn(mainThread()).subscribe({
            tvQuote.text = it.text
        }, {
            tvQuote.text = "ERROR :("
        })

        root
    }


}
