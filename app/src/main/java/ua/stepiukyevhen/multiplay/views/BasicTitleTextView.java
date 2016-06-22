package ua.stepiukyevhen.multiplay.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


public class BasicTitleTextView extends AppCompatTextView {
    public BasicTitleTextView(Context context) {
        super(context);
        init();
    }

    public BasicTitleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BasicTitleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setTypeface(Typeface.createFromAsset(
                getContext().getAssets(),
                "fonts/sans.ttf"));
    }
}
