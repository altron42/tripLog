package br.edu.ufam.icomp.triplog.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by micael on 26/07/15.
 */
public class BancoDeDados extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "TripLogApp.db";

    /**
     * Column names for table Viagens
     */
    public static final String VIAGEM_COL_NOME = "nome";
    public static final String VIAGEM_COL_COMECO = "data_comeco";
    public static final String VIAGEM_COL_FIM = "data_fim";
    public static final String VIAGEM_COL_DETALHES = "detalhes";
    public static final String VIAGEM_COL_TIPO = "tipo";
    public static final String VIAGEM_COL_ICONE = "icone";

    /**
     * Column names for table Despesas
     */
    public static final String DESPESA_COL_NOME = "nome";
    public static final String DESPESA_COL_DATA = "data";
    public static final String DESPESA_COL_VALOR = "valor";
    public static final String DESPESA_COL_CATEGORIA = "categoria";
    public static final String DESPESA_COL_PAGOCOM = "pago_com";
    public static final String DESPESA_COL_COMENTARIO = "comentario";

    /**
     * Table Despesas
     */
    private static final String SQL_CREATE_TABLE_DESPESAS = "CREATE TABLE Despesas(" +
            DESPESA_COL_NOME + " TEXT NOT NULL, "  +
            DESPESA_COL_DATA + " TEXT NOT NULL, " +
            DESPESA_COL_VALOR + " INT NOT NULL, " +
            DESPESA_COL_CATEGORIA + " DOUBLE NOT NULL, " +
            DESPESA_COL_PAGOCOM + " INT, " +
            DESPESA_COL_COMENTARIO + " TEXT)";

    /**
     * Table Viagens
     */
    private static final String SQL_CREATE_TABLE_VIAGENS = "CREATE TABLE Viagens(" +
            VIAGEM_COL_NOME + " TEXT NOT NULL, " +
            VIAGEM_COL_COMECO + " TEXT NOT NULL, " +
            VIAGEM_COL_FIM + " TEXT NOT NULL, " +
            VIAGEM_COL_DETALHES + " TEXT, " +
            VIAGEM_COL_TIPO + " INT NOT NULL, " +
            VIAGEM_COL_ICONE + " TEXT )";

    private static final String SQL_POPULATE_TABLE_VIAGENS = "INSERT INTO Viagens VALUES (" +
            "'Férias de Verão', " +
            "'26/07/2015', " +
            "'5/08/2015', " +
            "null, " +
            "1, " +
            "null)";

    private static final String SQL_DELETE_TABLES = "DROP TABLE IF EXISTS Viagens";

    public BancoDeDados(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_VIAGENS);
        db.execSQL(SQL_POPULATE_TABLE_VIAGENS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLES);
        onCreate(db);
    }
}
