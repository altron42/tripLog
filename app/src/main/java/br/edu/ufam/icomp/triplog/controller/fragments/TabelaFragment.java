package br.edu.ufam.icomp.triplog.controller.fragments;

import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.ParseException;

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.controller.PrincipalViagemActivity;
import br.edu.ufam.icomp.triplog.dao.DespesaDAO;
import br.edu.ufam.icomp.triplog.util.BancoDeDados;
import br.edu.ufam.icomp.triplog.util.DateHandler;


public class TabelaFragment extends ListFragment {
    private DespesaDAO despesaDAO;
    private Cursor despesas_cursor;
    private SimpleCursorAdapter adapter_despesas;

    @Override
    public void onStart() {
        super.onStart();
        setEmptyText(Html.fromHtml("<br><br><p>Está lista está vazia</p>"));
        Log.i(null,"TabelaFragmente RODANDO!");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.despesaDAO = new DespesaDAO(getActivity());

        String[] colunasDe = {BancoDeDados.DESPESA_COL_DATA,BancoDeDados.DESPESA_COL_NOME, BancoDeDados.DESPESA_COL_VALOR};
        int[] colunasPara = {R.id.tv_gastos_data, R.id.tv_gastos_nome, R.id.tv_gastos_valor};

        adapter_despesas = new SimpleCursorAdapter(getActivity(),
                R.layout.lista_item_despesas,
                despesas_cursor,
                colunasDe,
                colunasPara,
                0);

        setListAdapter(adapter_despesas);

        adapter_despesas.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (columnIndex == cursor.getColumnIndex(BancoDeDados.DESPESA_COL_DATA)) {
                    try {
                        String data = DateHandler.dateFormatShort.format(DateHandler.sdf.parse(cursor.getString(columnIndex)));
                        ((TextView) view).setText(data);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                if (columnIndex == cursor.getColumnIndex(BancoDeDados.DESPESA_COL_VALOR)) {
                    ((TextView) view).setText(String.valueOf(cursor.getDouble(columnIndex)));
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
        Cursor novo_cursor = despesaDAO.getDespesas(PrincipalViagemActivity.id_viagem_selecionada);
        despesas_cursor = adapter_despesas.swapCursor(novo_cursor);
        adapter_despesas.notifyDataSetChanged();
        if (adapter_despesas.isEmpty()) {
            Log.i(null, "LISTA VAZIA");
        }
    }
}
