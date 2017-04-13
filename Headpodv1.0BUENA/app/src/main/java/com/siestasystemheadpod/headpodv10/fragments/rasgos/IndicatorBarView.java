package com.siestasystemheadpod.headpodv10.fragments.rasgos;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.RasgosDesigner;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.bar.IndicatorBar;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.bar.config.OnlyConfig;

/**
 * Created by plaftware
 */

public class IndicatorBarView extends View implements RasgosView.RasgosListener {

    private IndicatorBar indicatorBar;
    private RasgosDesigner rasgosDesigner;

    public IndicatorBarView(Context context) {
        super(context);
        init();
    }

    public IndicatorBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IndicatorBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public IndicatorBarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        this.post(new Runnable() {
            @Override
            public void run() {
                float heigth = getWidth() * 20.9f / 100;
                getLayoutParams().height = (int) heigth;
                setLayoutParams(getLayoutParams());
                invalidate();
            }
        });

        rasgosDesigner = new RasgosDesigner(getContext());
        indicatorBar = new IndicatorBar(rasgosDesigner, new OnlyConfig());
        indicatorBar.setAfterBottonOf(new IndicatorBar.AfterBottonOf() {
            @Override
            public int getBotton() {
                return 5;
            }
        });
    }

    @Override
    public void indicador(int index, int percentage) {
        indicatorBar.indicador(index, percentage);
    }

    @Override
    public void render() {
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        rasgosDesigner.target(canvas);
    }
}
