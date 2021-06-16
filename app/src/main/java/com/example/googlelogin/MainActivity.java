package com.example.googlelogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

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

        if (googleSignInManager.isUserAlreadySignIn()) {
            //update the ui accordingly.
        }

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
                GoogleSignInAccount account = googleSignInManager.getProfileInfo();
                if (account != null) {
                    String personName = account.getDisplayName();
                    String personGivenName = account.getGivenName();
                    String personFamilyName = account.getFamilyName();
                    String personEmail = account.getEmail();
                    String personId = account.getId();
                    Uri personPhoto = account.getPhotoUrl();

                    String profileInfo = "Name: " + personName + "\n" +
                            "Given Name: " + personGivenName + "\n" +
                            "Family Name: " + personFamilyName + "\n" +
                            "Email: " + personEmail + "\n";

                    txtProfileInfo.setText(profileInfo);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == googleSignInManager.GOOGLE_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            //updateUI(account);

            Toast.makeText(this, "SignIn Successful.", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "SignIn Successful.");
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
            Toast.makeText(this, "SignIn Failed", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "SignIn Failed.");
        }
    }
}