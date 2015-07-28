package br.edu.ufam.icomp.triplog.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by micael on 26/07/15.
 */
public class Viagem implements Parcelable {

    private int id;
    private String nome;
    private String comeco;
    private String fim;
    private String detalhes;
    private int tipo;
    private String icone;

    public Viagem(String nome, String comeco, String fim, String detalhes, int tipo) {
        this.nome = nome;
        this.comeco = comeco;
        this.fim = fim;
        this.detalhes = detalhes;
        this.tipo = tipo;
    }

    public Viagem() {
        this(null,null,null,null,0);
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

    public String getComeco() {
        return comeco;
    }

    public void setComeco(String comeco) {
        this.comeco = comeco;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public int getTipo() {
        return tipo;
    }

    public String getTipoNome() {
        return tipo == 1 ? "pessoal" : "negócios";
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    // Parcelable implementation starts here

    protected Viagem(Parcel in) {
        nome = in.readString();
        comeco = in.readString();
        fim = in.readString();
        detalhes = in.readString();
        tipo = in.readInt();
        icone = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeString(comeco);
        dest.writeString(fim);
        dest.writeString(detalhes);
        dest.writeInt(tipo);
        dest.writeString(icone);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Viagem> CREATOR = new Parcelable.Creator<Viagem>() {
        @Override
        public Viagem createFromParcel(Parcel in) {
            return new Viagem(in);
        }

        @Override
        public Viagem[] newArray(int size) {
            return new Viagem[size];
        }
    };
}
