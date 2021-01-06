package e.manan.test1.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import e.manan.test1.R;

public class SocialMedia extends AppCompatActivity implements View.OnClickListener {
    private CardView yT,fb,twitter,iG,whatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();
        yT = findViewById(R.id.youtubeId);
        twitter = findViewById(R.id.twitterId);
        fb = findViewById(R.id.facebookId);
        iG = findViewById(R.id.instagramId);
        whatsapp = findViewById(R.id.whatsappId);
        yT.setOnClickListener(this);
        twitter.setOnClickListener(this);
        fb.setOnClickListener(this);
        iG.setOnClickListener(this);
        whatsapp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent o;
        switch (view.getId()){
            case R.id.youtubeId :o = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCPgTvrI3HvWZFVeNywkn80w"));startActivity(o);  break;
            case R.id.twitterId:  o = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/islamicstudio90"));startActivity(o); break;
            case R.id.instagramId : o = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/islamic_studio/")); startActivity(o);break;
            case R.id.facebookId : o = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/islamicstudi0/"));startActivity(o); break;
            case R.id.whatsappId : o = new Intent(Intent.ACTION_VIEW, Uri.parse("https://chat.whatsapp.com/invite/BIJE7bHIajDnQ3gt9VmJE"));startActivity(o); break;
            default: break;
        }
    }
}
