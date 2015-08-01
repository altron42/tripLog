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

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.dao.HospedagemDAO;
import br.edu.ufam.icomp.triplog.util.BancoDeDados;
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
