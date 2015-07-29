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

        }
        cursor.close();
        return despesa;
    }

    public boolean addDespesa(Despesa despesa) {
        try {
            String query = "INSERT INTO Despesas (" +
                    BancoDeDados.VIAGEM_COL_NOME + ", " +
                    BancoDeDados.VIAGEM_COL_COMECO + ", " +
                    BancoDeDados.VIAGEM_COL_FIM + ", " +
                    BancoDeDados.VIAGEM_COL_DETALHES + ", " +
                    BancoDeDados.VIAGEM_COL_TIPO + ", " +
                    BancoDeDados.VIAGEM_COL_ICONE + ") VALUES ('" +
                    despesa.getNome() + "', '" +
                    despesa.getComeco() + "', '" +
                    despesa.getFim() + "', '" +
                    despesa.getDetalhes() + "', " +
                    despesa.getTipo() + ", '" +
                    despesa.getIcone() + "')";

            this.bancoDeDados.execSQL(query);
            return true;
        } catch (SQLException e) {
            Log.e("TripLogDB", e.getMessage());
            return false;
        }
    }

    public Cursor getDespesas() {
        return this.bancoDeDados.rawQuery("SELECT " +
                "rowid AS _id, " +
                BancoDeDados.VIAGEM_COL_NOME + ", " +
                BancoDeDados.VIAGEM_COL_COMECO + ", " +
                BancoDeDados.VIAGEM_COL_FIM + ", " +
                BancoDeDados.VIAGEM_COL_ICONE + " " +
                "FROM Viagens ORDER BY rowid DESC", null);
    }
}
