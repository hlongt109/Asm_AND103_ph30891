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
import com.ph30891.asm_ph30891_qlsv.databinding.ActivityLoginBinding;
import com.ph30891.asm_ph30891_qlsv.presenter.LoginPresenter;
import com.ph30891.asm_ph30891_qlsv.presenter.contract.LoginInterface;

public class LoginActivity extends AppCompatActivity implements LoginInterface {
    private ActivityLoginBinding binding;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = new LoginPresenter(this);

        init();
    }
    private void init(){
        binding.btnLogin.setOnClickListener(v -> {
            String email = String.valueOf(binding.edEmail.getText());
            String pass = String.valueOf(binding.edPass.getText());
            presenter.login(email, pass);
        });
        binding.tvSignUp.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();
    }

    @Override
    public void loginFailure() {
        Toast.makeText(this, "Email or password is invalid", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void emailError() {
        binding.tilEmail.setError("Email is invalid");
    }

    @Override
    public void passwordError() {
        binding.tilPass.setError("Enter your password");
    }

    @Override
    public void setLoading() {
        binding.btnLogin.setEnabled(false);
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoading() {
        binding.btnLogin.setEnabled(true);
        binding.progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void clearError() {
        binding.tilEmail.setError(null);
        binding.tilPass.setError(null);
    }


}