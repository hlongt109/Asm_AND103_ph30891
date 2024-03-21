package com.ph30891.asm_ph30891_qlsv.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ph30891.asm_ph30891_qlsv.R;
import com.ph30891.asm_ph30891_qlsv.databinding.ActivityAddUpdateBinding;
import com.ph30891.asm_ph30891_qlsv.presenter.UDPresenter;
import com.ph30891.asm_ph30891_qlsv.presenter.contract.UDInterface;

public class Add_Update_Activity extends AppCompatActivity implements UDInterface {
    private ActivityAddUpdateBinding binding;
    private UDPresenter udPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        udPresenter = new UDPresenter(this,binding);

        init();
    }
    private void init(){
        udPresenter.openPickImage();
        udPresenter.setDataOnViewUpdate();
        //
        binding.btnBack.setOnClickListener(v -> onBackPressed());
        binding.btnSave.setOnClickListener(v -> {
            String name = binding.edName.getText().toString();
            String msv = String.valueOf(binding.edStudentId.getText());
            String diemTb = String.valueOf(binding.edGPA.getText());
            udPresenter.addAndUpdateSt(name,msv,diemTb);
        });
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void nameError() {
        binding.tilName.setError("Please enter the name");
    }

    @Override
    public void msvError() {
        binding.tilStudentId.setError("Please enter the student id");
    }

    @Override
    public void diemTbError() {
        binding.tilGPA.setError("GPA is invalid");
    }

    @Override
    public void avatarError() {

    }

    @Override
    public void clearErr() {
        binding.tilName.setError(null);
        binding.tilStudentId.setError(null);
        binding.tilGPA.setError(null);
    }

    @Override
    public void showLoad() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoad() {
        binding.progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        udPresenter.handleImagePickerResult(requestCode,resultCode,data);
    }
}