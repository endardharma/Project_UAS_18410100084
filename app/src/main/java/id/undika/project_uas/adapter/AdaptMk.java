package id.undika.project_uas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.Project_UAS.R;
//import com.example.Project_UAS.model.modelMatkul;


import java.util.List;

import id.undika.project_uas.R;
import id.undika.project_uas.model.ModelMk;

public class AdaptMk extends RecyclerView.Adapter<AdaptMk.MyViewHolder> {
    private Context context;
    private List<ModelMk> list;
    private Dialog dialog;

    public interface Dialog{
        void onClick(int pos);
    }

    public void setDialog(Dialog dialog){
        this.dialog = dialog;
    }

    public AdaptMk(Context context, List<ModelMk> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nama.setText(list.get(position).getNamaMk());
        holder.jam.setText(list.get(position).getJamMk());
        holder.hari.setText(list.get(position).getHariMk());
        holder.kelas.setText(list.get(position).getRuanganMk());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nama,jam,kelas,hari;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            nama = itemView.findViewById(R.id.namaMk);
            jam = itemView.findViewById(R.id.jamMk);
            hari = itemView.findViewById(R.id.hariMk);
            kelas = itemView.findViewById(R.id.ruanganMk);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog!=null){
                        dialog.onClick(getLayoutPosition());
                    }
                }
            });
        }
    }
}
