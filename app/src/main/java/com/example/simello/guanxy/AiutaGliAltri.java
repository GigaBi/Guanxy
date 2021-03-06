package com.example.simello.guanxy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.example.simello.aiuta.gli.altri.TabAiutaGliAltri;
import com.example.simello.utils.utils;

/**
 * Created by sunfury & simello on 22/01/15.
 */

//TODO pull down aggiorna con altri cinque utenti
//TODO aggiornare distanza e tempo tramite script ( attenzione al textfield con lo \n )
//TODO estendere l'xml del primo utente agli altri quattro
//TODO aggiungere i bottoni in basso guanxy punti guida
public class AiutaGliAltri extends ActionBarActivity
{
    private int flagSimelloFreccia = 0;

    public OnLongClickListener longClickListner;
    LinearLayout panel1,panel2,panel3,panel4,panel5;
    TextView text1,text2,text3,text4,text5;
    View openLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aiuta_gli_altri);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        panel1 = (LinearLayout) findViewById(R.id.panel1);


//panel1.setVisibility(View.VISIBLE); OVERLOLLING

//panel1.setVisibility(View.VISIBLE);

//Log.v("CZ","height at first ..." + panel1.getMeasuredHeight()); OVERLOLLING MADLY AROUNd

        text1 = (TextView) findViewById(R.id.text1);


        text1.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if(flagSimelloFreccia == 0) {
                    text1.setBackgroundResource(R.drawable.freccia_aperto);
                    flagSimelloFreccia = 1;
                }
                else if(flagSimelloFreccia == 1) {
                    text1.setBackgroundResource((R.drawable.freccia_chiusa));
                    flagSimelloFreccia = 0;
                }
                hideOthers(v);
            }
        });

        ImageButton mappa1 = (ImageButton) findViewById(R.id.bottone_mappa1);
        mappa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //todo RICORDATI DI CAMBIARE QUI
                Intent myIntent = new Intent(AiutaGliAltri.this, TabAiutaGliAltri.class);
                AiutaGliAltri.this.startActivity(myIntent);
                overridePendingTransition(0, 0);


            }

            ;
        });


        //REPARTO BOTTONI
        //Prendo il bottone e setto a true x lasciare il colore blue
        //DA RICORDARE
        //Quando si premeranno gli altri bottoni (Punti o Guida) il valore di pressed di Guanxy deve essere messo a false
        final Button guanxy = (Button) findViewById(R.id.guanxy);
        //Prendo il singolo bottone
        final Button punti = (Button) findViewById(R.id.punti);

        final Button guida = (Button) findViewById(R.id.guida);

        //GESTIONE COLORE BOTTONI
        guanxy.setPressed(true);
        guanxy.setSelected(true);
        guanxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);

                //Con queste due righ, quando premo Guanxy, cambia il colore di Punti/Guida(da aggiungere)
                punti.setPressed(false);
                punti.setSelected(false);

            };
        });

        punti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);

                //Con queste due righe, quando premo Punti, cambia il colore di Guanxy/Guida(da aggiungere)
                guanxy.setSelected(false);
                guanxy.setPressed(false);


                Intent myIntent = new Intent(AiutaGliAltri.this, PuntiActivity.class);
                AiutaGliAltri.this.startActivity(myIntent);
                overridePendingTransition(0, 0);

            };
        });

        guida.setOnClickListener( new View.OnClickListener() {

            public void onClick(View v){
                v.setSelected(true);

                guanxy.setSelected(false);
                guanxy.setPressed(false);

                Intent myIntent = new Intent(AiutaGliAltri.this, GuidaActivity.class);
                AiutaGliAltri.this.startActivity(myIntent);
                overridePendingTransition(0, 0);

            }
        });


    }
    private void hideThemAll()
    {
        if(openLayout == null) return;
        if(openLayout == panel1)
            panel1.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel1, true));

    }
    private void hideOthers(View layoutView)
    {
        {
            int v;
            if(layoutView.getId() == R.id.text1)
            {
                v = panel1.getVisibility();
                if(v != View.VISIBLE)
                {
                    panel1.setVisibility(View.VISIBLE);
                    Log.v("CZ","height..." + panel1.getHeight());
                }

//panel1.setVisibility(View.GONE);
//Log.v("CZ","again height..." + panel1.getHeight());
                hideThemAll();
                if(v != View.VISIBLE)
                {
                    panel1.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel1, true));
                }
            }

        }
    }

    public class ScaleAnimToHide extends ScaleAnimation
    {

        private View mView;

        private LayoutParams mLayoutParams;

        private int mMarginBottomFromY, mMarginBottomToY;

        private boolean mVanishAfter = false;

        public ScaleAnimToHide(float fromX, float toX, float fromY, float toY, int duration, View view,boolean vanishAfter)
        {
            super(fromX, toX, fromY, toY);
            setDuration(duration);
            openLayout = null;
            mView = view;
            mVanishAfter = vanishAfter;
            mLayoutParams = (LayoutParams) view.getLayoutParams();
            int height = mView.getHeight();
            mMarginBottomFromY = (int) (height * fromY) + mLayoutParams.bottomMargin - height;
            mMarginBottomToY = (int) (0 - ((height * toY) + mLayoutParams.bottomMargin)) - height;

            Log.v("CZ","height..." + height + " , mMarginBottomFromY...." + mMarginBottomFromY  + " , mMarginBottomToY.." +mMarginBottomToY);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t)
        {
            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime < 1.0f)
            {
                int newMarginBottom = mMarginBottomFromY + (int) ((mMarginBottomToY - mMarginBottomFromY) * interpolatedTime);
                mLayoutParams.setMargins(mLayoutParams.leftMargin, mLayoutParams.topMargin,mLayoutParams.rightMargin, newMarginBottom);
                mView.getParent().requestLayout();
                //Log.v("CZ","newMarginBottom..." + newMarginBottom + " , mLayoutParams.topMargin..." + mLayoutParams.topMargin);
            }
            else if (mVanishAfter)
            {
                mView.setVisibility(View.GONE);
            }
        }
    }
    public class ScaleAnimToShow extends ScaleAnimation
    {

        private View mView;

        private LayoutParams mLayoutParams;

        private int mMarginBottomFromY, mMarginBottomToY;

        private boolean mVanishAfter = false;

        public ScaleAnimToShow(float toX, float fromX, float toY, float fromY, int duration, View view,boolean vanishAfter)
        {
            super(fromX, toX, fromY, toY);
            openLayout = view;
            setDuration(duration);
            mView = view;
            mVanishAfter = vanishAfter;
            mLayoutParams = (LayoutParams) view.getLayoutParams();
            mView.setVisibility(View.VISIBLE);
            int height = mView.getHeight();
            //mMarginBottomFromY = (int) (height * fromY) + mLayoutParams.bottomMargin + height;
            //mMarginBottomToY = (int) (0 - ((height * toY) + mLayoutParams.bottomMargin)) + height;

            mMarginBottomFromY = 0;
            mMarginBottomToY = height;

            Log.v("CZ", ".................height..." + height + " , mMarginBottomFromY...." + mMarginBottomFromY + " , mMarginBottomToY.." + mMarginBottomToY);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t)
        {
            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime < 1.0f)
            {
                int newMarginBottom = (int) ((mMarginBottomToY - mMarginBottomFromY) * interpolatedTime) - mMarginBottomToY;
                mLayoutParams.setMargins(mLayoutParams.leftMargin, mLayoutParams.topMargin,mLayoutParams.rightMargin, newMarginBottom);
                mView.getParent().requestLayout();
                //Log.v("CZ","newMarginBottom..." + newMarginBottom + " , mLayoutParams.topMargin..." + mLayoutParams.topMargin);
            }
        }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                overridePendingTransition(0,0);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo che controlla la perdita del FOCUS della schermata attuale
     * @param hasFocus
     */
    //@todo Da sistemare l'onResume, deve chiudere per bene il Dialog
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub

        super.onWindowFocusChanged(hasFocus);

        utils.connect(hasFocus,this);

    }
}
