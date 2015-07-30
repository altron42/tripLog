package br.edu.ufam.icomp.triplog.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by micael on 29/07/15.
 */
public class Carteira implements Parcelable {
    private int id;
    private int idViagem;
    private String nome;
    private double valor_disponivel;

    public Carteira(String nome, double valor_disponivel) {
        this.nome = nome;
        this.valor_disponivel = valor_disponivel;
    }

    public Carteira() {
        this(null,0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValorDisponivel() {
        return valor_disponivel;
    }

    public void setValorDisponivel(double valor_disponivel) {
        this.valor_disponivel = valor_disponivel;
    }

    public int getIdViagem() {
        return idViagem;
    }

    public void setIdViagem(int idViagem) {
        this.idViagem = idViagem;
    }

    public void subtrairValor(double valor) {
        valor_disponivel -= valor;
    }

    public void acrescentarValor(double valor) {
        valor_disponivel += valor;
    }

    // Parcelable from here

    protected Carteira(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        valor_disponivel = in.readDouble();
        idViagem = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nome);
        dest.writeDouble(valor_disponivel);
        dest.writeInt(idViagem);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Carteira> CREATOR = new Parcelable.Creator<Carteira>() {
        @Override
        public Carteira createFromParcel(Parcel in) {
            return new Carteira(in);
        }

        @Override
        public Carteira[] newArray(int size) {
            return new Carteira[size];
        }
    };
}
