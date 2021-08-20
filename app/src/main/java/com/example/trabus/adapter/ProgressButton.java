package com.example.trabus.adapter;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.trabus.R;

public class ProgressButton {
    private CardView cardView;
    private ConstraintLayout constraintLayout;
    private ProgressBar progressBar;
    private TextView textView;
    public ProgressButton(Context ct, View view)
    {
      cardView=view.findViewById(R.id.cardview);
      constraintLayout=view.findViewById(R.id.constraintprogress);
      progressBar=view.findViewById(R.id.progressBar);
      textView=view.findViewById(R.id.tvprogress);
    }
    public void buttonactivated()

    {
        progressBar.setVisibility(View.VISIBLE);
        textView.setText("Please wait...");
    }
   public void buttonfinished()
    {
        progressBar.setVisibility(View.GONE);
        textView.setText("Failed");
    }


}
