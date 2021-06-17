package com.example.googlelogin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private SignInButton signInButton;
    private Button btnSignOut, btnGetProfileInfo;
    private TextView txtProfileInfo;
    private GoogleSignInManager googleSignInManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signInButton = findViewById(R.id.btnGoogleSignIn);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnGetProfileInfo = findViewById(R.id.btnGetProfileInfo);
        txtProfileInfo = findViewById(R.id.txtProfileInfo);

        googleSignInManager = GoogleSignInManager.getInstance(this);
        googleSignInManager.setupGoogleSignInOptions();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                googleSignInManager.signIn();
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                googleSignInManager.signOut();
                txtProfileInfo.setText("");
            }
        });

        btnGetProfileInfo.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                FirebaseUser account =
                        googleSignInManager.getProfileInfo();
                if (account != null) {
                    String personName = account.getDisplayName();
                    String personEmail = account.getEmail();
                    Uri personPhoto = account.getPhotoUrl();

                    String profileInfo = "Name: " + personName + "\n" +
                            "Email: " + personEmail + "\n" +
                            "Photo: " + personPhoto;

                    txtProfileInfo.setText(profileInfo);
                }
            }
        });
    }

    @Override protected void onStart() {
        super.onStart();
        if (googleSignInManager.isUserAlreadySignIn()) {
            Toast.makeText(this, "Already Signed in.", Toast.LENGTH_SHORT).show();
        } else {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == googleSignInManager.GOOGLE_SIGN_IN) {
            googleSignInManager.handleSignInResult(data);
        }
    }

}