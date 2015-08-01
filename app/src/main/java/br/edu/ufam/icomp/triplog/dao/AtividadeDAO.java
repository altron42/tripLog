package br.edu.ufam.icomp.triplog.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.edu.ufam.icomp.triplog.model.Atividade;
import br.edu.ufam.icomp.triplog.util.BancoDeDados;

/**
 * Created by micael on 01/08/15.
 */
public class AtividadeDAO {
    private SQLiteDatabase bancoDeDados;

    public AtividadeDAO (Context context) {
        this.bancoDeDados = (new BancoDeDados(context).getWritableDatabase());
    }

    public Atividade getAtividade(int id) {
        Atividade atividade = null;
        String query = "SELECT * FROM Atividades WHERE rowid=" + id;
        Cursor cursor = this.bancoDeDados.rawQuery(query, null);
        if (cursor.moveToNext()) {
            atividade = new Atividade();
            atividade.setId(id);
            atividade.setIdViagem(cursor.getInt(cursor.getColumnIndex(BancoDeDados.COL_ID_VIAGEM)));
            atividade.setNome(cursor.getString(cursor.getColumnIndex(BancoDeDados.ATIVIDADE_COL_NOME)));
            atividade.setData(cursor.getString(cursor.getColumnIndex(BancoDeDados.ATIVIDADE_COL_DATA)));
            atividade.setHora(cursor.getString(cursor.getColumnIndex(BancoDeDados.ATIVIDADE_COL_HORA)));
            atividade.setDetalhes(cursor.getColumnName(cursor.getColumnIndex(BancoDeDados.ATIVIDADE_COL_DETALHES)));
        }
        cursor.close();
        return atividade;
    }

    public boolean addAtividade(Atividade atividade) {
        try {
            String query = "INSERT INTO Atividades (" +
                    BancoDeDados.ATIVIDADE_COL_NOME + ", " +
                    BancoDeDados.ATIVIDADE_COL_DATA + ", " +
                    BancoDeDados.ATIVIDADE_COL_HORA + ", " +
                    BancoDeDados.ATIVIDADE_COL_DETALHES + ", " +
                    BancoDeDados.COL_ID_VIAGEM + ") VALUES ('" +
                    atividade.getNome() + "', '" +
                    atividade.getData() + "', '" +
                    atividade.getHora() + "', '" +
                    atividade.getDetalhes() + "', " +
                    atividade.getIdViagem() + ")";
            this.bancoDeDados.execSQL(query);
            return true;
        } catch (SQLException e) {
            Log.e("TripLogDB", e.getMessage());
            return false;
        }
    }

    public boolean editAtividade(Atividade atividade) {
        try {
            String query = "UPDATE Atividades SET " +
                    BancoDeDados.ATIVIDADE_COL_NOME + "='" + atividade.getNome() + "', " +
                    BancoDeDados.ATIVIDADE_COL_DATA + "='" + atividade.getData() + "', " +
                    BancoDeDados.ATIVIDADE_COL_HORA + "='" + atividade.getHora() + "', " +
                    BancoDeDados.ATIVIDADE_COL_DETALHES + "='" + atividade.getDetalhes() + "' " +
                    "WHERE rowid=" + atividade.getId();
            this.bancoDeDados.execSQL(query);
            return true;
        } catch (SQLException e) {
            Log.e("TripLogDB", e.getMessage());
            return false;
        }
    }

    public boolean delAtividade(int id) {
        try {
            String query = "DELETE FROM Atividades WHERE rowid=" + id;
            this.bancoDeDados.execSQL(query);
            return true;
        } catch (SQLException e) {
            Log.e("TripLogDB", e.getMessage());
            return false;
        }
    }

    public boolean delAtividades(int idViagem) {
        try {
            Log.i(null, "APAGANDO atividades " + idViagem);
            String query = "DELETE FROM Atividades WHERE " +
                    BancoDeDados.COL_ID_VIAGEM + "=" + idViagem;
            this.bancoDeDados.execSQL(query);
            return true;
        } catch (SQLException e) {
            Log.e("TripLogDB", e.getMessage());
            return false;
        }
    }

    public Cursor getAtividades(int idViagem) {
        String query = "SELECT " +
                "rowid AS _id, " +
                BancoDeDados.ATIVIDADE_COL_NOME + ", " +
                BancoDeDados.ATIVIDADE_COL_DATA + ", " +
                BancoDeDados.ATIVIDADE_COL_HORA + ", " +
                BancoDeDados.ATIVIDADE_COL_DETALHES + " " +
                "FROM Atividades WHERE " +
                BancoDeDados.COL_ID_VIAGEM + "=" + idViagem + " " +
                "ORDER BY rowid";
        return this.bancoDeDados.rawQuery(query, null);
    }
}
