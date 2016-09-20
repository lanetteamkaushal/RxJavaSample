package lanet.bhavin.rxjavasample.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lcom75 on 20/9/16.
 */
public class DynamicView extends View {
    public DynamicView(Context context) {
        super(context);
    }

    public DynamicView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DynamicView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}
