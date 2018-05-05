package com.dxball.siam.dxball;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.RelativeLayout;

public class DxBallActivity extends AppCompatActivity {
    private final int DX_BALL_DISPLAY_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dx_ball);






        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(DxBallActivity.this, MainGameActivity.class);
                DxBallActivity.this.startActivity(intent);
                DxBallActivity.this.finish();
            }
        }, DX_BALL_DISPLAY_TIME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
