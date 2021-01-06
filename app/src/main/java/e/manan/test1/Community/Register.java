package e.manan.test1.Community;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import e.manan.test1.Activities.Community;
import e.manan.test1.ModelData.User;
import e.manan.test1.R;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button regi;
    private EditText emailEntered, pass,number,name;
    private ProgressBar progressBar;
    private TextView oldAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.name);
        regi = findViewById(R.id.regRA);
        number = findViewById(R.id.numberRA);
        emailEntered = findViewById(R.id.emailRA);
        pass = findViewById(R.id.passwordRA);
        progressBar = findViewById(R.id.progressbar);
        oldAcc =findViewById(R.id.old_Acc);

        oldAcc = findViewById(R.id.old_Acc);
        oldAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,Community.class));
            }
        });

        regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
//                user.setEmail(emailEntered.getText().toString());
//                user.setPhone(number.getText().toString());
//                user.setPassword(pass.getText().toString());
//                user.setName(name.getText().toString());
                createUser(emailEntered.getText().toString(), pass.getText().toString(), user);

            }
        });

    }

    private void createUser(String email, String password, final User user){
        String enterEmail = emailEntered.getText().toString().trim();
        String enterPassword = pass.getText().toString().trim();
        if (enterEmail.isEmpty()){
            emailEntered.setError("Email is required");
            emailEntered.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(enterEmail).matches() ){
            emailEntered.setError("Enter a valid email");
            emailEntered.requestFocus();
            return;
        }
        if (enterPassword.isEmpty()){
            pass.setError("Password is required");
            pass.requestFocus();
            return;
        }
        if (enterPassword.length()<6){
            pass.setError("Minimum length of password should be six");
            pass.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            sendVerificationEmail();
                            saveUserData(user);
                        } else {
                            Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
    /**
     * sends an email verification link to the user
     */
    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(Register.this, "Sent Verification Email", Toast.LENGTH_SHORT).show();
                            }
                            else{

                                Toast.makeText(Register.this, "Couldn't Verification Send Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(Register.this, "Null", Toast.LENGTH_SHORT).show();
        }

    }

    public void saveUserData(User user){
        FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mAuth.signOut();
                            finish();
                        }else{
                            Toast.makeText(Register.this, "not saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
