package com.example.googlelogin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class GoogleSignInManager {

    private static GoogleSignInManager instance = null;
    private Context context;
    private Activity activity;
    private GoogleSignInClient mGoogleSignInClient;
    public final int GOOGLE_SIGN_IN = 100;

    private GoogleSignInManager() {
    }

    public static GoogleSignInManager getInstance(Context context) {
        if (instance == null) {
            instance = new GoogleSignInManager();
        }
        instance.init(context);
        return instance;
    }

    private void init(Context context) {
        this.context = context;
        this.activity = (Activity) context;
    }

    public void setupGoogleSignInOptions() {
        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    public boolean isUserAlreadySignIn() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        if (account != null) {
            return true;
        } else {
            return false;
        }
    }

    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    public void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        task.addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override public void onSuccess(Void unused) {
                                Toast.makeText(context, "Signed Out.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                        task.addOnFailureListener(new OnFailureListener() {
                            @Override public void onFailure(@NonNull @org.jetbrains.annotations.NotNull Exception e) {
                                Toast.makeText(context, "Failed to Signed Out.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }

    public GoogleSignInAccount getProfileInfo() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(context);
        if (acct != null) {
            //
        } else {
            Toast.makeText(context, "No account info found.", Toast.LENGTH_SHORT).show();
        }
        return acct;
    }
}
