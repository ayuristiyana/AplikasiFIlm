package com.aa183.kemaladewi;

import java.util.Date;

public class Film {

    private int idFilm;
    private String judul;
    private Date tanggal;
    private String poster;
    private String sutradara;
    private String pemain;
    private String penulis;
    private String sinopsis;
    private String link;

    public Film(int idFilm, String judul, Date tanggal, String poster, String sutradara, String pemain, String penulis, String sinopsis, String link) {
        this.idFilm = idFilm;
        this.judul = judul;
        this.tanggal = tanggal;
        this.poster = poster;
        this.sutradara = sutradara;
        this.pemain = pemain;
        this.penulis = penulis;
        this.sinopsis = sinopsis;
        this.link = link;
    }

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSutradara() {
        return sutradara;
    }

    public void setSutradara(String sutradara) {
        this.sutradara = sutradara;
    }

    public String getPemain() {
        return pemain;
    }

    public void setPemain(String pemain) {
        this.pemain = pemain;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        penulis = penulis;
    }

    public String getSinopsis() { return sinopsis; }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
