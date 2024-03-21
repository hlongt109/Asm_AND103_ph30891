package com.ph30891.asm_ph30891_qlsv.presenter;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ph30891.asm_ph30891_qlsv.R;
import com.ph30891.asm_ph30891_qlsv.adapter.StudentAdapter;
import com.ph30891.asm_ph30891_qlsv.databinding.ActivityMainBinding;
import com.ph30891.asm_ph30891_qlsv.model.Students;
import com.ph30891.asm_ph30891_qlsv.networking.ApiServices;
import com.ph30891.asm_ph30891_qlsv.presenter.contract.MainInterface;
import com.ph30891.asm_ph30891_qlsv.view.Add_Update_Activity;
import com.ph30891.asm_ph30891_qlsv.view.MainActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPresenter {
    private MainInterface mainInterface;
    private ArrayList<Students> listSt = new ArrayList<>();
    private StudentAdapter adapter;
    private ActivityMainBinding binding;
    public MainPresenter(MainInterface mainInterface,ActivityMainBinding binding) {
        this.mainInterface = mainInterface;
        this.binding = binding;
    }

    public void listenData(SwipeRefreshLayout swipeRefreshLayout,RecyclerView rcv, MainPresenter mainPresenter){

        swipeRefreshLayout.setOnRefreshListener(() -> {
            new Handler().postDelayed(() -> {
                fetchDataOnRcv();
            },2000);
        });
        fetchDataOnRcv();
    }
    public void fetchDataOnRcv(){
        loading(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices api = retrofit.create(ApiServices.class);
        Call<ArrayList<Students>> call = api.getListStudent();
        call.enqueue(new Callback<ArrayList<Students>>() {
            @Override
            public void onResponse(Call<ArrayList<Students>> call, Response<ArrayList<Students>> response) {
                binding.swiperefreshlayout.setRefreshing(false);
                if(response.isSuccessful()){
                    listSt = response.body();
                    if(adapter == null){
                        adapter = new StudentAdapter(mainInterface.getContext(), listSt);
                        binding.rcvStudent.setLayoutManager(new LinearLayoutManager(mainInterface.getContext()));
                        binding.rcvStudent.setAdapter(adapter);
                    }else {
                        adapter.setStudentsList(listSt);
                        adapter.notifyDataSetChanged();
                    }
                    loading(false);
                    mainInterface.hideImageEmpty();
                }else {
                    Log.i("Sync","Fail");
                    loading(false);
                    mainInterface.showImageEmpty();
                }
                adapter.showMenuUD((students, view) -> {
                    showMenuUtities(students,view);
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Students>> call, Throwable t) {
                loading(false);
                mainInterface.showImageEmpty();
            }
        });

    }
    private void loading (boolean isLoad){
        if(isLoad){
            mainInterface.showLoad();
        }else {
            mainInterface.hideLoad();
        }
    }
    private void showMenuUtities(Students students, View view) {
        PopupMenu popupMenu = new PopupMenu(mainInterface.getContext(), view);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.setGravity(Gravity.START);
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.navUpdate) {
                Intent intent = new Intent(mainInterface.getContext(), Add_Update_Activity.class);
                intent.putExtra("st", students);
                mainInterface.getContext().startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.navDelete) {
                openDelete(students);
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();

    }
    private void openDelete(Students students) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainInterface.getContext());
        builder.setTitle("Message !");
        builder.setCancelable(false);
        builder.setMessage("Do you want to delete this student ?");
        builder.setNegativeButton("No", null);
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            String idUser = students.get_id();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ApiServices apiServices = retrofit.create(ApiServices.class);
            Call<Students> call = apiServices.removeStudent(idUser);
            call.enqueue(new Callback<Students>() {
                @Override
                public void onResponse(Call<Students> call, Response<Students> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(mainInterface.getContext(), "Delete success", Toast.LENGTH_SHORT).show();
                        fetchDataOnRcv();
                        dialogInterface.dismiss();

                    }else {
                        Toast.makeText(mainInterface.getContext(), "Delete fail", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Students> call, Throwable t) {
                    Toast.makeText(mainInterface.getContext(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            });
        });
        builder.create().show();
    }
}
