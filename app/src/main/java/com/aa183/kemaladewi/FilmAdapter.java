package com.aa183.kemaladewi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmViewHoler> {

    private Context context;
    private ArrayList<Film> dataFilm;
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy");

    public FilmAdapter(Context context, ArrayList<Film> dataFilm) {
        this.context = context;
        this.dataFilm = dataFilm;
    }

    @NonNull
    @Override
    public FilmViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.list_film, parent, false);
        return new FilmViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHoler holder, int position) {
        Film temptFilm = dataFilm.get(position);
        holder.idFilm = temptFilm.getIdFilm();
        holder.tvJudul.setText(temptFilm.getJudul());
        holder.tvHeadline.setText(temptFilm.getSinopsis());
        holder.tanggal = sdFormat.format(temptFilm.getTanggal());
        holder.poster = temptFilm.getPoster();
        holder.sutradara = temptFilm.getSutradara();
        holder.pemain = temptFilm.getPemain();
        holder.penulis = temptFilm.getPenulis();
        holder.link = temptFilm.getLink();

        try {
            File file = new File(holder.poster);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            holder.imgFilm.setImageBitmap(bitmap);
            holder.imgFilm.setContentDescription(holder.poster);
        } catch (FileNotFoundException er) {
            er.printStackTrace();
            Toast.makeText(context, "Gagal mengambil gambar dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return dataFilm.size();
    }

    public class FilmViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ImageView imgFilm;
        private TextView tvJudul, tvHeadline;
        private int idFilm;
        private String tanggal, poster, sutradara, pemain, penulis, sinopsis, link;

        public FilmViewHoler(@NonNull View itemView) {
            super(itemView);

            imgFilm = itemView.findViewById(R.id.iv_film);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvHeadline = itemView.findViewById(R.id.tv_headline);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent bukaFilm = new Intent(context, TampilActivity.class);
            bukaFilm.putExtra("ID", idFilm);
            bukaFilm.putExtra("JUDUL", tvJudul.getText().toString());
            bukaFilm.putExtra("TANGGAL", tanggal);
            bukaFilm.putExtra("POSTER", poster);
            bukaFilm.putExtra("SUTRADARA", sutradara);
            bukaFilm.putExtra("PEMAIN", pemain);
            bukaFilm.putExtra("PENULIS", penulis);
            bukaFilm.putExtra("SINOPSIS", tvHeadline.getText().toString());
            bukaFilm.putExtra("LINK", link);
            context.startActivity(bukaFilm);

        }

        @Override
        public boolean onLongClick(View v) {

            Intent bukaInput = new Intent(context, InputActivity.class);
            bukaInput.putExtra("OPERASI", "update");
            bukaInput.putExtra("ID", idFilm);
            bukaInput.putExtra("JUDUL", tvJudul.getText().toString());
            bukaInput.putExtra("TANGGAL", tanggal);
            bukaInput.putExtra("POSTER", poster);
            bukaInput.putExtra("SUTRADARA", sutradara);
            bukaInput.putExtra("PEMAIN", pemain);
            bukaInput.putExtra("PENULIS", penulis);
            bukaInput.putExtra("SINOPSIS", tvHeadline.getText().toString());
            bukaInput.putExtra("LINK", link);
            context.startActivity(bukaInput);
            return true;
        }
    }
}
