package br.edu.ufam.icomp.triplog.controller.fragments;


import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.ParseException;

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.dao.ModoViagemDAO;
import br.edu.ufam.icomp.triplog.util.BancoDeDados;
import br.edu.ufam.icomp.triplog.util.DateHandler;
import br.edu.ufam.icomp.triplog.util.Opcoes;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModoViagemFragment extends ListFragment {

    ModoViagemDAO modoViagemDAO;
    Cursor cursor;
    SimpleCursorAdapter simpleCursorAdapter;

    @Override
    public void onStart() {
        super.onStart();
        setEmptyText("Esta lista esta vazia");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        modoViagemDAO = new ModoViagemDAO(getActivity());

        String[] colunasDe = {BancoDeDados.MODOVIAGEM_COL_TIPO_MODO,BancoDeDados.MODOVIAGEM_COL_PARTIDA,
        BancoDeDados.MODOVIAGEM_COL_CHEGADA, BancoDeDados.MODOVIAGEM_COL_PARTIDA_DATA, BancoDeDados.MODOVIAGEM_COL_PARTIDA_HORA,
        BancoDeDados.MODOVIAGEM_COL_CHEGADA_DATA, BancoDeDados.MODOVIAGEM_COL_CHEGADA_HORA};
        int[] colunasPara = {R.id.tv_modo_detalhe,R.id.tv_modo_partida,R.id.tv_modo_chegada};

        simpleCursorAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.lista_modo_item,
                cursor,
                colunasDe,
                colunasPara,
                0);
        setListAdapter(simpleCursorAdapter);

        simpleCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (columnIndex == cursor.getColumnIndex(BancoDeDados.MODOVIAGEM_COL_TIPO_MODO)) {
                    String s = Opcoes.modo_viagem[cursor.getInt(columnIndex)] + ": " +
                            cursor.getString(cursor.getColumnIndex(BancoDeDados.MODOVIAGEM_COL_PARTIDA)) + " -> " +
                            cursor.getString(cursor.getColumnIndex(BancoDeDados.MODOVIAGEM_COL_CHEGADA));
                    ((TextView) view).setText(s);
                    return true;
                }
                if (columnIndex == cursor.getColumnIndex(BancoDeDados.MODOVIAGEM_COL_PARTIDA)) {
                    try {
                        String s = DateHandler.dateFormatDefault.format(DateHandler.sdf.parse(cursor.getString(cursor.getColumnIndex(BancoDeDados.MODOVIAGEM_COL_PARTIDA_DATA)))) + " " +
                                DateHandler.timeFormat.format(DateHandler.sdf_time.parse(cursor.getString(cursor.getColumnIndex(BancoDeDados.MODOVIAGEM_COL_PARTIDA_HORA))));
                        ((TextView) view).setText(getResources().getString(R.string.tv_partida) + ": " + s);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                if (columnIndex == cursor.getColumnIndex(BancoDeDados.MODOVIAGEM_COL_CHEGADA)) {
                    try {
                        String s = DateHandler.dateFormatDefault.format(DateHandler.sdf.parse(cursor.getString(cursor.getColumnIndex(BancoDeDados.MODOVIAGEM_COL_CHEGADA_DATA)))) + " " +
                                DateHandler.timeFormat.format(DateHandler.sdf_time.parse(cursor.getString(cursor.getColumnIndex(BancoDeDados.MODOVIAGEM_COL_CHEGADA_HORA))));
                        ((TextView) view).setText(getResources().getString(R.string.tv_chegada) + ": " + s);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        atualizarAdapter();
    }

    public void atualizarAdapter() {
        Cursor novo_cursor = modoViagemDAO.getModoViagemLista(Opcoes.getIdViagem());
        cursor = simpleCursorAdapter.swapCursor(novo_cursor);
        simpleCursorAdapter.notifyDataSetChanged();
    }
}

