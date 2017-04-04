package com.siestasystemheadpod.headpodv10.fragments.rasgos;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.RasgosDesigner;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.arco.ArcoElement;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.avatar.Avatar;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.bar.IndicatorBar;

/**
 * Created by plaftware
 */

public class RasgosView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private Handler handler;
    private RasgosDesigner rasgosDesigner;

    private ArcoElement arcoElement;
    private IndicatorBar indicatorBar;

    public RasgosView(Context context) {
        super(context);
        init();
    }

    public RasgosView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RasgosView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public RasgosView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        handler = new Handler();
        rasgosDesigner = new RasgosDesigner(getContext());
        getHolder().addCallback(this);
        setZOrderOnTop(true);
        //setZOrderMediaOverlay(true); Para agregar view arriba
        getHolder().setFormat(PixelFormat.TRANSPARENT);

        // init elements
        // avatar
        Avatar avatar = new Avatar(rasgosDesigner);

        // arco
        arcoElement = new ArcoElement(rasgosDesigner);

        // bar
        indicatorBar = new IndicatorBar(rasgosDesigner);
        indicatorBar.setAfterBottonOf(avatar);
    }

    /**
     * Este metodo permite actualizar el valor de cada uno de los indicadores
     * */
    public RasgosView indicador(int index, int percentage) {
        if (percentage < 0)
            percentage = 0;

        if (percentage > 100)
            percentage = 100;

        indicador0(index, percentage);
        return this;
    }

    private void indicador0(int index, int percentage) {
        arcoElement.indicador(index, percentage);
        indicatorBar.indicador(index, percentage);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        rasgosDesigner.target(canvas);
    }

    public void render() {
        handler.post(this);
    }

    public void clear() {
        for (int i = 0; i < 11; i++) {
            indicador0(i + 1, 0);
        }
        render();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //render();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        render();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void run() {
        Canvas canvas = null;
        try {
            canvas = getHolder().lockCanvas();
            synchronized (getHolder()) {
                draw(canvas);
            }
        } finally {
            if (canvas != null) {
                getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }
}
