package id.undika.project_uas.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.undika.project_uas.R;
import id.undika.project_uas.adapter.AdaptMk;
import id.undika.project_uas.model.ModelMk;

public class FragJadwal extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private AdaptMk adaptMk;
    private List<ModelMk> list = new ArrayList<>();

    public FragJadwal(){
    }

    public static String getCurrentDate(){
        Date call = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE", new Locale("id", "ID"));
        String tanggal = simpleDateFormat.format(call);
        return tanggal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_jadwal, container, false);

        recyclerView = view.findViewById(R.id.recycleView);
        adaptMk = new AdaptMk(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adaptMk);

        getData();
        return  view;
    }

    private void getData() {
        db.collection("Nama Mata Kuliah")
                .whereEqualTo("Hari Mata Kuliah", getCurrentDate())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                ModelMk modal = new ModelMk(document.getString("Nama Mata Kuliah"),document.getString("Waktu Mata Kuliah"),document.getString("Hari Mata Kuliah"),document.getString("Kelas Mata Kuliah"));
                                modal.setId(document.getId());
                                list.add(modal);
                            }
                            adaptMk.notifyDataSetChanged();
                        }else{
                            Toast.makeText(getActivity(), "data gagal di ambil", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }
}
