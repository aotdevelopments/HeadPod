package com.siestasystemheadpod.headpodv10.adicionales.informe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.siestasystemheadpod.headpodv10.R;

/**
 * Subproceso para caragar los elemenos de la vista del InformeFragment
 */
public class MySurfaceView extends SurfaceView {

    private SurfaceHolder surfaceHolder;
    private Bitmap bmpIcon;

    public MySurfaceView(Context context) {
        super(context);
        init(context);
    }

    public MySurfaceView(Context context,
                         AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MySurfaceView(Context context,
                         AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        surfaceHolder = getHolder();
        bmpIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.rango_inclinacion_prueba);
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Canvas canvas = holder.lockCanvas(null);
                drawSomething(canvas);
                holder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder,
                                       int format, int width, int height) {
                // TODO Auto-generated method stub

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // TODO Auto-generated method stub

            }
        });
    }

    protected void drawSomething(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(bmpIcon,
                getWidth(), getHeight(), null);
    }
}


/*
public class MySurfaceView extends SurfaceView implements
        SurfaceHolder.Callback {

    private TutorialThread _thread;

    public MySurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
        _thread = new TutorialThread(getHolder(), this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap _scratch = BitmapFactory.decodeResource(getResources(),
                R.drawable.g11_20);
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(_scratch, 10, 10, null);

    }

    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    public void surfaceCreated(SurfaceHolder arg0) {
        _thread.setRunning(true);
        _thread.start();
    }

    public void surfaceDestroyed(SurfaceHolder arg0) {
        boolean retry = true;
        _thread.setRunning(false);
        while (retry) {
            try {
                _thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    class TutorialThread extends Thread {
        private SurfaceHolder _surfaceHolder;
        private MySurfaceView _panel;
        private boolean _run = false;

        public TutorialThread(SurfaceHolder surfaceHolder, MySurfaceView panel) {
            _surfaceHolder = surfaceHolder;
            _panel = panel;
        }

        public void setRunning(boolean run) {
            _run = run;
        }

        @Override
        public void run() {
            Canvas c;
            while (_run) {
                c = null;
                try {
                    c = _surfaceHolder.lockCanvas(null);
                    synchronized (_surfaceHolder) {
                        _panel.draw(c);
                    }
                } finally {
                    if (c != null) {
                        _surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }
    }
}

*/

