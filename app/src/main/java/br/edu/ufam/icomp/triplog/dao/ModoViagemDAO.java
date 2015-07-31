package br.edu.ufam.icomp.triplog.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.edu.ufam.icomp.triplog.model.ModoViagem;
import br.edu.ufam.icomp.triplog.util.BancoDeDados;

/**
 * Created by micael on 31/07/15.
 */
public class ModoViagemDAO {

    private SQLiteDatabase bancoDeDados;

    public ModoViagemDAO (Context context) {
        this.bancoDeDados = (new BancoDeDados(context).getWritableDatabase());
    }

    public ModoViagem getModoViagem(int id) {
        ModoViagem novo= null;
        String query = "SELECT * FROM ModoViagem WHERE rowid=" + id;
        Cursor cursor = this.bancoDeDados.rawQuery(query,null);
        if(cursor.moveToNext()) {
            novo = new ModoViagem();
            novo.setNome(cursor.getString(cursor.getColumnIndex(BancoDeDados.MODOVIAGEM_COL_NOME)));
            novo.setPartida(cursor.getString(cursor.getColumnIndex(BancoDeDados.MODOVIAGEM_COL_PARTIDA)));
            novo.setPartida_data(cursor.getString(cursor.getColumnIndex(BancoDeDados.MODOVIAGEM_COL_PARTIDA_DATA)));
            novo.setPartida_hora(cursor.getString(cursor.getColumnIndex(BancoDeDados.MODOVIAGEM_COL_PARTIDA_HORA)));
            novo.setChegada(cursor.getString(cursor.getColumnIndex(BancoDeDados.MODOVIAGEM_COL_CHEGADA)));
            novo.setChegada_data(cursor.getString(cursor.getColumnIndex(BancoDeDados.MODOVIAGEM_COL_CHEGADA_DATA)));
            novo.setChegada_hora(cursor.getString(cursor.getColumnIndex(BancoDeDados.MODOVIAGEM_COL_CHEGADA_HORA)));
            novo.setComentario(cursor.getString(cursor.getColumnIndex(BancoDeDados.MODOVIAGEM_COL_COMENTARIO)));
            novo.setTipoModo(cursor.getInt(cursor.getColumnIndex(BancoDeDados.MODOVIAGEM_COL_TIPO_MODO)));
            novo.setIdViagem(cursor.getInt(cursor.getColumnIndex(BancoDeDados.COL_ID_VIAGEM)));
            novo.setId(id);
        }
        cursor.close();
        return novo;
    }

    public boolean addModoViagem(ModoViagem modoViagem) {
        try {
            String query = "INSERT INTO ModoViagem (" +
                    BancoDeDados.MODOVIAGEM_COL_NOME + ", " +
                    BancoDeDados.MODOVIAGEM_COL_PARTIDA + ", " +
                    BancoDeDados.MODOVIAGEM_COL_PARTIDA_DATA + ", " +
                    BancoDeDados.MODOVIAGEM_COL_PARTIDA_HORA + ", " +
                    BancoDeDados.MODOVIAGEM_COL_CHEGADA + ", " +
                    BancoDeDados.MODOVIAGEM_COL_CHEGADA_DATA + ", " +
                    BancoDeDados.MODOVIAGEM_COL_CHEGADA_HORA + ", " +
                    BancoDeDados.MODOVIAGEM_COL_COMENTARIO + ", " +
                    BancoDeDados.MODOVIAGEM_COL_TIPO_MODO + ", " +
                    BancoDeDados.COL_ID_VIAGEM + ") VALUES ('" +
                    modoViagem.getNome() + "', '" +
                    modoViagem.getPartida() + "', '" +
                    modoViagem.getPartida_data() + "', '" +
                    modoViagem.getPartida_hora() + "', '" +
                    modoViagem.getChegada() + "', '" +
                    modoViagem.getChegada_data() + "', '" +
                    modoViagem.getChegada_hora() + "', '" +
                    modoViagem.getComentario() + "', " +
                    modoViagem.getTipoModo() + ", " +
                    modoViagem.getIdViagem() + ")";
            this.bancoDeDados.execSQL(query);
            return true;
        } catch (SQLException e) {
            Log.e("TripLogDB", e.getMessage());
            return false;
        }
    }

    public boolean delModoViagem(int id) {
        try {
            String query = "DELETE FROM ModoViagem WHERE rowid=" + id;
            this.bancoDeDados.execSQL(query);
            return true;
        } catch (SQLException e) {
            Log.e("TripLogDB", e.getMessage());
            return false;
        }

    }

    public boolean delModoViagemTodos(int idViagem) {
        try {
            String query = "DELETE FROM ModoViagem WHERE " +
                    BancoDeDados.COL_ID_VIAGEM + "=" + idViagem;
            this.bancoDeDados.execSQL(query);
            return true;
        } catch (SQLException e) {
            Log.e("TripLogDB", e.getMessage());
            return false;
        }
    }

    public Cursor getModoViagemLista(int idViagem) {
        String query = "SELECT " +
                "rowid AS _id, " +
                BancoDeDados.MODOVIAGEM_COL_TIPO_MODO + ", " +
                BancoDeDados.MODOVIAGEM_COL_PARTIDA + ", " +
                BancoDeDados.MODOVIAGEM_COL_PARTIDA_DATA + ", " +
                BancoDeDados.MODOVIAGEM_COL_PARTIDA_HORA + ", " +
                BancoDeDados.MODOVIAGEM_COL_CHEGADA + ", " +
                BancoDeDados.MODOVIAGEM_COL_CHEGADA_DATA + ", " +
                BancoDeDados.MODOVIAGEM_COL_CHEGADA_HORA + ", " +
                BancoDeDados.MODOVIAGEM_COL_NOME + " " +
                "FROM ModoViagem WHERE " +
                BancoDeDados.COL_ID_VIAGEM + "=" + idViagem +
                " ORDER BY rowid";
        return this.bancoDeDados.rawQuery(query, null);
    }
}
