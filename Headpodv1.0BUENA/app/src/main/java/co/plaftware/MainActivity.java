package co.plaftware;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.SeekBar;

import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.RasgosView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener{

    RasgosView rasgosView;

    private SparseIntArray indexSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        rasgosView = (RasgosView) findViewById(R.id.rasgos);

        indexSeekBar = new SparseIntArray();
        indexSeekBar.put(R.id.ind1, 1);
        indexSeekBar.put(R.id.ind2, 2);
        indexSeekBar.put(R.id.ind3, 3);
        indexSeekBar.put(R.id.ind4, 4);
        indexSeekBar.put(R.id.ind5, 5);
        indexSeekBar.put(R.id.ind6, 6);
        indexSeekBar.put(R.id.ind7, 7);
        indexSeekBar.put(R.id.ind8, 8);
        indexSeekBar.put(R.id.ind9, 9);
        indexSeekBar.put(R.id.ind10, 10);
        indexSeekBar.put(R.id.ind11, 11);

        ((SeekBar)findViewById(R.id.ind1)).setOnSeekBarChangeListener(this);
        ((SeekBar)findViewById(R.id.ind2)).setOnSeekBarChangeListener(this);
        ((SeekBar)findViewById(R.id.ind3)).setOnSeekBarChangeListener(this);
        ((SeekBar)findViewById(R.id.ind4)).setOnSeekBarChangeListener(this);
        ((SeekBar)findViewById(R.id.ind5)).setOnSeekBarChangeListener(this);
        ((SeekBar)findViewById(R.id.ind6)).setOnSeekBarChangeListener(this);
        ((SeekBar)findViewById(R.id.ind7)).setOnSeekBarChangeListener(this);
        ((SeekBar)findViewById(R.id.ind8)).setOnSeekBarChangeListener(this);
        ((SeekBar)findViewById(R.id.ind9)).setOnSeekBarChangeListener(this);
        ((SeekBar)findViewById(R.id.ind10)).setOnSeekBarChangeListener(this);
        ((SeekBar)findViewById(R.id.ind11)).setOnSeekBarChangeListener(this);

        // Este es un ejemplo de como pasar los datos al componente
        rasgosView.indicador(6, 20)
                .indicador(5, 30)
                .indicador(4, 40)
                .indicador(3, 10)
                .render();
    }


    @Override
    public void onClick(View v) {
       //rasgosView.setColor(rasgosView.getColor() == Color.BLACK ? Color.RED : Color.BLACK);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        rasgosView.indicador(indexSeekBar.get(seekBar.getId()), progress).render();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
