package css.cis3334.firebaseauthentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    private TextView textViewStatus;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonGoogleLogin;
    private Button buttonCreateLogin;
    private Button buttonSignOut;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    // Configure Google Sign In
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //create buttons to be used on interface. Set them for signing in, creating account, and signing out
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonGoogleLogin = (Button) findViewById(R.id.buttonGoogleLogin);
        buttonCreateLogin = (Button) findViewById(R.id.buttonCreateLogin);
        buttonSignOut = (Button) findViewById(R.id.buttonSignOut);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //on button click - grab text from email and password editTexts, start listener for sign in
                //took out log d
                signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        buttonCreateLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //on button click - grab text from email and password editTexts to create new account
                //took out log d
                createAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        buttonGoogleLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //on button click - initiate Google sign in and listener
                //took out log d
                googleSignIn();
            }
        });

        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //on button click, sign out
                //took out log d
                signOut();
            }
        });


        mAuth = FirebaseAuth.getInstance(); //initialize FirebaseAuth


        mAuthListener = new FirebaseAuth.AuthStateListener() { //initialized mAuthListener
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //track the user when they sign in or out using the firebaseAuth
                //
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //took out log d
                } else {
                    // User is signed out
                    //took out log d
                }
                // ...
            }
        };

     FirebaseAuth mAuth; // declare object
        //get shared instance of Firebase Auth for Firebase
        mAuth = FirebaseAuth.getInstance(); //declare object for Firebase
    }

    @Override
    public void onStart() {
        //initiate the authentication listener
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener); // update the listener on the users place

        //added for google signin
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    @Override
    public void onStop() {
        //discontinue the authentication
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener); // remove the listener
        }
    }

    private void createAccount(String email, String password) {
        //create account for new users
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {  //update listener
                        //took out log d
                        //replace toast with textViewStatus.setText();
                        textViewStatus.setText("Account Created!");

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) { //when failed
                            //********change the EmailPasswordActivity.this to our activity, replace with text if it's an r.file (R.string.auth_failed -> "Authen failed")
                            //replace toast with textViewStatus.setText();
                            textViewStatus.setText("Authenfication failed");
                        }

                        // ...
                    }
                });
    }

    private void signIn(String email, String password){
        //sign in the recurrent user with email and password previously created.
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() { //add to listener
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //took out log d
                        textViewStatus.setText("Signed In");
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) { //when failed
                            //took out log w
                            //replace toast with textViewStatus.setText();
                            textViewStatus.setText("Authentication Failed");
                        }


                    }
                });
    }


    private void signOut () {
        //sing the user out of google and firebase
        mAuth.signOut();
        FirebaseAuth.getInstance().signOut();
    }

    private void googleSignIn() {
        //uses firebase along with google sign in and authenticates users
        private void firebaseAuthWithGoogle(GoogleSignInAccount ) {
            //took out log d

            //AuthCredential credential = GoogleAuthProvider.getCredential(task.getIdToken(), null);
            //mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //took out log
                                textViewStatus.setText("Signed in to Google");
                                FirebaseUser user = mAuth.getCurrentUser();

                            } else {
                                // If sign in fails, display a message to the user.
                               //took out log w
                                //replace toast with textViewStatus.setText();
                                textViewStatus.setText("Authentication Failed");

                            }

                            // ...
                        }
                    });
        }
    }




}
