package br.edu.ufam.icomp.triplog.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.edu.ufam.icomp.triplog.model.Carteira;
import br.edu.ufam.icomp.triplog.util.BancoDeDados;

/**
 * Created by micael on 29/07/15.
 */
public class CarteiraDAO {

    private SQLiteDatabase bancoDeDados;

    public CarteiraDAO (Context context) {
        this.bancoDeDados = (new BancoDeDados(context).getWritableDatabase());
    }

    public CarteiraDAO (SQLiteDatabase database) {
        this.bancoDeDados = database;
    }

    public Carteira getCarteira(int id) {
        Carteira carteira = null;
        String query = "SELECT * FROM Carteiras WHERE rowid=" + id;
        Cursor cursor = this.bancoDeDados.rawQuery(query, null);
        if (cursor.moveToNext()) {
            carteira = new Carteira();
            carteira.setId(id);
            carteira.setNome(cursor.getString(cursor.getColumnIndex(BancoDeDados.CARTEIRA_COL_NOME)));
            carteira.setValorDisponivel(cursor.getDouble(cursor.getColumnIndex(BancoDeDados.CARTEIRA_COL_VALOR)));
            carteira.setIdViagem(cursor.getInt(cursor.getColumnIndex(BancoDeDados.COL_ID_VIAGEM)));
        }
        cursor.close();
        return carteira;
    }

    public boolean addCarteira(Carteira carteira) {
        try {
            String query = "INSERT INTO Carteiras (" +
                    BancoDeDados.CARTEIRA_COL_NOME + ", " +
                    BancoDeDados.CARTEIRA_COL_VALOR + ", " +
                    BancoDeDados.COL_ID_VIAGEM + ") VALUES ('" +
                    carteira.getNome() + "', " +
                    carteira.getValorDisponivel() + ", " +
                    carteira.getIdViagem() + ")";
            this.bancoDeDados.execSQL(query);
            return true;
        } catch (SQLException e) {
            Log.e("TripLogDB", e.getMessage());
            return false;
        }
    }

    public boolean editCarteira(Carteira carteira) {
        try {
            String query = "UPDATE Carteiras SET " +
                    BancoDeDados.CARTEIRA_COL_NOME + "='" + carteira.getNome() + "', " +
                    BancoDeDados.CARTEIRA_COL_VALOR + "=" + carteira.getValorDisponivel() + " " +
                    "WHERE rowid=" + carteira.getId();
            this.bancoDeDados.execSQL(query);
            return true;
        } catch (SQLException e) {
            Log.e("TripLogDB", e.getMessage());
            return false;
        }
    }

    public boolean delCarteira(int id) {
        try {
            String query = "DELETE FROM Carteiras WHERE rowid=" + id;
            this.bancoDeDados.execSQL(query);
            return true;
        } catch (SQLException e) {
            Log.e("TripLogDB", e.getMessage());
            return false;
        }
    }

    public boolean delCarteiras(int idViagem) {
        try {
            Log.i(null, "APAGANDO carteiras" + idViagem);
            String query = "DELETE FROM Carteiras WHERE " +
                    BancoDeDados.COL_ID_VIAGEM + "=" + idViagem;
            this.bancoDeDados.execSQL(query);
            return true;
        } catch (SQLException e) {
            Log.e("TripLogDB", e.getMessage());
            return false;
        }
    }

    public Cursor getCarteiras(int idViagem) {
        String query = "SELECT " +
                "rowid AS _id, " +
                BancoDeDados.CARTEIRA_COL_NOME + ", " +
                BancoDeDados.CARTEIRA_COL_VALOR + " " +
                "FROM Carteiras WHERE " +
                BancoDeDados.COL_ID_VIAGEM + "=" + idViagem + " " +
                "ORDER BY rowid";
        return this.bancoDeDados.rawQuery(query, null);
    }

    public double getOrcamentoDisponivel(int idViagem) {
        double orcamentoTotal = 0;
        Cursor cursor = getCarteiras(idViagem);
        if (cursor != null)
            while (cursor.moveToNext()) {
                orcamentoTotal += cursor.getDouble(cursor.getColumnIndex(BancoDeDados.CARTEIRA_COL_VALOR));
            }

        return orcamentoTotal;
    }
}
