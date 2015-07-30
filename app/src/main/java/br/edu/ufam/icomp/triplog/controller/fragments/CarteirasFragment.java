package br.edu.ufam.icomp.triplog.controller.fragments;


import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.ParseException;

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.controller.PrincipalViagemActivity;
import br.edu.ufam.icomp.triplog.dao.CarteiraDAO;
import br.edu.ufam.icomp.triplog.util.BancoDeDados;
import br.edu.ufam.icomp.triplog.util.DateHandler;
import br.edu.ufam.icomp.triplog.util.Opcoes;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarteirasFragment extends ListFragment {
    private CarteiraDAO carteiraDAO;
    private Cursor carteiras_cursor;
    private SimpleCursorAdapter adapter;

    @Override
    public void onStart() {
        super.onStart();
        setEmptyText(Html.fromHtml("<br><br><p>Está lista está vazia</p>"));
        Log.i(null, "TabelaFragmente RODANDO!");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.carteiraDAO = new CarteiraDAO(getActivity());

        String[] colunasDe = {BancoDeDados.CARTEIRA_COL_NOME,BancoDeDados.CARTEIRA_COL_VALOR};
        int[] colunasPara = {R.id.tv_nome_carteira, R.id.tv_valor_disponivel_carteira};

        adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.lista_item_carteira,
                carteiras_cursor,
                colunasDe,
                colunasPara,
                0);

        setListAdapter(adapter);

        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (columnIndex == cursor.getColumnIndex(BancoDeDados.CARTEIRA_COL_VALOR)) {
                    ((TextView) view).setText("Disponível: R$ " + String.valueOf(cursor.getDouble(columnIndex)));
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
        Cursor novo_cursor = carteiraDAO.getCarteiras(Opcoes.getIdViagem());
        carteiras_cursor = adapter.swapCursor(novo_cursor);
        adapter.notifyDataSetChanged();
        if (adapter.isEmpty()) {
            Log.i(null, "LISTA VAZIA");
        }
    }
}
