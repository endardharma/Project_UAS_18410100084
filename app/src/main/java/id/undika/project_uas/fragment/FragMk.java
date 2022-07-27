package id.undika.project_uas.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import id.undika.project_uas.InputActivity;
import id.undika.project_uas.R;
import id.undika.project_uas.Update;
import id.undika.project_uas.adapter.AdaptMk;
import id.undika.project_uas.model.ModelMk;

public class FragMk extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private List<ModelMk> list = new ArrayList<>();
    private AdaptMk adaptMk;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mk, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floating_action_button);

        recyclerView = view.findViewById(R.id.recycleView);
        adaptMk = new AdaptMk(getActivity(), list);
        adaptMk.setDialog(new AdaptMk.Dialog() {

            @Override
            public void onClick(int pos) {
                final CharSequence[] dialogItem = {"Update","Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Intent intent = new Intent(getActivity(), Update.class);
                                intent.putExtra("id", list.get(pos).getId());
                                intent.putExtra("nama", list.get(pos).getNamaMk());
                                intent.putExtra("Jam", list.get(pos).getJamMk());
                                intent.putExtra("hari", list.get(pos).getHariMk());
                                intent.putExtra("kelas", list.get(pos).getRuanganMk());
                                startActivity(intent);
                                break;
                            case 1:
                                deleteDate(list.get(pos).getId());
                                break;

                        }
                    }
                });
                dialog.show();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adaptMk);

        fab.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), InputActivity.class));
        });
        getData();
        return view;

    }

    @Override
    public void onStart(){
        super.onStart();
        getData();
    }

    //menampilkan recycleview pada navbar mk
    private void getData(){
        db.collection("mata kuliah")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                ModelMk modal = new ModelMk(document.getString("Nama Mata Kuliah"),
                                        document.getString("Jam Mata Kuliah"),document.getString("Hari Mata Kuliah"),
                                        document.getString("Ruangan Mata Kuliah"));
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

    //menghapus
    private void deleteDate(String id){
        db.collection("Nama Mata Kuliah").document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(getActivity(), "Data Gagal Di Hapus", Toast.LENGTH_SHORT).show();
                        }
                        getData();
                    }
                });
    }

}
