package com.example.pemantauanbudidayajangkrikv6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.os.Handler;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    //inisialisasi textView Nilai suhu, kelembaban, intensitas cahay beserta statusnya
    private TextView    nilaiSuhu,
            nilaiKelembaban,
            nilaiLux,
            statusTemp,
            statusHumi,
            statusLux,
            statusBuzzer,
            idtanggal;

    //buat reference untuk firebase koneksi server/host frebase
    private Firebase mSuhu,
            mKelembaban,
            mLux,
            mstatusTemp,
            mstatusHumi,
            mstatusLux,
            mstatusBuzzer;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idtanggal = findViewById(R.id.idtanggal);
        handler = new Handler();

        // Mulai pembaruan tanggal secara real-time
        startDateTimeUpdate();

        nilaiSuhu = (TextView) findViewById(R.id.nilaiSuhu);
        nilaiKelembaban = (TextView) findViewById(R.id.nilaiKelembaban);
        nilaiLux = (TextView) findViewById(R.id.nilaiLux);
        statusTemp = (TextView) findViewById(R.id.statusTemp);
        statusHumi = (TextView) findViewById(R.id.statusHumi);
        statusLux = (TextView) findViewById(R.id.statusLux);
        statusBuzzer = (TextView) findViewById(R.id.statusBuzzer);

        //buka koneksi ke host firebase
        mSuhu = new Firebase("https://pemantauan-budidaya-jangkrikv3-default-rtdb.asia-southeast1.firebasedatabase.app/value_temp");
        mKelembaban = new Firebase("https://pemantauan-budidaya-jangkrikv3-default-rtdb.asia-southeast1.firebasedatabase.app/value_humi");
        mLux = new Firebase("https://pemantauan-budidaya-jangkrikv3-default-rtdb.asia-southeast1.firebasedatabase.app/value_lux");
        mstatusTemp = new Firebase("https://pemantauan-budidaya-jangkrikv3-default-rtdb.asia-southeast1.firebasedatabase.app/value_status_temp");
        mstatusHumi = new Firebase("https://pemantauan-budidaya-jangkrikv3-default-rtdb.asia-southeast1.firebasedatabase.app/value_status_humi");
        mstatusLux = new Firebase("https://pemantauan-budidaya-jangkrikv3-default-rtdb.asia-southeast1.firebasedatabase.app/value_status_lux");
        mstatusBuzzer = new Firebase("https://pemantauan-budidaya-jangkrikv3-default-rtdb.asia-southeast1.firebasedatabase.app/value_status_buzzer");

        //proses realtime
        mSuhu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double value_temp= dataSnapshot.getValue(Double.class); // Mengambil nilai dari Firebase

                DecimalFormat decimalFormat = new DecimalFormat("0.00");

                // Menggunakan objek DecimalFormat untuk mengubah nilai menjadi format desimal dengan dua digit di belakang koma
                String valueTemp = decimalFormat.format(value_temp);
                nilaiSuhu.setText(valueTemp);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mKelembaban.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double value_humi = dataSnapshot.getValue(Double.class); // Mengambil nilai dari Firebase

                DecimalFormat decimalFormat = new DecimalFormat("0.00");

                // Menggunakan objek DecimalFormat untuk mengubah nilai menjadi format desimal dengan dua digit di belakang koma
                String valueHumi = decimalFormat.format(value_humi);
                nilaiKelembaban.setText(valueHumi);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mLux.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double value_lux = dataSnapshot.getValue(Double.class); // Mengambil nilai dari Firebase

                // Membuat objek DecimalFormat dengan pola "0.00"
                DecimalFormat decimalFormat = new DecimalFormat("0.00");

                // Menggunakan objek DecimalFormat untuk mengubah nilai menjadi format desimal dengan dua digit di belakang koma
                String valueLux = decimalFormat.format(value_lux);
                nilaiLux.setText(valueLux);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mstatusTemp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue();

                if (value!= null) {
                    String value_status_temp =value.toString();
                    statusTemp.setText(value_status_temp);

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mstatusHumi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue();

                if (value!= null) {
                    String value_status_humi =value.toString();
                    statusHumi.setText(value_status_humi);

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mstatusLux.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue();

                if (value!= null) {
                    String value_status_lux =value.toString();
                    statusLux.setText(value_status_lux);

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mstatusBuzzer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue();

                if (value!= null) {
                    String value_status_buzzer =value.toString();
                    statusBuzzer.setText(value_status_buzzer);

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    private void startDateTimeUpdate() {
        // Set the update interval (in milliseconds)
        long updateInterval = 1000; // 1 second

        // Create a runnable to update the date and time display
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Get the current date and time
                LocalDateTime currentDateTime = LocalDateTime.now();

                // Format the date and time using DateTimeFormatter
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy\nHH:mm:ss");

                // Convert the date and time to a string
                String dateTimeString = currentDateTime.format(dateTimeFormatter);

                // Display the date and time in the TextView
                idtanggal.setText(dateTimeString);

                // Schedule the next update after a certain interval
                handler.postDelayed(this, updateInterval);
            }
        };

        // Start the first update
        handler.post(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Stop the date and time updates when the app is destroyed
        handler.removeCallbacksAndMessages(null);
    }
}