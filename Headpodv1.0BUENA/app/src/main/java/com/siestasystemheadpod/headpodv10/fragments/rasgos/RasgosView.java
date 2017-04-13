package com.siestasystemheadpod.headpodv10.fragments.rasgos;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.RasgosDesigner;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.arco.ArcoElement;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.arco.config.FlexionConfig;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.arco.config.InclinationConfig;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.avatar.Avatar;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.bar.IndicatorBar;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.bar.config.IncludeConfig;

/**
 * Created by plaftware
 */

public class RasgosView extends View implements Runnable{

    private RasgosDesigner rasgosDesigner;

    private ArcoElement arcoElement;
    private IndicatorBar indicatorBar;
    private Avatar avatar;
    private Type type;
    private RasgosListener rasgosListener;

    public enum Type{
        INCLINATION, FLEXION;

        static Type valueOf(int type){
            return type == getDefault() ? INCLINATION : FLEXION;
        }

        static int getDefault(){
            return 0;
        }
    }

    public interface RasgosListener{

        void indicador(int index, int percentage);

        void render();
    }

    public RasgosView(Context context) {
        super(context);
        init(null);
    }

    public RasgosView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RasgosView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(21)
    public RasgosView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    @Override
    public void run() {

    }

    private void init(AttributeSet attrs) {
        this.post(new Runnable() {
            @Override
            public void run() {
                float heigth = getWidth() * 119.04762f / 100;
                getLayoutParams().height = (int) heigth;
                setLayoutParams(getLayoutParams());
                invalidate();
            }
        });

        // init elements
        type = Type.INCLINATION;

        if(attrs != null){
            TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.RasgosView, 0, 0);
            type = Type.valueOf(typedArray.getInt(R.styleable.RasgosView_type, Type.getDefault()));
        }

        rasgosDesigner = new RasgosDesigner(getContext());

        // avatar
        avatar = new Avatar(rasgosDesigner);
        avatar.setAvatar(type == Type.INCLINATION ? R.drawable.avatar : R.drawable.avatar_flexion);

        // arco
        ArcoElement.Config config = type == Type.INCLINATION ? new InclinationConfig() : new FlexionConfig();
        arcoElement = new ArcoElement(rasgosDesigner, config);

        // bar
        if(type == Type.INCLINATION){
            indicatorBar = new IndicatorBar(rasgosDesigner, new IncludeConfig());
            indicatorBar.setAfterBottonOf(avatar);
        }
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

        if(rasgosListener != null){
            rasgosListener.indicador(index, percentage);
        }

        if(type == Type.INCLINATION){
            indicatorBar.indicador(index, percentage);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        rasgosDesigner.target(canvas);
    }

    public void render() {
        invalidate();

        if(rasgosListener != null){
            rasgosListener.render();
        }
    }

    public void clear() {
        for (int i = 0; i < 11; i++) {
            indicador0(i + 1, 0);
        }
        render();
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public RasgosListener getRasgosListener() {
        return rasgosListener;
    }

    public void setRasgosListener(RasgosListener rasgosListener) {
        this.rasgosListener = rasgosListener;
        if(rasgosListener != null){
            rasgosListener.render();
        }
    }
}
