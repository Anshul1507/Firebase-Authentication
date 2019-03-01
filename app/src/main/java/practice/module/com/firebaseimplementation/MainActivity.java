package practice.module.com.firebaseimplementation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class MainActivity extends AppCompatActivity {

    private EditText login_email,login_password;
    private Button buttonLogin;
    private TextView signUpText;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
//        auth = FirebaseAuth.getInstance();
//        if(auth.getCurrentUser() != null){
//            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
//            finish();
//            Toast.makeText(this, "Signed up", Toast.LENGTH_SHORT).show();
//        }
        //set the view now
        setContentView(R.layout.activity_main);

        login_email = findViewById(R.id.input_email);
        login_password = findViewById(R.id.input_password);
        buttonLogin = findViewById(R.id.login_button);
        signUpText = findViewById(R.id.create_account);
        progressBar = findViewById(R.id.login_progress);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignupActivity.class));
            }
        });


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginEmail = login_email.getText().toString();
                final String loginPassword = login_password.getText().toString();
                if(TextUtils.isEmpty(loginEmail)){
                    Toast.makeText(MainActivity.this, "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(loginPassword)){
                    Toast.makeText(MainActivity.this, "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.signInWithEmailAndPassword(loginEmail,loginPassword)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if(!task.isSuccessful()){
                                    //there was an error
                                    if(loginPassword.length() < 6){
                                        login_password.setError("Password too short, enter minimum 6 characters!");
                                    }else{
                                        Toast.makeText(MainActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
                                    startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                                    login_password.setText("");
                                    login_email.setText("");
                                    //pressing signout button is not proper functioning and we have to activate finish(); here for
                                    // finish the login screen on success login and if we apply finish(); here then pressing on signout, app crashes
                                    

                                    Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }
}
