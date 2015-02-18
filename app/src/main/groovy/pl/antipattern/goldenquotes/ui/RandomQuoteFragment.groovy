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
import pl.antipattern.goldenquotes.data.model.Quote
import pl.antipattern.goldenquotes.event.AuthorInfoClickedEvent
import pl.antipattern.goldenquotes.event.bus.EventBus
import rx.android.view.ViewObservable

import javax.inject.Inject

import static rx.android.schedulers.AndroidSchedulers.mainThread

@CompileStatic
class RandomQuoteFragment extends BaseFragment {

    @Inject
    @PackageScope
    QuotesData data

    @Inject
    @PackageScope
    EventBus bus

    private Quote quote

    @Override
    View onCreateView(LayoutInflater inflater,
                      @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        def root = inflater.inflate(R.layout.fragment_random_quote, container, false)

        setupDataFetching(root)
        setupNavigation(root)

        root
    }

    private void setupDataFetching(View root) {
        def tvQuote = root.findViewById(R.id.tvQuote) as TextView
        def tvAuthor = root.findViewById(R.id.tvAuthor) as TextView
        def btnRandomize = root.findViewById(R.id.btnRandomize) as Button

        bind(ViewObservable.clicks(btnRandomize, true).flatMap({
            data.randomQuote()
        })).observeOn(mainThread()).subscribe({
            quote = it
            tvQuote.text = quote.content
            tvAuthor.text = quote.author.name
        })
    }

    private void setupNavigation(View root) {
        def btnAuthor = root.findViewById(R.id.btnAuthor) as Button
        bind(ViewObservable.clicks(btnAuthor, false)).subscribe({
            bus.post(new AuthorInfoClickedEvent(quote.author.name))
        })
    }
}
