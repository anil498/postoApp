package com.play.edit_dp_and_post.editpost_dp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;


public class Size_fragment extends Fragment
{
    private SeekBar sizebar;

    Editp obj =new Editp();


    public Size_fragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View vv= inflater.inflate(R.layout.fragment_size_fragment, container, false);
               sizebar=vv.findViewById(R.id.sizebar);
        float fs =  30;
        sizebar.setProgress((int)fs);
        obj.Prtname.setTextSize(TypedValue.COMPLEX_UNIT_DIP,sizebar.getProgress());
        sizebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                obj.Prtname.setTextSize(TypedValue.COMPLEX_UNIT_DIP,progress+50);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


      return vv;
    }


}
