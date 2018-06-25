package com.tts.infocruise18;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {
    ImageView direc, event, video, contact, assistant,account_image;
    TextView register, time,eventtv;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        direc = (ImageView) findViewById(R.id.iv_guide);
        event = (ImageView) findViewById(R.id.iv_events);
        video = (ImageView) findViewById(R.id.iv_video);
        contact = (ImageView) findViewById(R.id.iv_contact);
        assistant = (ImageView) findViewById(R.id.iv_assistant);
        account_image = (ImageView) findViewById(R.id.account_image);

        event.startAnimation(AnimationUtils.loadAnimation(this, R.anim.image_click));
        register = (TextView) findViewById(R.id.tv_register);
        eventtv = (TextView) findViewById(R.id.tv_event);
        time = (TextView) findViewById(R.id.time);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            Picasso.with(this).load(mAuth.getCurrentUser().getPhotoUrl()).into(account_image);

        }



        CountDownTimer newtimer = new CountDownTimer(1000000000, 1000) {

            public void onTick(long millisUntilFinished) {
                Calendar c = Calendar.getInstance();
                int a = c.get(Calendar.AM_PM);
                if(a == Calendar.AM){
                    time.setText(c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + " AM");
                }
                else {
                    time.setText(c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + " PM");
                }

            }

            public void onFinish() {

            }
        };
        newtimer.start();
        eventtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, Events.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        direc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, DirectionActivity.class);
                startActivity(intent);
            }
        });
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, Events.class);
                startActivity(intent);
            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, VideoActivity.class);
                startActivity(intent);
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ContactsActivity.class);
                startActivity(intent);
            }
        });
        assistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuth.getCurrentUser() == null) {
                    Toast.makeText(HomeActivity.this, "Please signin to continue to our InfoBot.", Toast.LENGTH_SHORT).show();
                    exit();
                } else {
                    Intent intent = new Intent(HomeActivity.this, InfoBotDialogFlow.class);
                    startActivity(intent);
                }
            }
        });
        assistant.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onPopupButtonClick(assistant);
                return true;
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        event.startAnimation(AnimationUtils.loadAnimation(this, R.anim.image_click));

    }

    @Override
    protected void onResume() {
        super.onResume();
        event.startAnimation(AnimationUtils.loadAnimation(this, R.anim.image_click));

    }

    private void exit() {

        Intent intent = new Intent(HomeActivity.this, Splash.class);
        startActivity(intent);
        finish();
    }
    public void onPopupButtonClick(View button) {
        PopupMenu popup = new PopupMenu(this, button);
        popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.infobotthams){
                    if (mAuth.getCurrentUser() == null) {
                        if(!isConnected(HomeActivity.this)) {
                            buildDialog(HomeActivity.this).show();
                        }
                        else {
                                exit();
                        }
                    } else {
                        Intent intent = new Intent(HomeActivity.this, InfoBotDialogFlow.class);
                        startActivity(intent);
                    }
                }
                if(item.getItemId()==R.id.infobotsathees){
//                    Intent intent = new Intent(HomeActivity.this, InfoBotSathees.class);
//                    startActivity(intent);
                }
                return true;
            }
        });

        popup.show();
    }    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_signout) {
            if(!isConnected(HomeActivity.this)) {
                buildDialog(HomeActivity.this).show();
            }
            else {
                if (mAuth.getCurrentUser() != null) {
                    DatabaseReference ref;
                    ref = FirebaseDatabase.getInstance().getReference(mAuth.getUid());
                    ref.removeValue();
                    mAuth.signOut();
                    exit();

                }
            }

            return true;
        }
        if (id == R.id.action_share) {
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Infocruise'18");
                String sAux = "\nInfocruise a national level technical sysposium.\n\n" +
                        "Download the app and register the event.";
                sAux = sAux + "https://play.google.com/store/apps/details?id=com.tts.infocruise18&hl=en \n\n";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }
        }
        if (id == R.id.action_rateus) {

            Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
            }
        }
        if (id == R.id.action_developer) {
                Intent intent = new Intent(HomeActivity.this, DeveloperActivity.class);
                startActivity(intent);
            }


            return super.onOptionsItemSelected(item);
        }
    }


