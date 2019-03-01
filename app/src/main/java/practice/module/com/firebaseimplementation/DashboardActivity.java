package practice.module.com.firebaseimplementation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {

    private Button signOutBtn,deleteAccount,changePswdBtn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        signOutBtn = findViewById(R.id.sign_out_btn);
        deleteAccount = findViewById(R.id.delete_account_btn);
        changePswdBtn = findViewById(R.id.change_password_btn);


//        auth = FirebaseAuth.getInstance();
//        auth.signOut();
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                auth.signOut();

                //this listener will be called when there is change in firebase user session
//                FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
//                    @Override
//                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                        FirebaseUser user = firebaseAuth.getCurrentUser();
//                        if(user == null){
//                            //user auh state is changed - user is null
//                            //launch login activity
//                            startActivity(new Intent(DashboardActivity.this,MainActivity.class));
//                            finish();
//                        }
//                    }
//                };
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(DashboardActivity.this, "Your Account is deleted", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(DashboardActivity.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        changePswdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this,ChangePassword.class));
                finish();
            }
        });
    }
}
