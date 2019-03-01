package practice.module.com.firebaseimplementation;

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
import com.google.firebase.auth.FirebaseAuth;

public class ResetActivity extends AppCompatActivity {

    private Button btnResetPassword;
    private EditText email_reset_et;
    private TextView back_tv;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        btnResetPassword = findViewById(R.id.reset_password_button);
        email_reset_et = findViewById(R.id.reset_email);
        back_tv = findViewById(R.id.back_to_login);
        progressBar = findViewById(R.id.progressBarReset);

        auth = FirebaseAuth.getInstance();

        back_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resetEmail = email_reset_et.getText().toString().trim();

                if(TextUtils.isEmpty(resetEmail)){
                    Toast.makeText(getApplicationContext(), "Enter your registered email", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.sendPasswordResetEmail(resetEmail)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ResetActivity.this, "We have sent link to reset password to your email", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(ResetActivity.this, "Failed to sent reset link", Toast.LENGTH_SHORT).show();
                                }
                                    progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }
}
