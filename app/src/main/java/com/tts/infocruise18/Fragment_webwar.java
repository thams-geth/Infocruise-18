package com.tts.infocruise18;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */

public class Fragment_webwar extends Fragment {



    public Fragment_webwar() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_webwar, container, false);
        Button b_view=(Button)view.findViewById(R.id.b_view);
        b_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),DetailsActivity.class);
                intent.putExtra("position",2);
                startActivity(intent);
            }
        });
        return view;
    }

}
