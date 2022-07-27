package id.undika.project_uas;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class InputActivity extends AppCompatActivity {

    private EditText nama;
    private Button submit, pilihJam;
    private Spinner pilihHari, pilihKelas;
    private String jamMk;
    private int hour, minute;
    private String id="";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        nama = findViewById(R.id.txt_namaMk);
        pilihJam = findViewById(R.id.btn_jam);
        pilihHari = findViewById(R.id.spn_hari);
        pilihKelas = findViewById(R.id.spn_ruangKelas);
        submit = findViewById(R.id.btn_submit);

        //Spinner untuk Pilih Hari
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.pilih_hari, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pilihHari.setAdapter(adapter);

        //Spinner untuk Pilih Kelas
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.pilih_kelas, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pilihKelas.setAdapter(adapter1);

        //Btn Submit
        submit.setOnClickListener(view-> {
            if (nama.getText().length()>0){
                simpanData(nama.getText().toString(),jamMk,pilihHari.getSelectedItem().toString(),pilihKelas.getSelectedItem().toString());
            } else {
                Toast.makeText(getApplicationContext(), "Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Tambah Data
    private void simpanData(String nama, String kelas, String hari, String jam){
        Map<String, Object> mk = new HashMap<>();
        mk.put("Nama Mata Kuliah :", nama);
        mk.put("Ruang Kelas Mata Kuliah :", kelas);
        mk.put("Hari Mata Kuliah :", hari);
        mk.put("Jam Mata Kuliah :", jam);

        db.collection("Data Mata Kuliah Mahasiswa")
                .add(mk)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    //Timepicker
    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                jamMk= String.format(Locale.getDefault(), "%02d:%02d",hour,minute);
                pilihJam.setText(String.format(Locale.getDefault(), "%02d:%02d",hour,minute));

            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Pilih Jam Mata Kuliah");
        timePickerDialog.show();
    }
}


