package br.edu.ufam.icomp.triplog.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by micael on 01/08/15.
 */
public class Atividade implements Parcelable {
    private String nome;
    private String data;
    private String hora;
    private String detalhes;
    private int idViagem;
    private int id;

    public Atividade(String nome, String data, String hora, int idViagem) {
        this.nome = nome;
        this.data = data;
        this.hora = hora;
        this.idViagem = idViagem;
    }

    public Atividade() {
        this(null,null,null,0);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public int getIdViagem() {
        return idViagem;
    }

    public void setIdViagem(int idViagem) {
        this.idViagem = idViagem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Parcalable from here

    protected Atividade(Parcel in) {
        nome = in.readString();
        data = in.readString();
        hora = in.readString();
        detalhes = in.readString();
        idViagem = in.readInt();
        id = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeString(data);
        dest.writeString(hora);
        dest.writeString(detalhes);
        dest.writeInt(idViagem);
        dest.writeInt(id);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Atividade> CREATOR = new Parcelable.Creator<Atividade>() {
        @Override
        public Atividade createFromParcel(Parcel in) {
            return new Atividade(in);
        }

        @Override
        public Atividade[] newArray(int size) {
            return new Atividade[size];
        }
    };
}
