package com.ph30891.asm_ph30891_qlsv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ph30891.asm_ph30891_qlsv.R;
import com.ph30891.asm_ph30891_qlsv.databinding.ItemStudentBinding;
import com.ph30891.asm_ph30891_qlsv.model.Students;
import com.ph30891.asm_ph30891_qlsv.presenter.MainPresenter;
import com.ph30891.asm_ph30891_qlsv.presenter.contract.OnClickStudent;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.myViewHolder>{
    private final Context context;
    private ArrayList<Students> list;
    private MainPresenter presenter;

    OnClickStudent onClickStudent;

    public void setStudentsList(ArrayList<Students> studentsList) {
        this.list = studentsList;
        notifyDataSetChanged();
    }

    public StudentAdapter(Context context, ArrayList<Students> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStudentBinding binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new myViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Students students = list.get(position);
        holder.setDataOnItem(students);
        holder.binding.btnMore.setOnClickListener(v -> {
            onClickStudent.onClick(students, v);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
       private ItemStudentBinding binding;
       myViewHolder(ItemStudentBinding itemStudentBinding){
           super(itemStudentBinding.getRoot());
           binding = itemStudentBinding;
       }
       void setDataOnItem(Students st){
           Glide.with(context).load(st.getAvatar()).error(R.drawable.image).into(binding.imvStudent);
           binding.tvStudentId.setText(st.getMsv());
           binding.tvName.setText(st.getName());
           binding.tvGPA.setText(String.valueOf(st.getDiemTb()));
       }
   }
    public void showMenuUD(OnClickStudent onClickStudent){
        this.onClickStudent = onClickStudent;
    }

}
