package br.edu.ufam.icomp.triplog.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by micael on 26/07/15.
 */
public class BancoDeDados extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "TripLogApp.db";

    private static final String COLTYPE_TEXT = " TEXT";
    private static final String COLTYPE_INT = " INT";
    private static final String COLTYPE_DOUBLE = " DOUBLE";
    private static final String COLTYPE_NOT_NULL = " NOT NULL";



    public static final String COL_ID_VIAGEM = "id_viagem";

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
     * Column name for table Carteiras
     */
    public static final String CARTEIRA_COL_NOME = "nome";
    public static final String CARTEIRA_COL_VALOR = "valor_disponivel";

    /**
     * Column names for table ModoViagem
     */
    public static final String MODOVIAGEM_COL_NOME = "detalhes";
    public static final String MODOVIAGEM_COL_PARTIDA = "partida";
    public static final String MODOVIAGEM_COL_PARTIDA_DATA = "partida_data";
    public static final String MODOVIAGEM_COL_PARTIDA_HORA = "partida_hora";
    public static final String MODOVIAGEM_COL_CHEGADA = "chegada";
    public static final String MODOVIAGEM_COL_CHEGADA_DATA = "chegada_data";
    public static final String MODOVIAGEM_COL_CHEGADA_HORA = "chegada_hora";
    public static final String MODOVIAGEM_COL_COMENTARIO = "comentario";
    public static final String MODOVIAGEM_COL_TIPO_MODO = "tipo_modo";

    /**
     * Column names for table Hospedagens
     */
    public static final String HOSPEDAGEM_COL_NOME = "nome";
    public static final String HOSPEDAGEM_COL_DATA_CHECKIN = "checkin_data";
    public static final String HOSPEDAGEM_COL_HORA_CHECKIN = "checkin_hora";
    public static final String HOSPEDAGEM_COL_DATA_CHECKOUT = "checkout_data";
    public static final String HOSPEDAGEM_COL_HORA_CHECKOUT = "checkout_hora";
    public static final String HOSPEDAGEM_COL_COMENTARIO = "comentario";

    /**
     * Table Viagens
     */
    private static final String SQL_CREATE_TABLE_VIAGENS = "CREATE TABLE IF NOT EXISTS Viagens(" +
            VIAGEM_COL_NOME + COLTYPE_TEXT + COLTYPE_NOT_NULL + ", " +
            VIAGEM_COL_COMECO + COLTYPE_TEXT + COLTYPE_NOT_NULL + ", " +
            VIAGEM_COL_FIM + COLTYPE_TEXT + COLTYPE_NOT_NULL + ", " +
            VIAGEM_COL_DETALHES + COLTYPE_TEXT +", " +
            VIAGEM_COL_TIPO + COLTYPE_INT + COLTYPE_NOT_NULL + ", " +
            VIAGEM_COL_ICONE + COLTYPE_TEXT + ")";

    /**
     * Table Despesas
     */
    private static final String SQL_CREATE_TABLE_DESPESAS = "CREATE TABLE IF NOT EXISTS Despesas(" +
            DESPESA_COL_NOME + COLTYPE_TEXT + COLTYPE_NOT_NULL + ", " +
            DESPESA_COL_DATA + COLTYPE_TEXT + COLTYPE_NOT_NULL + ", " +
            DESPESA_COL_VALOR + COLTYPE_DOUBLE + COLTYPE_NOT_NULL + ", " +
            DESPESA_COL_CATEGORIA + COLTYPE_INT + COLTYPE_NOT_NULL + ", " +
            DESPESA_COL_PAGOCOM + COLTYPE_INT + COLTYPE_NOT_NULL + ", " +
            DESPESA_COL_COMENTARIO + COLTYPE_TEXT + ", " +
            COL_ID_VIAGEM + COLTYPE_INT + COLTYPE_NOT_NULL + ")";

    /**
     * Table Carteiras
     */
    private static final String SQL_CREATE_TABLE_CARTEIRAS = "CREATE TABLE IF NOT EXISTS Carteiras(" +
            CARTEIRA_COL_NOME + COLTYPE_TEXT + COLTYPE_NOT_NULL + ", " +
            CARTEIRA_COL_VALOR + COLTYPE_DOUBLE + COLTYPE_NOT_NULL + ", " +
            COL_ID_VIAGEM + COLTYPE_INT + COLTYPE_NOT_NULL + ")";

    /**
     * Table ModoViagem
     */
    private static final String SQL_CREATE_TABLE_MODOVIAGEM = "CREATE TABLE IF NOT EXISTS ModoViagem(" +
            MODOVIAGEM_COL_NOME + COLTYPE_TEXT + COLTYPE_NOT_NULL + ", " +
            MODOVIAGEM_COL_PARTIDA + COLTYPE_TEXT + COLTYPE_NOT_NULL + ", " +
            MODOVIAGEM_COL_PARTIDA_DATA + COLTYPE_TEXT + COLTYPE_NOT_NULL + ", " +
            MODOVIAGEM_COL_PARTIDA_HORA + COLTYPE_TEXT + COLTYPE_NOT_NULL + ", " +
            MODOVIAGEM_COL_CHEGADA + COLTYPE_TEXT + COLTYPE_NOT_NULL + ", " +
            MODOVIAGEM_COL_CHEGADA_DATA + COLTYPE_TEXT + COLTYPE_NOT_NULL + ", " +
            MODOVIAGEM_COL_CHEGADA_HORA + COLTYPE_TEXT + COLTYPE_NOT_NULL + ", " +
            MODOVIAGEM_COL_COMENTARIO + COLTYPE_TEXT + ", " +
            MODOVIAGEM_COL_TIPO_MODO + COLTYPE_INT + ", " +
            COL_ID_VIAGEM + COLTYPE_INT + COLTYPE_NOT_NULL + ")";

    /**
     * Table Hospedagem
     */

    private static final String SQL_CREATE_TABLE_HOSPEDAGEM = "CREATE TABLE IF NOT EXISTS Hospedagens(" +
            HOSPEDAGEM_COL_NOME + COLTYPE_TEXT + ", " +
            HOSPEDAGEM_COL_DATA_CHECKIN + COLTYPE_TEXT + COLTYPE_NOT_NULL + ", " +
            HOSPEDAGEM_COL_HORA_CHECKIN + COLTYPE_TEXT + COLTYPE_NOT_NULL + ", " +
            HOSPEDAGEM_COL_DATA_CHECKOUT + COLTYPE_TEXT + COLTYPE_NOT_NULL + ", " +
            HOSPEDAGEM_COL_HORA_CHECKOUT + COLTYPE_TEXT + COLTYPE_NOT_NULL + ", " +
            HOSPEDAGEM_COL_COMENTARIO + COLTYPE_TEXT + ", " +
            COL_ID_VIAGEM + COLTYPE_INT + COLTYPE_NOT_NULL + ")";

    private static final String SQL_POPULATE_TABLE_VIAGENS = "INSERT INTO Viagens VALUES (" +
            "'Férias de Verão', " +
            "'26/07/2015', " +
            "'05/08/2015', " +
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
        db.execSQL(SQL_CREATE_TABLE_CARTEIRAS);
        db.execSQL(SQL_CREATE_TABLE_DESPESAS);
        db.execSQL(SQL_CREATE_TABLE_MODOVIAGEM);
        db.execSQL(SQL_CREATE_TABLE_HOSPEDAGEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(SQL_DELETE_TABLES);
        onCreate(db);
    }
}
