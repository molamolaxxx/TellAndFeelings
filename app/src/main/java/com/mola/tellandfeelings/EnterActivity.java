package com.mola.tellandfeelings;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mola.openingstartanimation.OpeningStartAnimation;
import com.mola.openingstartanimation.RotationDrawStrategy;

import java.util.Timer;
import java.util.TimerTask;

public class EnterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        startOpenningAnimation();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                overridePendingTransition(R.anim.slide_bottom_in,0);
                Intent intent=new Intent(EnterActivity.this,TellActivity.class);
                startActivity(intent);
                this.cancel();
                finish();
            }
        },1920);
    }
    private void startOpenningAnimation(){
        Resources resources=EnterActivity.this.getResources();
        Drawable drawable=resources.getDrawable(R.mipmap.tell_icon);
        //drawable= MyDrawableUtils.zoomDrawable(drawable,120,120);
        OpeningStartAnimation op=new OpeningStartAnimation.Builder(this)
                .setDrawStategy(new RotationDrawStrategy())
                .setAppStatement("每秒,都是诗句")
                .setAppIcon(drawable)
                .setAnimationInterval(2000)
                .setAnimationFinishTime(10)
                .create();
        op.show(EnterActivity.this);
    }
}
