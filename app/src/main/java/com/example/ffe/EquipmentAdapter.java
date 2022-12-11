package com.example.ffe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.ViewHolder> {

    ArrayList<Equipment> items = new ArrayList<Equipment>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.equipment_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Equipment item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Equipment item){
        items.add(item);
    }

    public void setItems(ArrayList<Equipment> items){
        this.items = items;
    }

    public Equipment getItem(int position){
        return items.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView gdsName, cmpyNm, ctfcnFomNm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            gdsName = itemView.findViewById(R.id.gdsName);
            cmpyNm = itemView.findViewById(R.id.cmpyNm);
            ctfcnFomNm = itemView.findViewById(R.id.ctfcnFomNm);
        }

        public void setItem(Equipment item) {
            gdsName.setText(item.gdsNm);
            cmpyNm.setText(item.cmpyNm);
            ctfcnFomNm.setText(item.ctfcnFomNm);
        }

    }

}
