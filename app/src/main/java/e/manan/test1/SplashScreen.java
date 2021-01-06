package e.manan.test1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.app.ActionBar;
public class SplashScreen extends AppCompatActivity {
    private TextView tv;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        tv = findViewById(R.id.txtV);
        iv = findViewById(R.id.logo);
       ActionBar actionBar =getSupportActionBar();
        actionBar.hide();
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        tv.startAnimation(myanim);
        iv.startAnimation(myanim);
         final Intent i = new Intent(SplashScreen.this,MainActivity.class);
        Thread timer = new Thread(){
            public void run (){
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
                }
            };
                timer.start();
    }

    }

