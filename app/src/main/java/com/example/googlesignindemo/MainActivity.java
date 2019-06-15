package com.example.googlesignindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.profilePicIVID)
    CircleImageView profilePicIVID;
    @BindView(R.id.nameTVID)
    TextView nameTVID;
    @BindView(R.id.emailTVID)
    TextView emailTVID;
    @BindView(R.id.userDetailsLL)
    LinearLayout userDetailsLL;
    @BindView(R.id.signInRL)
    RelativeLayout signInRL;
    @BindView(R.id.signOutBtn)
    Button signOutBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {
            String providerName = user.getProviderId();
            for (UserInfo user1 : FirebaseAuth.getInstance().getCurrentUser().getProviderData()) {
                if (user1.getProviderId().equals("google.com")) {
                    nameTVID.setText(user1.getDisplayName());
                    emailTVID.setText(user1.getEmail());
                    if (user1.getPhotoUrl() != null) {
                        setImage(profilePicIVID, user1.getPhotoUrl().toString());
                    }

                }
            }

        }
    }



    private void setImage(CircleImageView profilePicIVID, String toString) {
        Glide.with(this).load(toString).error(R.drawable.default_profile).placeholder(R.drawable.default_profile).into(profilePicIVID);
    }

    @OnClick(R.id.signOutBtn)
    public void onViewClicked() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();

    }
}
