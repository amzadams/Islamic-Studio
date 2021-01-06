package e.manan.test1.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import e.manan.test1.Community.MainCommActivity;
import e.manan.test1.Community.Register;
import e.manan.test1.Community.ResetPasswordActivity;
import e.manan.test1.R;

public class Community extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final Boolean CHECK_IF_VERIFIED = false;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Context mContext;
    private ProgressBar progressbar;
    private TextView reg,resetPassword;
    private Button login;
    private EditText email_LL,password_LL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();
        progressbar = findViewById(R.id.progressbar);
        mAuth = FirebaseAuth.getInstance();
        login = findViewById(R.id.login);
        reg = findViewById(R.id.register);
        email_LL = findViewById(R.id.email);
        password_LL = findViewById(R.id.password);
        resetPassword = findViewById(R.id.resetPassword);

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Community.this, ResetPasswordActivity.class));
            }
        });


        mContext = Community.this;
        Log.d(TAG, "onCreate: started.");


        setupFirebaseAuth();
        init();

    }

    private boolean isStringNull(String string){
        Log.d(TAG, "isStringNull: checking string if null.");

        if(string.equals("")){
            return true;
        }
        else{
            return false;
        }
    }

     /*
    ------------------------------------ Firebase ---------------------------------------------
     */

    private void init(){

        //initialize the button for logging in
        Button btnLogin = (Button) findViewById(R.id.login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to log in.");

                String email = email_LL.getText().toString();
                String password = password_LL.getText().toString();
                String enterEmail = email_LL.getText().toString().trim();
                String enterPassword = password_LL.getText().toString().trim();
                if (enterEmail.isEmpty()){
                    email_LL.setError("Email is required");
                    email_LL.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(enterEmail).matches() ){
                    email_LL.setError("Enter a valid email");
                    email_LL.requestFocus();
                    return;
                }
                if (enterPassword.isEmpty()){
                    password_LL.setError("Password is required");
                    password_LL.requestFocus();
                    return;
                }
                if (enterPassword.length()<6){
                    password_LL.setError("Minimum length of password should be six");
                    password_LL.requestFocus();
                    return;
                }

                if(isStringNull(email) && isStringNull(password)){
                    Toast.makeText(mContext, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
                }else{
                            progressbar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Community.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        progressbar.setVisibility(View.GONE);
                                        Log.w(TAG, "signInWithEmail:failed", task.getException());

                                        Toast.makeText(Community.this, getString(R.string.auth_failed),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        try{
                                            if(CHECK_IF_VERIFIED){
                                                if(user.isEmailVerified()){
                                                    Log.d(TAG, "onComplete: success. email is verified.");
                                                    Intent intent = new Intent(Community.this, MainCommActivity.class);
                                                    startActivity(intent);
                                                }else{
                                                    Toast.makeText(mContext, "Email is not verified \n check your email inbox.", Toast.LENGTH_SHORT).show();
                                                    mAuth.signOut();
                                                }
                                            }
                                            else{
                                                Log.d(TAG, "onComplete: success. email is verified.");
                                                Intent intent = new Intent(Community.this, MainCommActivity.class);
                                                startActivity(intent);
                                            }

                                        }catch (NullPointerException e){
                                            Log.e(TAG, "onComplete: NullPointerException: " + e.getMessage() );
                                        }
                                    }

                                    // ...
                                }
                            });
                }

            }
        });

        TextView linkSignUp = (TextView) findViewById(R.id.register);
        linkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to register screen");
                Intent intent = new Intent(Community.this, Register.class);
                startActivity(intent);
            }
        });

         /*
         If the user is logged in then navigate to HomeActivity and call 'finish()'
          */
        if(mAuth.getCurrentUser() != null){
            Intent intent = new Intent(Community.this, MainCommActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            if (currentUser.isEmailVerified()) {
                startActivity(new Intent(Community.this, MainCommActivity.class));
            } else {
                Toast.makeText(Community.this, "Please log in", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(Community.this, "Please log in ", Toast.LENGTH_SHORT).show();
        }
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}