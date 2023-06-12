package com.example.lab5_iot;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.lab5_iot.DTOs.User;
import com.example.lab5_iot.databinding.ActivityMainBinding;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        AuthMethodPickerLayout customLayout = new AuthMethodPickerLayout.Builder(R.layout.fragment_login)
                .setEmailButtonId(R.id.ingresar)
                .setGoogleButtonId(R.id.loginGoogle)
                .setFacebookButtonId(R.id.loginFacebook)
                .build();
        Intent intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAuthMethodPickerLayout(customLayout)
                .setAvailableProviders(Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build(),
                        new AuthUI.IdpConfig.FacebookBuilder().build()
                ))
                .setIsSmartLockEnabled(false).build();
        signInLauncher.launch(intent);
    }
    ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            result -> {
                if (result.getResultCode() == RESULT_OK){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    User usuarioLogueado = new User();
                    usuarioLogueado.setCorreo(user.getEmail());
                    UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
                    userViewModel.getUsuarioLogueado().setValue(usuarioLogueado);
                    Log.d("exito","exito");
                }else{
                    Log.e("mgs-tst","Canceló el flujo");
                }
            }
    );
}