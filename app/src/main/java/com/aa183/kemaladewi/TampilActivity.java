package com.aa183.kemaladewi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TampilActivity extends AppCompatActivity {

    private ImageView imgFilm;
    private TextView tvJudul, tvTanggal, tvSutradara, tvPemain, tvPenulis, tvSinopsis;
    private String linkFilm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil);

        imgFilm = findViewById(R.id.iv_film);
        tvJudul = findViewById(R.id.tv_judul);
        tvTanggal = findViewById(R.id.tv_tanggal);
        tvSutradara = findViewById(R.id.tv_sutradara);
        tvPemain = findViewById(R.id.tv_pemain);
        tvPenulis = findViewById(R.id.tv_penulis);
        tvSinopsis = findViewById(R.id.tv_sinopsis);

        Intent terimaData = getIntent();
        tvJudul.setText(terimaData.getStringExtra("JUDUL"));
        tvTanggal.setText(terimaData.getStringExtra("TANGGAL"));
        tvSutradara.setText(terimaData.getStringExtra("SUTRADARA"));
        tvPemain.setText(terimaData.getStringExtra("PEMAIN"));
        tvPenulis.setText(terimaData.getStringExtra("PENULIS"));
        tvSinopsis.setText(terimaData.getStringExtra("SINOPSIS"));
        String imgLocation = terimaData.getStringExtra("POSTER");

        try {
            File file = new File(imgLocation);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            imgFilm.setImageBitmap(bitmap);
            imgFilm.setContentDescription(imgLocation);
        } catch (FileNotFoundException er) {
            er.printStackTrace();
            Toast.makeText(this, "Gagal mengambil gambar dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }

        linkFilm = terimaData.getStringExtra("LINK");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tampil_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.item_bagikan)
        {
            Intent bagikanFilm = new Intent(Intent.ACTION_SEND);
            bagikanFilm.putExtra(Intent.EXTRA_SUBJECT, tvJudul.getText().toString());
            bagikanFilm.putExtra(Intent.EXTRA_TEXT, linkFilm);
            bagikanFilm.setType("text/plain");
            startActivity(Intent.createChooser(bagikanFilm, "Bagikan film"));
        }
        return super.onOptionsItemSelected(item);
    }
}

