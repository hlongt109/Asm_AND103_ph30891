package com.ph30891.asm_ph30891_qlsv.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ph30891.asm_ph30891_qlsv.R;
import com.ph30891.asm_ph30891_qlsv.databinding.ActivitySignUpBinding;
import com.ph30891.asm_ph30891_qlsv.presenter.SignUpPresenter;
import com.ph30891.asm_ph30891_qlsv.presenter.contract.SignUpInterface;

public class SignUpActivity extends AppCompatActivity implements SignUpInterface {
    private ActivitySignUpBinding binding;
    private SignUpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
;
         presenter = new SignUpPresenter(this);
         init();
    }
    private void init(){
        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });
        binding.btnSignUp.setOnClickListener(v -> {
            String email = String.valueOf(binding.edEmail.getText());
            String pass = String.valueOf(binding.edPass.getText());
            String name = String.valueOf(binding.edName.getText());
            presenter.signUp(name,email,pass);
        });
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void nameError() {
        binding.tilName.setError("Please enter your name");
    }

    @Override
    public void emailError() {
        binding.tilEmail.setError("Email is not valid");
    }

    @Override
    public void passwordError() {
        binding.tilPass.setError("The password must be at least 6 characters long");
    }

    @Override
    public void clearError() {
        binding.tilName.setError(null);
        binding.tilEmail.setError(null);
        binding.tilPass.setError(null);
    }

    @Override
    public void setLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btnSignUp.setEnabled(false);
    }

    @Override
    public void clearLoading() {
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.btnSignUp.setEnabled(true);
    }

    @Override
    public void signUpSuccess() {
        Toast.makeText(this, "SignUp success", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Override
    public void signUpFail() {
        Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void emailExists() {
        binding.tilName.setError("Email is exists");
    }
}