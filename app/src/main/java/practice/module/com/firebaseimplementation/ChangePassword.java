package practice.module.com.firebaseimplementation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {

    private Button updatePasswordBtn;
    private FirebaseAuth auth;
    private EditText newPassET,confirmPassET;
    private ProgressBar resetProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        updatePasswordBtn = findViewById(R.id.update_password);
        newPassET = findViewById(R.id.new_password);
        confirmPassET = findViewById(R.id.confirm_password);
        resetProgressBar = findViewById(R.id.progressBarReset);

        updatePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //password and confirm password matching condition fails
//                if (newPassET.getText().toString() == confirmPassET.getText().toString()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    user.updatePassword(newPassET.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(ChangePassword.this, "Password is updated.", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        finish();

                                    }else{
                                        Toast.makeText(ChangePassword.this, "Failed to update Password.", Toast.LENGTH_SHORT).show();
                                        resetProgressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
//                }else{
//                    Toast.makeText(getApplicationContext(), "Passwords not matched!", Toast.LENGTH_SHORT).show();
//                }


            }
        });

    }
}
