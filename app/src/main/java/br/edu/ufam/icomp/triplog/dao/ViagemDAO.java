package br.edu.ufam.icomp.triplog.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.edu.ufam.icomp.triplog.model.Viagem;
import br.edu.ufam.icomp.triplog.util.BancoDeDados;

/**
 * Created by micael on 26/07/15.
 */
public class ViagemDAO {
    private SQLiteDatabase bancoDeDados;

    public ViagemDAO(Context context) {
        this.bancoDeDados = (new BancoDeDados(context).getWritableDatabase());
    }

    public Viagem getViagem(){
        return new Viagem();
    }

    public boolean addViagem(Viagem viagem) {
        try {
            String query = "INSERT INTO Viagens (" +
                    BancoDeDados.VIAGEM_COL_NOME + ", " +
                    BancoDeDados.VIAGEM_COL_COMECO + ", " +
                    BancoDeDados.VIAGEM_COL_FIM + ", " +
                    BancoDeDados.VIAGEM_COL_DETALHES + ", " +
                    BancoDeDados.VIAGEM_COL_TIPO + ", " +
                    BancoDeDados.VIAGEM_COL_ICONE + ") VALUES ('" +
                    viagem.getNome() + "', '" +
                    viagem.getComeco() + "', '" +
                    viagem.getFim() + "', '" +
                    viagem.getDetalhes() + "', " +
                    viagem.getTipo() + ", '" +
                    viagem.getIcone() + "')";

            this.bancoDeDados.execSQL(query);
            return true;
        } catch (SQLException e) {
            Log.e("TripLogDB", e.getMessage());
            return false;
        }

    }

    public Cursor getViagens() {
        return this.bancoDeDados.rawQuery("SELECT " +
                "rowid AS _id, " +
                BancoDeDados.VIAGEM_COL_NOME + ", " +
                BancoDeDados.VIAGEM_COL_COMECO + ", " +
                BancoDeDados.VIAGEM_COL_FIM + ", " +
                BancoDeDados.VIAGEM_COL_ICONE + " " +
                "FROM Viagens ORDER BY rowid", null);
    }
}
