package br.edu.ufam.icomp.triplog.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by micael on 31/07/15.
 */
public class Hospedagem implements Parcelable {
    private String nome;
    private String data_checkin;
    private String hora_checkin;
    private String data_checkout;
    private String hora_checkout;
    private String comentario;
    private int id;
    private int idViagem;

    public Hospedagem() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData_checkin() {
        return data_checkin;
    }

    public void setData_checkin(String data_checkin) {
        this.data_checkin = data_checkin;
    }

    public String getHora_checkin() {
        return hora_checkin;
    }

    public void setHora_checkin(String hora_checkin) {
        this.hora_checkin = hora_checkin;
    }

    public String getData_checkout() {
        return data_checkout;
    }

    public void setData_checkout(String data_checkout) {
        this.data_checkout = data_checkout;
    }

    public String getHora_checkout() {
        return hora_checkout;
    }

    public void setHora_checkout(String hora_checkout) {
        this.hora_checkout = hora_checkout;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdViagem() {
        return idViagem;
    }

    public void setIdViagem(int idViagem) {
        this.idViagem = idViagem;
    }

    // Parcalable from here

    protected Hospedagem(Parcel in) {
        nome = in.readString();
        data_checkin = in.readString();
        hora_checkin = in.readString();
        data_checkout = in.readString();
        hora_checkout = in.readString();
        comentario = in.readString();
        id = in.readInt();
        idViagem = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeString(data_checkin);
        dest.writeString(hora_checkin);
        dest.writeString(data_checkout);
        dest.writeString(hora_checkout);
        dest.writeString(comentario);
        dest.writeInt(id);
        dest.writeInt(idViagem);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Hospedagem> CREATOR = new Parcelable.Creator<Hospedagem>() {
        @Override
        public Hospedagem createFromParcel(Parcel in) {
            return new Hospedagem(in);
        }

        @Override
        public Hospedagem[] newArray(int size) {
            return new Hospedagem[size];
        }
    };
}
