package com.example.android.justjava;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Jonathan on 10/19/2015.
 */
public class Splash extends Activity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashh);
        final ImageView iv = (ImageView) findViewById(R.id.imageView);
        final Animation ani = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        final Animation ani2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_fade_out);
        iv.startAnimation(ani);
        ani.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv.startAnimation(ani2);
                finish();
                Intent inj = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(inj);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
