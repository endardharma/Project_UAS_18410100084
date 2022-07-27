package id.undika.project_uas;

import android.app.TimePickerDialog;
import android.content.Intent;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Update extends AppCompatActivity {

    private EditText nama;
    private Button submit, pilihJam;
    private Spinner pilihHari, pilihKelas;
    private String jamMk;
    private int hour,minute;
    private String id = "";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

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

        //menampilkan data ketika edit di klik
        Intent intent = getIntent();
        if (intent != null){
            id = intent.getStringExtra("id");
            nama.setText(intent.getStringExtra("Nama Mata Kuliah"));
            pilihJam.setText(intent.getStringExtra("Jam Mata Kuliah"));
            pilihHari.setSelection(getPilihHari(pilihHari, intent.getStringExtra("Hari Mata Kuliah")));
            pilihKelas.setSelection(getPilihKelas(pilihKelas, intent.getStringExtra("Ruangan Mata Kuliah")));
        }
    }

    private int getPilihKelas(Spinner kelas, String kelas1){
        for(int i = 0; i < kelas.getCount(); i++){
            if (kelas.getItemAtPosition(i).toString().equalsIgnoreCase(kelas1)){
                return i;
            }
        }
        return 0;
    }

    private int getPilihHari(Spinner hari, String hari1){
        for(int i = 0; i < hari.getCount(); i++){
            if (hari.getItemAtPosition(i).toString().equalsIgnoreCase(hari1)){
                return i;
            }
        }
        return 0;
    }

    private void simpanData(String nama, String jam, String hari, String kelas) {
        Map<String, Object> matkul = new HashMap<>();
        matkul.put("Nama Mata Kuliah", nama);
        matkul.put("Jam Mata Kuliah", jam);
        matkul.put("Hari Mata Kuliah", hari);
        matkul.put("Kelas Mata Kuliah", kelas);

        db.collection("Nama Mata Kuliah").document(id)
                .set(matkul)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Data Berhasil di Ubah", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Data Gagal di Ubah", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

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
