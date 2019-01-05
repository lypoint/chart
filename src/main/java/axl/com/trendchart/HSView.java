package axl.com.trendchart;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class HSView extends HorizontalScrollView {
    private HorizontalScrollView another;

    public HSView(Context context) {
        super(context);
    }

    public HSView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HSView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HSView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setAnother(HorizontalScrollView another) {
        this.another = another;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (another != null) {
            another.scrollTo(l, t);
        }
    }
}
