package com.ph30891.asm_ph30891_qlsv.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ph30891.asm_ph30891_qlsv.R;
import com.ph30891.asm_ph30891_qlsv.databinding.ActivityAddUpdateBinding;
import com.ph30891.asm_ph30891_qlsv.model.Students;
import com.ph30891.asm_ph30891_qlsv.networking.ApiServices;
import com.ph30891.asm_ph30891_qlsv.presenter.contract.UDInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UDPresenter {
    private UDInterface udInterface;
    private ActivityAddUpdateBinding binding;
    private Uri imgUri = null;
    private boolean isAdd = true;
    private String imageUpdate = "";
    private String idUpdate = "";
    public interface GetImageUrlCallback {
        void onImageUrlReceived(String imageUrl);
    }

    public UDPresenter(UDInterface udInterface, ActivityAddUpdateBinding binding) {
        this.udInterface = udInterface;
        this.binding = binding;
    }
    public void openPickImage(){
        binding.imgSt.setOnClickListener(v -> {
            ImagePicker.with(udInterface.getActivity())
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080,1080)
                    .start();
        });
    }
    public void setDataOnViewUpdate(){
        Students students = (Students) udInterface.getActivity().getIntent().getSerializableExtra("st");
        Log.d("st", "st:" +students);
        if(students != null){
            isAdd = false;
            binding.edName.setText(students.getName());
            binding.edStudentId.setText(students.getMsv());
            binding.edGPA.setText(String.valueOf(students.getDiemTb()));
            Glide.with(udInterface.getContext()).load(students.getAvatar()).error(R.drawable.image).into(binding.imgSt);
            imageUpdate = students.getAvatar();
            idUpdate = students.get_id();
        }else {
            isAdd = true;
        }
    }
    public void addAndUpdateSt(String name, String msv, String diemTb){
        if(validate(name,msv,diemTb)){
            if(isAdd){
                AddSt(name,msv,diemTb,imgUri);
            }else {
                // update
                UpdateSt(idUpdate,name,msv,diemTb,imgUri);
            }
        }
    }

    private void AddSt(String name, String msv, String diemTb, Uri avt){
        loading(true);
        double diem = Double.valueOf(diemTb);
        if(avt != null){
            getUrlImage(avt, msv,imageUrl -> {
                if(imageUrl != null){
                    Students students = new Students(name,msv,diem,imageUrl);
                    handleAddStudent(students);
                }else {
                    loading(false);
                    Toast.makeText(udInterface.getContext(), "get url image failed", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Students students = new Students(name,msv,diem,"");
            handleAddStudent(students);
        }

    }
    private void handleAddStudent(Students students){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);
        Call<Students> call = apiServices.addStudent(students);
        call.enqueue(new Callback<Students>() {
            @Override
            public void onResponse(Call<Students> call, Response<Students> response) {
                if(response.isSuccessful()){
                    loading(false);
                    resetFields();
                    Toast.makeText(udInterface.getContext(), "Add success", Toast.LENGTH_SHORT).show();
                }else {
                    loading(false);
                    Toast.makeText(udInterface.getContext(), "Add failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Students> call, Throwable t) {
                loading(false);
                Toast.makeText(udInterface.getContext(), "Add Error :"+t, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void UpdateSt(String id,String name, String msv, String diemTb, Uri avt){
        loading(true);
        double diem = Double.valueOf(diemTb);
        if(avt != null){
            getUrlImage(avt, msv,imageUrl -> {
                Students students = new Students();
                students.setAvatar(imageUrl);
                students.setName(name);
                students.setMsv(msv);
                students.setDiemTb(diem);
                handleUpdate(id,students);
            });

        }else {
            Students students = new Students();
            students.setAvatar(imageUpdate);
            students.setName(name);
            students.setMsv(msv);
            students.setDiemTb(diem);
            handleUpdate(id,students);
        }
    }
    private void handleUpdate(String id,Students students){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);
        Call<Students> call = apiServices.updateStudent(id,students);
        call.enqueue(new Callback<Students>() {
            @Override
            public void onResponse(Call<Students> call, Response<Students> response) {
                if(response.isSuccessful()){
                    loading(false);
                    resetFields();
                    Toast.makeText(udInterface.getContext(), "Update success", Toast.LENGTH_SHORT).show();
                }else {
                    loading(false);
                    Toast.makeText(udInterface.getContext(), "Update fail", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Students> call, Throwable t) {
                loading(false);
                Toast.makeText(udInterface.getContext(), "Update Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getUrlImage(Uri imgUri,String msv, GetImageUrlCallback callback) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("StudentPhoto").child(msv+".jpg");
        UploadTask uploadTask = storageReference.putFile(imgUri);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                String imgPath  = uri.toString();
                callback.onImageUrlReceived(imgPath);
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(udInterface.getContext(), "Upload image failed", Toast.LENGTH_SHORT).show();
            callback.onImageUrlReceived(null);
      });
    }
    private boolean validate(String name, String msv, String diemTb){
        if(TextUtils.isEmpty(name)){
            udInterface.nameError();
            return false;
        }else {
            udInterface.clearErr();
        }
        if(TextUtils.isEmpty(msv)){
            udInterface.msvError();
            return false;
        }else {
            udInterface.clearErr();
        }
        if(TextUtils.isEmpty(diemTb) || Double.valueOf(diemTb) <0){
            udInterface.diemTbError();
            return false;
        }else {
            udInterface.clearErr();
        }
        udInterface.clearErr();
        return true;
    }
    private void loading(boolean isLoad){
        if(isLoad){
            udInterface.showLoad();
        }else {
            udInterface.hideLoad();
        }
    }

    public void handleImagePickerResult(int requestCode, int resultCode, @Nullable Intent data){
        if(resultCode == Activity.RESULT_OK && data != null){
            imgUri = data.getData();
            if(binding.imgSt != null){
                binding.imgSt.setImageURI(imgUri);
            }
        }
    }
    private void resetFields(){
        binding.edGPA.setText("");
        binding.edName.setText("");
        binding.edStudentId.setText("");
        binding.imgSt.setImageResource(R.drawable.image);
    }
}
