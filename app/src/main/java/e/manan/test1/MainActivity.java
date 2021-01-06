package e.manan.test1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import e.manan.test1.Activities.Community;
import e.manan.test1.Activities.Donation;
import e.manan.test1.Activities.Iman;
import e.manan.test1.Activities.PostsListActivity;
import e.manan.test1.Activities.SocialMedia;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private CardView remi,imaan,socialM,comm,donation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        remi = findViewById(R.id.remindersId);
        imaan = findViewById(R.id.imanId);
        socialM = findViewById(R.id.socialmediaId);
        comm = findViewById(R.id.commId);
        donation = findViewById(R.id.donationId);
        remi.setOnClickListener(this);
        imaan.setOnClickListener(this);
        socialM.setOnClickListener(this);
        comm.setOnClickListener(this);
        donation.setOnClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id== R.id.action_about){
            return true;
        }
        if (id== R.id.action_notifications){
            return true;
        }
        if (id== R.id.action_share){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.remindersId:  i = new Intent(this, PostsListActivity.class);startActivity(i);  break;
            case R.id.imanId:  i = new Intent(this, Iman.class);startActivity(i); break;
            case R.id.socialmediaId : i = new Intent(this, SocialMedia.class); startActivity(i);break;
            case R.id.commId : i = new Intent(this, Community.class);startActivity(i); break;
            case R.id.donationId : i = new Intent(this, Donation.class);startActivity(i); break;
            default: break;
        }

    }
}

