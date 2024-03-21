package com.ph30891.asm_ph30891_qlsv.presenter;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ph30891.asm_ph30891_qlsv.presenter.contract.LoginInterface;

public class LoginPresenter {
    private LoginInterface loginInterface;

    public LoginPresenter(LoginInterface loginInterface) {
        this.loginInterface = loginInterface;
    }
    public void login(String email, String password) {
        if(validate(email, password)){
            handleLogin(email, password);
        }
    }
    private void handleLogin(String email, String password){
        loading(true);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
           if(task.isSuccessful()){
               String id = auth.getCurrentUser().getUid();
               DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(id);
               reference.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       if(snapshot.exists()){
                           FirebaseUser user = auth.getCurrentUser();
                           loginInterface.loginSuccess();
                           loading(false);
                       }else {
                           loginInterface.loginSuccess();
                           loading(false);
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {
                       loginInterface.loginSuccess();
                       loading(false);
                   }
               });

           }
        }).addOnFailureListener(e -> {
            loginInterface.loginSuccess();
            loading(false);
            Log.e("Error login", "Error: ",e );
        });
    }
    private boolean validate(String email, String password) {
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            loginInterface.emailError();
            return false;
        }else {
            loginInterface.clearError();
        }

        if (TextUtils.isEmpty(password)){
            loginInterface.passwordError();
            return false;
        }else {
            loginInterface.clearError();
        }
        return true;
    }
    private void loading(boolean isLoading) {
        if(isLoading){
            loginInterface.setLoading();
        }else {
            loginInterface.stopLoading();
        }
    }

}
