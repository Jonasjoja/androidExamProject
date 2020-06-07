package com.example.destinationinspire_androidexam;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;



public class MainActivity extends AppCompatActivity {
    //creating variables
    private Button getMeGoing;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private ProfilePictureView profilePictureView; //A facebook widget for displaying profile picture.
    private AccessTokenTracker tokenTracker; //going to use this to listen for logout.
    private TextView welcomeTxtview;
    private TextView plsLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        plsLogin = findViewById(R.id.pleaseLogin);
        callbackManager = new CallbackManager.Factory().create();   //Assigning / creating callback manager
        loginButton = findViewById(R.id.login_button);  //Assigning loginbutton
        profilePictureView = findViewById(R.id.profilePic); //Assigning profilepicture and initial visibility to invisible
        getMeGoing = findViewById(R.id.getMeGoingBtn); //Assigning get me going button
        welcomeTxtview = findViewById(R.id.welcomeTxtview); //Assigning welcome textview

        notLoggedIn();

        //Registering a callback
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //get and set profilepicture on succesful login
                profilePictureView.setProfileId(loginResult.getAccessToken().getUserId());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "You cancelled your login!",Toast.LENGTH_LONG).show(); //Displays a toast message on cancel
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Error on FB login", error.toString());
            }
        });

        //Continues to listen for a change in access token, hereby knowing if log out occurs.
        tokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) { //Listens for a token that equals null, which  means logged out
                    Toast.makeText(getApplicationContext(), "You have logged out",Toast.LENGTH_LONG).show(); //Displays a toast message on logout.
                    //What to do after log out
                    notLoggedIn();
                } else isLoggedIn();
            }
        };
        tokenTracker.startTracking();

        if (AccessToken.isCurrentAccessTokenActive()){ //if the app is restarted check if the user is still logged in. It wont log out if you kill the app or pause it ect.
            isLoggedIn();
        }
    }

    private void notLoggedIn() { //This changes layout to how it should be when not logged in.
        plsLogin.setVisibility(View.VISIBLE);
        profilePictureView.setVisibility(View.INVISIBLE);
        getMeGoing.setVisibility(View.INVISIBLE);
        welcomeTxtview.setVisibility(View.INVISIBLE);
    }
    private void isLoggedIn() { //This changes layout to how it should be when logged in.
        plsLogin.setVisibility(View.INVISIBLE);
        getMeGoing.setVisibility(View.VISIBLE);
        profilePictureView.setVisibility(View.VISIBLE);
        welcomeTxtview.setVisibility(View.VISIBLE);
        profilePictureView.setProfileId(AccessToken.getCurrentAccessToken().getUserId()); //this gets the profile picture from facebook based on your userId
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) //-1 is successful login
        {
            isLoggedIn();
        }
    }

    public void getMeGoing(View view){ //On click method for button, to change activity
        Intent intent = new Intent(this,ChoiceActivity.class);
        startActivity(intent);
    }



}
