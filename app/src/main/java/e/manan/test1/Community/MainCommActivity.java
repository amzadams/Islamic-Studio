package e.manan.test1.Community;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import e.manan.test1.Activities.Community;
import e.manan.test1.R;

public class MainCommActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_comm);
        mAuth = FirebaseAuth.getInstance();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comm, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id== R.id.action_signOut){
            startActivity(new Intent(MainCommActivity.this,Community.class));
           mAuth.signOut();
            return true;
        }
        if (id==R.id.action_del){
            deleteAccount();
        }
        return super.onOptionsItemSelected(item);
    }
    private void deleteAccount() {
        Log.d("MainCommunity", "ingreso a deleteAccount");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("MainCommunity","OK! Works fine!");
                    startActivity(new Intent(MainCommActivity.this, Community.class));
                    finish();
                } else {
                    Log.w("MainCommunity","Something is wrong!");
                }
            }
        });
    }

}
