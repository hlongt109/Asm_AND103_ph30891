package com.ph30891.asm_ph30891_qlsv.presenter;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ph30891.asm_ph30891_qlsv.model.Users;
import com.ph30891.asm_ph30891_qlsv.presenter.contract.SignUpInterface;

public class SignUpPresenter {
    private SignUpInterface signUpInterface;
    private FirebaseAuth auth;

    public SignUpPresenter(SignUpInterface signUpInterface) {
        this.signUpInterface = signUpInterface;
        this.auth = FirebaseAuth.getInstance();
    }

    public void signUp(String name, String email, String password) {
        if (validate(name, email, password)) {
             checkEmailExists(name, email, password);
        }
    }
    private void checkEmailExists(String name,String email, String password){
        loading(true);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        Query query = reference.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    signUpInterface.emailExists();
                    loading(false);
                }else {
                    handleSignUp(name, email, password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                signUpInterface.signUpFail();
                loading(false);
            }
        });
    }
    private void handleSignUp(String name, String email, String password){
       loading(true);
       DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
       auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
           if(task.isSuccessful()){
               String id = auth.getCurrentUser().getUid();
               Users users = new Users(id,email,password,name,"");
               reference.child(id).setValue(users).addOnCompleteListener(task1 -> {
                   signUpInterface.signUpSuccess();
                   loading(false);
               }).addOnFailureListener(e -> {
                  signUpInterface.signUpFail();
                  loading(false);
               });
           }
       });
    }
    private boolean validate(String name, String email, String password){
        if(TextUtils.isEmpty(name)){
            signUpInterface.nameError();
            return false;
        }else {
            signUpInterface.clearError();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signUpInterface.emailError();
            return false;
        }else {
            signUpInterface.clearError();
        }
        if(TextUtils.isEmpty(password) && password.length() <6){
             signUpInterface.passwordError();
            return false;
        }else {
            signUpInterface.clearError();
        }
        return true;
    }
    private void loading(boolean isLoading){
        if(isLoading){
            signUpInterface.setLoading();
        }else {
            signUpInterface.clearLoading();
        }
    }
}
