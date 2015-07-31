package br.edu.ufam.icomp.triplog.util;

import android.content.Context;

import br.edu.ufam.icomp.triplog.R;

/**
 * Created by micael on 28/07/15.
 */
public class Opcoes {

    private static Context context;
    public static String[] menu_opcoes_lista;
    public static String[] categorias_gastos_lista;
    public static String[] modo_viagem;

    private static String[] tipo_viagem;

    private static int idViagem;

    public Opcoes(Context context) {
        this.context = context;

        menu_opcoes_lista = context.getResources().getStringArray(R.array.opcoes_array);
        categorias_gastos_lista = context.getResources().getStringArray(R.array.categorias_array);
        tipo_viagem = context.getResources().getStringArray(R.array.tipo_viagem_array);
        modo_viagem = context.getResources().getStringArray(R.array.modo_viagem_array);
    }

    public static String getTipoViagem(int i) {
        return tipo_viagem[i-1];
    }

    public static int getIdViagem() {
        return idViagem;
    }

    public static void setIdViagem(int idViagem) {
        Opcoes.idViagem = idViagem;
    }
}
