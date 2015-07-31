package br.edu.ufam.icomp.triplog.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by micael on 31/07/15.
 */
public class ModoViagem implements Parcelable {
    private String nome;
    private String partida;
    private String partida_data;
    private String partida_hora;
    private String chegada;
    private String chegada_data;
    private String chegada_hora;
    private String comentario;
    private int tipoModo;
    private int idViagem;
    private int id;

    public ModoViagem(String nome, int idViagem, int tipoModo, String partida, String chegada) {
        this.nome = nome;
        this.idViagem = idViagem;
        this.tipoModo = tipoModo;
        this.partida = partida;
        this.chegada = chegada;
    }

    public ModoViagem() {
        this(null,0,0,null,null);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public String getPartida_data() {
        return partida_data;
    }

    public void setPartida_data(String partida_data) {
        this.partida_data = partida_data;
    }

    public String getPartida_hora() {
        return partida_hora;
    }

    public void setPartida_hora(String partida_hora) {
        this.partida_hora = partida_hora;
    }

    public String getChegada() {
        return chegada;
    }

    public void setChegada(String chegada) {
        this.chegada = chegada;
    }

    public String getChegada_data() {
        return chegada_data;
    }

    public void setChegada_data(String chegada_data) {
        this.chegada_data = chegada_data;
    }

    public String getChegada_hora() {
        return chegada_hora;
    }

    public void setChegada_hora(String chegada_hora) {
        this.chegada_hora = chegada_hora;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getTipoModo() {
        return tipoModo;
    }

    public void setTipoModo(int tipoModo) {
        this.tipoModo = tipoModo;
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

    // Parcelable from here

    protected ModoViagem(Parcel in) {
        nome = in.readString();
        partida = in.readString();
        partida_data = in.readString();
        partida_hora = in.readString();
        chegada = in.readString();
        chegada_data = in.readString();
        chegada_hora = in.readString();
        comentario = in.readString();
        tipoModo = in.readInt();
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
        dest.writeString(partida);
        dest.writeString(partida_data);
        dest.writeString(partida_hora);
        dest.writeString(chegada);
        dest.writeString(chegada_data);
        dest.writeString(chegada_hora);
        dest.writeString(comentario);
        dest.writeInt(tipoModo);
        dest.writeInt(idViagem);
        dest.writeInt(id);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ModoViagem> CREATOR = new Parcelable.Creator<ModoViagem>() {
        @Override
        public ModoViagem createFromParcel(Parcel in) {
            return new ModoViagem(in);
        }

        @Override
        public ModoViagem[] newArray(int size) {
            return new ModoViagem[size];
        }
    };
}
