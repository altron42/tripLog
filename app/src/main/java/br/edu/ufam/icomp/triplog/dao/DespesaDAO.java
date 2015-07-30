package br.edu.ufam.icomp.triplog.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.edu.ufam.icomp.triplog.model.Despesa;
import br.edu.ufam.icomp.triplog.util.BancoDeDados;

/**
 * Created by micael on 29/07/15.
 */
public class DespesaDAO {
    private SQLiteDatabase bancoDeDados;

    public DespesaDAO (Context context) {
        this.bancoDeDados = (new BancoDeDados(context).getWritableDatabase());
    }

    public Despesa getDespesa(int id) {
        Despesa despesa = null;
        String query = "SELECT * FROM Despesas WHERE rowid=" + id;
        Cursor cursor = this.bancoDeDados.rawQuery(query, null);
        if (cursor.moveToNext()) {
            despesa = new Despesa();
            despesa.setId(id);
            despesa.setNome(cursor.getString(cursor.getColumnIndex(BancoDeDados.DESPESA_COL_NOME)));
            despesa.setData(cursor.getString(cursor.getColumnIndex(BancoDeDados.DESPESA_COL_DATA)));
            despesa.setValor(cursor.getDouble(cursor.getColumnIndex(BancoDeDados.DESPESA_COL_VALOR)));
            despesa.setCategoria(cursor.getInt(cursor.getColumnIndex(BancoDeDados.DESPESA_COL_CATEGORIA)));
            despesa.setPagoCom(cursor.getInt(cursor.getColumnIndex(BancoDeDados.DESPESA_COL_PAGOCOM)));
            despesa.setComentario(cursor.getString(cursor.getColumnIndex(BancoDeDados.DESPESA_COL_COMENTARIO)));
            despesa.setIdViagem(cursor.getInt(cursor.getColumnIndex(BancoDeDados.COL_ID_VIAGEM)));
        }
        cursor.close();
        return despesa;
    }

    public boolean addDespesa(Despesa despesa) {
        try {
            String query = "INSERT INTO Despesas (" +
                    BancoDeDados.DESPESA_COL_NOME + ", " +
                    BancoDeDados.DESPESA_COL_DATA + ", " +
                    BancoDeDados.DESPESA_COL_VALOR + ", " +
                    BancoDeDados.DESPESA_COL_CATEGORIA + ", " +
                    BancoDeDados.DESPESA_COL_PAGOCOM + ", " +
                    BancoDeDados.COL_ID_VIAGEM + ", " +
                    BancoDeDados.DESPESA_COL_COMENTARIO + ") VALUES ('" +
                    despesa.getNome() + "', '" +
                    despesa.getData() + "', " +
                    despesa.getValor() + ", " +
                    despesa.getCategoria() + ", " +
                    despesa.getPagoCom() + ", " +
                    despesa.getIdViagem() + ", '" +
                    despesa.getComentario() + "')";
            Log.i(null, despesa.getNome() + " adicionada");
            this.bancoDeDados.execSQL(query);
            return true;
        } catch (SQLException e) {
            Log.e("TripLogDB", e.getMessage());
            return false;
        }
    }

    public Cursor getDespesas(int idViagem) {
        return this.bancoDeDados.rawQuery("SELECT " +
                "rowid AS _id, " +
                BancoDeDados.DESPESA_COL_NOME + ", " +
                BancoDeDados.DESPESA_COL_DATA + ", " +
                BancoDeDados.DESPESA_COL_VALOR + ", " +
                BancoDeDados.DESPESA_COL_CATEGORIA + " " +
                "FROM Despesas WHERE " +
                BancoDeDados.COL_ID_VIAGEM + "=" + idViagem + " " +
                "ORDER BY rowid DESC", null);
    }
}
