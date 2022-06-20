package com.example.uas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private Context context;
    private List<User> list;
    private Dialog dialog;

    public interface Dialog{
        void onClick(int pos);
    }

    public void setDialog(Dialog dialog){
        this.dialog = dialog;
    }

    public UserAdapter(Context context, List<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user, parent, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder holder, int position) {
        holder.nis.setText(list.get(position).getNis());
        holder.name.setText(list.get(position).getName());
        holder.gender.setText(list.get(position).getGender());
        holder.asal.setText(list.get(position).getAsal());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nis, name, gender, asal;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            nis = itemView.findViewById(R.id.nis);
            name = itemView.findViewById(R.id.name);
            gender = itemView.findViewById(R.id.gender);
            asal = itemView.findViewById(R.id.asal);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(dialog != null){
                        dialog.onClick(getLayoutPosition());
                    }
                }
            });
        }
    }
}
