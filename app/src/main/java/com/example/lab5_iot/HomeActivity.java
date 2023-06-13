package com.example.lab5_iot;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.lab5_iot.DTOs.User;
import com.example.lab5_iot.Fragments.LoginFragment;
import com.example.lab5_iot.databinding.ActivityHomeBinding;
import com.example.lab5_iot.databinding.ActivityMainBinding;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    LoginFragment loginFragment;
    GoogleApiClient googleApiClient;
    private static final int RC_SIGN_INT = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.registrate.setOnClickListener(view ->{
            Log.d("msg", "registrooooooo");
        });
        binding.ingresar.setOnClickListener(view -> {
           /* String correo = binding.editTextCorreo.getText().toString();
            String contrasenia = binding.editTextContrasenia.getText().toString();

            */
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("infoApp", "logout exitoso");
                        }
                    });
            UserViewModel viewModel = new ViewModelProvider(this).get(UserViewModel.class);
            viewModel.getUsuarioLogueado().postValue(null);
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });
        FirebaseAuth auth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, null)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        binding.loginGoogle.setOnClickListener(view -> {
            Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(intent, RC_SIGN_INT);
        });
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_INT){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result){
        if (result.isSuccess()){
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.d("acct", acct.getDisplayName());
        }
    }

}