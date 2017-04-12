package com.siestasystemheadpod.headpodv10.fragments.rasgos.element.avatar;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;

import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.AbstractRasgosElement;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.RasgosDesigner;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.bar.IndicatorBar;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.utils.AndroidBitmapUtils;

//import co.plaftware.R;
import com.siestasystemheadpod.headpodv10.R;
//import com.siestasystemheadpod.headpodv10.fragments.InformeFragment;

/**
 * Created by plaftware
 */

public class Avatar extends AbstractRasgosElement implements IndicatorBar.AfterBottonOf{

    private int top;
    private int heigthAvatar;
    private int avatar = R.drawable.avatar;

    public Avatar(RasgosDesigner rasgosDesigner) {
        super(rasgosDesigner);
    }

    @Override
    public void draw() {
        int PERCENT_IN_DESKTOP = 40;
        int widthAvatar = (int)rasgosDesigner.getWidth(PERCENT_IN_DESKTOP);
        heigthAvatar = (int)rasgosDesigner.getHeight(PERCENT_IN_DESKTOP);
        int centerAvatarX = widthAvatar / 2;
        int centerAvatarY = heigthAvatar / 2;
        top = (int) rasgosDesigner.getCenterY() - centerAvatarY;
        int left = (int) rasgosDesigner.getCenterX() - centerAvatarX;

        Bitmap bmp = AndroidBitmapUtils
                .getBitmapFromVectorDrawable(rasgosDesigner.getContext(),
                        avatar, widthAvatar, heigthAvatar);
        rasgosDesigner.getCanvas().drawBitmap(bmp, left, top, null);
    }

    @Override
    public int getBotton() {
        return top + heigthAvatar + (int)rasgosDesigner.getHeight(2);
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(@DrawableRes int avatar) {
        this.avatar = avatar;
    }
}
