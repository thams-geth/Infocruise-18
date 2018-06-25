package com.tts.infocruise18;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private TextView guidelines;
    private TextView topics;
    private TextView breif;
    private CardView cardView;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);

        topics=(TextView)findViewById(R.id.tv_topics);
        breif=(TextView)findViewById(R.id.tv_breif);
        guidelines=(TextView) findViewById(R.id.tv_guidlines);
        cardView=(CardView) findViewById(R.id.card_topics);
        b=getIntent().getExtras();
        if(b.getInt("position")==1){

            breif.setText(this.getResources().getString (R.string.CODEZERS));
            guidelines.setText(this.getResources().getString (R.string.CODEZERS_guid));
            collapsingToolbarLayout.setBackgroundResource(R.drawable.bug);
            collapsingToolbarLayout.setTitle("CODEZERS");

        }else if(b.getInt("position")==2){

            breif.setText(this.getResources().getString (R.string.WEBWAR));
            guidelines.setText(this.getResources().getString (R.string.WEBWAR_guid));
            collapsingToolbarLayout.setBackgroundResource(R.drawable.code);
            collapsingToolbarLayout.setTitle("WEB WAR");
        }else if(b.getInt("position")==3){

            breif.setText(this.getResources().getString (R.string.PAPERENZA));
            guidelines.setText(this.getResources().getString (R.string.PAPERENZA_guid));
            collapsingToolbarLayout.setBackgroundResource(R.drawable.papper);
            topics.setText(this.getResources().getString (R.string.PAPERENZA_topics));
            cardView.setVisibility(View.VISIBLE);
            collapsingToolbarLayout.setTitle("PAPERENZA");
        }else if(b.getInt("position")==4){

            breif.setText(this.getResources().getString (R.string.project));
            guidelines.setText(this.getResources().getString (R.string.project_guid));
            collapsingToolbarLayout.setBackgroundResource(R.drawable.project);
            collapsingToolbarLayout.setTitle("PROJECT PRESENTO");
        }else if(b.getInt("position")==5){

            breif.setText(this.getResources().getString (R.string.GOGOOGLE));
            guidelines.setText(this.getResources().getString (R.string.GOGOOGLE_guid));
            collapsingToolbarLayout.setBackgroundResource(R.drawable.google);
            collapsingToolbarLayout.setTitle("GO GOOGLE");
        }else if(b.getInt("position")==6){

            breif.setText(this.getResources().getString (R.string.TECHNOFREAKS));
            guidelines.setText(this.getResources().getString (R.string.TECHNOFREAKS_guid));
            collapsingToolbarLayout.setBackgroundResource(R.drawable.team);
            collapsingToolbarLayout.setTitle("TECHNO FREAKS");
        }else if(b.getInt("position")==7){

            breif.setText(this.getResources().getString (R.string.SEARCHOSEEK));
            guidelines.setText(this.getResources().getString (R.string.SEARCHOSEEK_guide));
            collapsingToolbarLayout.setBackgroundResource(R.drawable.kecrun);
            collapsingToolbarLayout.setTitle("SEARCH O SEARCH");
        }else if(b.getInt("position")==8){

            breif.setText(this.getResources().getString (R.string.ZAM));
            guidelines.setText(this.getResources().getString (R.string.ZAM_guid));
            collapsingToolbarLayout.setBackgroundResource(R.drawable.brainy);
            collapsingToolbarLayout.setTitle("ZAM");
        }else if(b.getInt("position")==9){
            breif.setText(this.getResources().getString (R.string.MRMSINFOCRUISE));
            guidelines.setText(this.getResources().getString (R.string.MSINFOCRUISE_guid));
            collapsingToolbarLayout.setBackgroundResource(R.drawable.solostar);
            collapsingToolbarLayout.setTitle("MR/MS INFOCRUISE");
        }else if(b.getInt("position")==10){

            breif.setText(this.getResources().getString (R.string.AGAMEHASNONAME));
            guidelines.setText(this.getResources().getString (R.string.AGAMEHASNONAME_guid));
            collapsingToolbarLayout.setBackgroundResource(R.drawable.mediafury);
            collapsingToolbarLayout.setTitle("A GAME HAS NO GAME");
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Please Register the event.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent=new Intent(DetailsActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}
