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

    public Opcoes(Context context) {
        this.context = context;

        menu_opcoes_lista = context.getResources().getStringArray(R.array.opcoes_array);
        categorias_gastos_lista = context.getResources().getStringArray(R.array.categorias_array);
    }
}
