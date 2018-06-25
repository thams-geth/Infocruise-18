package com.tts.infocruise18;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.mapzen.speakerbox.Speakerbox;

import java.util.Locale;

public class MainActivityApp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TextToSpeech tts;
    AnimationDrawable roartAnimation;
    Button home;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

        ImageView roarImage = (ImageView) findViewById(R.id.iv_roar);
        roarImage.setBackgroundResource(R.drawable.animation_roar);
        home=(Button)findViewById(R.id.b_home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roartAnimation.stop();
                mediaPlayer.stop();
                Intent intent=new Intent(MainActivityApp.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        roartAnimation = (AnimationDrawable) roarImage.getBackground();
        roartAnimation.start();
        int resID=getResources().getIdentifier("sound", "raw", getPackageName());
        mediaPlayer= MediaPlayer.create(this,resID);
        mediaPlayer.start();
        roarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roartAnimation.stop();
                mediaPlayer.stop();
                roartAnimation.start();
                int resID=getResources().getIdentifier("sound", "raw", getPackageName());
                mediaPlayer= MediaPlayer.create(MainActivityApp.this,resID);
                mediaPlayer.start();
            }
        });
        final Speakerbox speakerbox = new Speakerbox(getApplication());





        // In



        tts=new TextToSpeech(MainActivityApp.this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                // TODO Auto-generated method stub
                if(status == TextToSpeech.SUCCESS){
                    int result=tts.setLanguage(Locale.US);
                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("error", "This Language is not supported");
                    }
                    else{
//                        ConvertTextToSpeech();
                    }
                }
                else
                    Log.e("error", "Initilization Failed!");
            }
        });


    }
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            return true;
        }
        return super.onTouchEvent(event);
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub

        if(tts != null){

            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }



    private void ConvertTextToSpeech() {
        // TODO Auto-generated method stub
        String text = "Hello";
        if(text==null||"".equals(text))
        {
            text = "Content not available";
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }else
            tts.speak("Hi... I am InfoBot your personal assistant. \n Developed by Thamaraiselvan.T(thams-geth)."+"is saved", TextToSpeech.QUEUE_FLUSH, null);
    }
    private void exit() {
        Intent intent = new Intent(MainActivityApp.this, Splash.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_signout) {
            mAuth.signOut();
           exit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
