package e.manan.test1.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import e.manan.test1.R;

public class Donation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();
    }
}
