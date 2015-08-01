package br.edu.ufam.icomp.triplog.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.edu.ufam.icomp.triplog.model.Hospedagem;
import br.edu.ufam.icomp.triplog.util.BancoDeDados;

/**
 * Created by micael on 31/07/15.
 */
public class HospedagemDAO {

    private SQLiteDatabase bancoDeDados;

    public HospedagemDAO (Context context) {
        this.bancoDeDados = (new BancoDeDados(context).getWritableDatabase());
    }

    public Hospedagem getHospedagem(int id) {
        Hospedagem novo= null;
        String query = "SELECT * FROM Hospedagens WHERE rowid=" + id;
        Cursor cursor = this.bancoDeDados.rawQuery(query,null);
        if(cursor.moveToNext()) {
            novo = new Hospedagem();
            novo.setNome(cursor.getString(cursor.getColumnIndex(BancoDeDados.HOSPEDAGEM_COL_NOME)));
            novo.setData_checkin(cursor.getString(cursor.getColumnIndex(BancoDeDados.HOSPEDAGEM_COL_DATA_CHECKIN)));
            novo.setHora_checkin(cursor.getString(cursor.getColumnIndex(BancoDeDados.HOSPEDAGEM_COL_HORA_CHECKIN)));
            novo.setData_checkout(cursor.getString(cursor.getColumnIndex(BancoDeDados.HOSPEDAGEM_COL_DATA_CHECKOUT)));
            novo.setHora_checkout(cursor.getString(cursor.getColumnIndex(BancoDeDados.HOSPEDAGEM_COL_HORA_CHECKOUT)));
            novo.setComentario(cursor.getString(cursor.getColumnIndex(BancoDeDados.HOSPEDAGEM_COL_COMENTARIO)));
            novo.setIdViagem(cursor.getInt(cursor.getColumnIndex(BancoDeDados.COL_ID_VIAGEM)));
            novo.setId(id);
        }
        cursor.close();
        return novo;
    }

    public boolean addHospedagem(Hospedagem hospedagem) {
        try {
            String query = "INSERT INTO Hospedagens (" +
                    BancoDeDados.HOSPEDAGEM_COL_NOME + ", " +
                    BancoDeDados.HOSPEDAGEM_COL_DATA_CHECKIN + ", " +
                    BancoDeDados.HOSPEDAGEM_COL_HORA_CHECKIN + ", " +
                    BancoDeDados.HOSPEDAGEM_COL_DATA_CHECKOUT + ", " +
                    BancoDeDados.HOSPEDAGEM_COL_HORA_CHECKOUT + ", " +
                    BancoDeDados.HOSPEDAGEM_COL_COMENTARIO + ", " +
                    BancoDeDados.COL_ID_VIAGEM + ") VALUES ('" +
                    hospedagem.getNome() + "', '" +
                    hospedagem.getData_checkin() + "', '" +
                    hospedagem.getHora_checkin() + "', '" +
                    hospedagem.getData_checkout() + "', '" +
                    hospedagem.getHora_checkout() + "', '" +
                    hospedagem.getComentario() + "', " +
                    hospedagem.getIdViagem() + ")";
            this.bancoDeDados.execSQL(query);
            return true;
        } catch (SQLException e) {
            Log.e("TripLogDB", e.getMessage());
            return false;
        }
    }

    public boolean delHospedagem(int id) {
        try {
            String query = "DELETE FROM Hospedagens WHERE rowid=" + id;
            this.bancoDeDados.execSQL(query);
            return true;
        } catch (SQLException e) {
            Log.e("TripLogDB", e.getMessage());
            return false;
        }

    }

    public boolean delHospedagens(int idViagem) {
        try {
            String query = "DELETE FROM Hospedagens WHERE " +
                    BancoDeDados.COL_ID_VIAGEM + "=" + idViagem;
            this.bancoDeDados.execSQL(query);
            return true;
        } catch (SQLException e) {
            Log.e("TripLogDB", e.getMessage());
            return false;
        }
    }

    public Cursor getHospedagens(int idViagem) {
        String query = "SELECT " +
                "rowid AS _id, " +
                BancoDeDados.HOSPEDAGEM_COL_NOME + ", " +
                BancoDeDados.HOSPEDAGEM_COL_DATA_CHECKIN + ", " +
                BancoDeDados.HOSPEDAGEM_COL_HORA_CHECKIN + ", " +
                BancoDeDados.HOSPEDAGEM_COL_DATA_CHECKOUT + ", " +
                BancoDeDados.HOSPEDAGEM_COL_HORA_CHECKOUT + " " +
                "FROM Hospedagens WHERE " +
                BancoDeDados.COL_ID_VIAGEM + "=" + idViagem +
                " ORDER BY rowid";
        return this.bancoDeDados.rawQuery(query, null);
    }
}
