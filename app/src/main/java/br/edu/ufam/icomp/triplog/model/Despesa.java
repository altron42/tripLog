package br.edu.ufam.icomp.triplog.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by micael on 29/07/15.
 */
public class Despesa implements Parcelable {
    private int id;
    private String nome;
    private String data;
    private String comentario;
    private double valor;
    private int categoria;
    private int pagoCom;

    public Despesa(String nome, String data, String comentario, double valor, int categoria, int pagoCom) {
        this.nome = nome;
        this.data = data;
        this.comentario = comentario;
        this.valor = valor;
        this.categoria = categoria;
        this.pagoCom = pagoCom;
    }

    public Despesa(String nome, String data, double valor, int categoria, int pagoCom) {
        this(nome, data, null, valor, categoria, pagoCom);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Despesa() {
        this(null, null, 0, 0, 0);
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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public int getPagoCom() {
        return pagoCom;
    }

    public void setPagoCom(int pagoCom) {
        this.pagoCom = pagoCom;
    }

    // Parcelable from here

    protected Despesa(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        data = in.readString();
        comentario = in.readString();
        valor = in.readDouble();
        categoria = in.readInt();
        pagoCom = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nome);
        dest.writeString(data);
        dest.writeString(comentario);
        dest.writeDouble(valor);
        dest.writeInt(categoria);
        dest.writeInt(pagoCom);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Despesa> CREATOR = new Parcelable.Creator<Despesa>() {
        @Override
        public Despesa createFromParcel(Parcel in) {
            return new Despesa(in);
        }

        @Override
        public Despesa[] newArray(int size) {
            return new Despesa[size];
        }
    };
}
