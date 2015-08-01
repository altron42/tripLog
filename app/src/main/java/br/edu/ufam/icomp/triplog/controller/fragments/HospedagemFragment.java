package br.edu.ufam.icomp.triplog.controller.fragments;


import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.ParseException;

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.dao.HospedagemDAO;
import br.edu.ufam.icomp.triplog.util.BancoDeDados;
import br.edu.ufam.icomp.triplog.util.DateHandler;
import br.edu.ufam.icomp.triplog.util.Opcoes;

/**
 * A simple {@link Fragment} subclass.
 */
public class HospedagemFragment extends ListFragment {

    HospedagemDAO hospedagemDAO;
    Cursor cursor;
    SimpleCursorAdapter cursorAdapter;

    @Override
    public void onStart() {
        super.onStart();
        setEmptyText("Esta lista está vazia");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hospedagemDAO = new HospedagemDAO(getActivity());

        String[] colunasDe = {BancoDeDados.HOSPEDAGEM_COL_NOME, BancoDeDados.HOSPEDAGEM_COL_DATA_CHECKIN,
        BancoDeDados.HOSPEDAGEM_COL_DATA_CHECKOUT};
        int[] colunasPara = {R.id.textView5,R.id.textView6,R.id.textView7};
        cursorAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.lista_hospedagem,
                cursor,
                colunasDe,
                colunasPara,
                0);
        setListAdapter(cursorAdapter);

        cursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (columnIndex == cursor.getColumnIndex(BancoDeDados.HOSPEDAGEM_COL_DATA_CHECKIN)) {
                    try {
                        String s = DateHandler.dateFormatDefault.format(DateHandler.sdf.parse(cursor.getString(cursor.getColumnIndex(BancoDeDados.HOSPEDAGEM_COL_DATA_CHECKIN)))) + " " +
                                DateHandler.timeFormat.format(DateHandler.sdf_time.parse(cursor.getString(cursor.getColumnIndex(BancoDeDados.HOSPEDAGEM_COL_HORA_CHECKIN))));
                        ((TextView) view).setText(getResources().getString(R.string.tv_check_in) + ": " + s);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                if (columnIndex == cursor.getColumnIndex(BancoDeDados.HOSPEDAGEM_COL_DATA_CHECKOUT)) {
                    try {
                        String s = DateHandler.dateFormatDefault.format(DateHandler.sdf.parse(cursor.getString(cursor.getColumnIndex(BancoDeDados.HOSPEDAGEM_COL_DATA_CHECKOUT)))) + " " +
                                DateHandler.timeFormat.format(DateHandler.sdf_time.parse(cursor.getString(cursor.getColumnIndex(BancoDeDados.HOSPEDAGEM_COL_HORA_CHECKOUT))));
                        ((TextView) view).setText(getResources().getString(R.string.tv_check_out) + ": " + s);
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

    private void atualizarAdapter() {
        Cursor novo_cursor = hospedagemDAO.getHospedagens(Opcoes.getIdViagem());
        cursor = cursorAdapter.swapCursor(novo_cursor);
        cursorAdapter.notifyDataSetChanged();
        if (cursorAdapter.isEmpty()) {
            Log.i(null, "LISTA VAZIA");
        }
    }
}
