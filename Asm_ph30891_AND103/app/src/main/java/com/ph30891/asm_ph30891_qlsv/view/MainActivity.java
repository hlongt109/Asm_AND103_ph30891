package com.ph30891.asm_ph30891_qlsv.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ph30891.asm_ph30891_qlsv.R;
import com.ph30891.asm_ph30891_qlsv.adapter.StudentAdapter;
import com.ph30891.asm_ph30891_qlsv.databinding.ActivityMainBinding;
import com.ph30891.asm_ph30891_qlsv.model.Students;
import com.ph30891.asm_ph30891_qlsv.networking.ApiServices;
import com.ph30891.asm_ph30891_qlsv.networking.HttpRequest;
import com.ph30891.asm_ph30891_qlsv.presenter.MainPresenter;
import com.ph30891.asm_ph30891_qlsv.presenter.contract.MainInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MainInterface {
    private ActivityMainBinding binding;
    private ArrayList<Students> listSt = new ArrayList<>();
    private StudentAdapter adapter;
    private MainPresenter mainPresenter;
    private HttpRequest httpRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mainPresenter = new MainPresenter(this, binding);
        mainPresenter.listenData(binding.swiperefreshlayout,binding.rcvStudent,mainPresenter);
//        mainPresenter.fetchDataOnRcv(binding.rcvStudent,mainPresenter);
        binding.btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, Add_Update_Activity.class));
        });
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:3000/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        ApiServices api = retrofit.create(ApiServices.class);
//        Call<ArrayList<Students>> call = api.getListStudent();
//        call.enqueue(new Callback<ArrayList<Students>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Students>> call, Response<ArrayList<Students>> response) {
//                if(response.isSuccessful()){
//                    listSt = response.body();
//                    Log.i("Sync",String.valueOf(listSt.size()));
//                    adapter = new StudentAdapter(MainActivity.this,listSt);
//                    binding.rcvStudent.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                    binding.rcvStudent.setAdapter(adapter);
//                }else {
//                    Log.i("Sync","Fail");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Students>> call, Throwable t) {
//
//            }
//        });

        // cach 2
//        httpRequest = new HttpRequest();
//        httpRequest.callAPI()
//                .getListStudent().enqueue(get);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLoad() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.vEmpty.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showImageEmpty() {
        binding.vEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoad() {
        binding.progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideImageEmpty() {
        binding.vEmpty.setVisibility(View.INVISIBLE);
    }
}