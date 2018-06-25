package com.tts.infocruise18;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ai.api.AIDataService;
import ai.api.AIListener;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;

import com.mapzen.speakerbox.Speakerbox;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class InfoBotDialogFlow extends AppCompatActivity implements AIListener {

    RecyclerView recyclerView;
    EditText editText;
    RelativeLayout addBtn;
    DatabaseReference ref;
    FirebaseRecyclerAdapter<ChatMessage,chat_rec> adapter;
    Boolean flagFab = true;
    private FirebaseAuth mAuth;
    private AIService aiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context=getApplicationContext();


        if(!isConnected(InfoBotDialogFlow.this)) {
            buildDialog(InfoBotDialogFlow.this).show();
        }
        else {
            setContentView(R.layout.infobot_dialogflow);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},1);


            recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
            editText = (EditText)findViewById(R.id.editText);
            addBtn = (RelativeLayout)findViewById(R.id.addBtn);

            mAuth = FirebaseAuth.getInstance();
            if(mAuth.getCurrentUser()==null){
                exit();
            }
            final Speakerbox speakerbox = new Speakerbox(getApplication());


            recyclerView.setHasFixedSize(true);
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(linearLayoutManager);

            ref = FirebaseDatabase.getInstance().getReference(mAuth.getUid());
            ref.keepSynced(true);
            speakerbox.play("Hi... I am InfoBot your personal assistant."+
                    "Developed by Thamaraiselvan");

            final AIConfiguration config = new AIConfiguration("44f94cab98044aad8d5d5db15b3b195b",
                    AIConfiguration.SupportedLanguages.English,
                    AIConfiguration.RecognitionEngine.System);

            aiService = AIService.getService(this, config);
            aiService.setListener(this);

            final AIDataService aiDataService = new AIDataService(config);

            final AIRequest aiRequest = new AIRequest();



            addBtn.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("StaticFieldLeak")
                @Override
                public void onClick(View view) {

                    String message = editText.getText().toString().trim();

                    if (!message.equals("")) {

                        ChatMessage chatMessage = new ChatMessage(message, "user");
                        ref.child("chat").push().setValue(chatMessage);

                        aiRequest.setQuery(message);
                        new AsyncTask<AIRequest,Void,AIResponse>(){

                            @Override
                            protected AIResponse doInBackground(AIRequest... aiRequests) {
                                final AIRequest request = aiRequests[0];
                                try {
                                    final AIResponse response = aiDataService.request(aiRequest);
                                    return response;
                                } catch (AIServiceException e) {
                                }
                                return null;
                            }
                            @Override
                            protected void onPostExecute(AIResponse response) {
                                if (response != null) {

                                    Result result = response.getResult();
                                    String reply = result.getFulfillment().getSpeech();
//                                reply = reply.replace('\n', '\n');
                                    ChatMessage chatMessage = new ChatMessage(reply, "bot");
                                    ref.child("chat").push().setValue(chatMessage);
                                    if (reply.equals("fuck you")){
                                        reply=reply+mAuth.getCurrentUser().getDisplayName();
                                    speakerbox.play(reply);
                                    }
                                    else {
                                        speakerbox.play(reply);
                                    }

//                                BufferedReader reader = null;
//                                StringBuilder sb = new StringBuilder();
//                                try {
//                                    reader = new BufferedReader(
//                                            new InputStreamReader(response.getResult());
//                                    String line;
//                                    try {
//                                        while ((line = reader.readLine()) != null) {
//                                            sb.append(line);
//                                        }
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                                String result = sb.toString();
                                }
                            }
                        }.execute(aiRequest);
                    }
                    else {
                        aiService.startListening();
                    }

                    editText.setText("");

                }
            });



            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    ImageView fab_img = (ImageView)findViewById(R.id.fab_img);
                    Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.ic_send_white_24dp);
                    Bitmap img1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_mic_white_24dp);


                    if (s.toString().trim().length()!=0 && flagFab){
                        ImageViewAnimatedChange(InfoBotDialogFlow.this,fab_img,img);
                        flagFab=false;

                    }
                    else if (s.toString().trim().length()==0){
                        ImageViewAnimatedChange(InfoBotDialogFlow.this,fab_img,img1);
                        flagFab=true;

                    }


                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            adapter = new FirebaseRecyclerAdapter<ChatMessage, chat_rec>(ChatMessage.class,
                    R.layout.msglist,chat_rec.class,ref.child("chat")) {
                @Override
                protected void populateViewHolder(chat_rec viewHolder, ChatMessage model, int position) {


                    if (model.getMsgUser().equals("user")) {


                        viewHolder.rightText.setText(model.getMsgText());

                        viewHolder.rightText.setVisibility(View.VISIBLE);
                        viewHolder.leftText.setVisibility(View.GONE);
                    }
                    else {
                        viewHolder.leftText.setText(model.getMsgText());

                        viewHolder.rightText.setVisibility(View.GONE);
                        viewHolder.leftText.setVisibility(View.VISIBLE);
                    }
                }
            };

            adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);

                    int msgCount = adapter.getItemCount();
                    int lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();

                    if (lastVisiblePosition == -1 ||
                            (positionStart >= (msgCount - 1) &&
                                    lastVisiblePosition == (positionStart - 1))) {
                        recyclerView.scrollToPosition(positionStart);

                    }

                }
            });

            recyclerView.setAdapter(adapter);
        }



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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear) {
            ref.removeValue();
            String home="Hi... I am InfoBot your personal assistant. \nDeveloped by Thamaraiselvan.";
            ChatMessage chatMessage = new ChatMessage(home, "bot");
            ref.child("chat").push().setValue(chatMessage);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void exit() {
        Intent intent = new Intent(InfoBotDialogFlow.this, Splash.class);
        startActivity(intent);
        finish();
    }
    public void ImageViewAnimatedChange(Context c, final ImageView v, final Bitmap new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, R.anim.zoom_out);
        final Animation anim_in  = AnimationUtils.loadAnimation(c, R.anim.zoom_in);
        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                v.setImageBitmap(new_image);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {}
                });
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }

    @Override
    public void onResult(ai.api.model.AIResponse response) {


        Result result = response.getResult();

        String message = result.getResolvedQuery();
        ChatMessage chatMessage0 = new ChatMessage(message, "user");
        ref.child("chat").push().setValue(chatMessage0);


        String reply = result.getFulfillment().getSpeech();
        ChatMessage chatMessage = new ChatMessage(reply, "bot");
        ref.child("chat").push().setValue(chatMessage);



    }

    @Override
    public void onError(ai.api.model.AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {
        editText.setText("Listening..");

    }

    @Override
    public void onListeningCanceled() {
        editText.setText("");

    }

    @Override
    public void onListeningFinished() {
        editText.setText("");

    }


}
