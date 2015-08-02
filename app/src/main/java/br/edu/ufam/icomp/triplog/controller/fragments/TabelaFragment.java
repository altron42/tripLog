package br.edu.ufam.icomp.triplog.controller.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.controller.NovaDespesaActivity;
import br.edu.ufam.icomp.triplog.controller.PrincipalViagemActivity;
import br.edu.ufam.icomp.triplog.dao.CarteiraDAO;
import br.edu.ufam.icomp.triplog.dao.DespesaDAO;
import br.edu.ufam.icomp.triplog.model.Carteira;
import br.edu.ufam.icomp.triplog.model.Despesa;
import br.edu.ufam.icomp.triplog.util.BancoDeDados;
import br.edu.ufam.icomp.triplog.util.DateHandler;
import br.edu.ufam.icomp.triplog.util.Opcoes;


public class TabelaFragment extends ListFragment {
    private DespesaDAO despesaDAO;
    private Cursor despesas_cursor;
    private SimpleCursorAdapter adapter_despesas;

    private ActionMode mActionMode;

    private long id_selecionado;
    private int position_selecionado;

    private Despesa despesa;

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
                    ((TextView) view).setText(String.format("%.2f", cursor.getDouble(columnIndex)));
                    return true;
                }
                return false;
            }
        });

        AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (mActionMode != null) {
                    return false;
                }
                id_selecionado = id;
                position_selecionado = position;
                getListView().setItemChecked(position,true);
                mActionMode = getActivity().startActionMode(mAntionModeCallback);
                return true;
            }
        };

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        getListView().setOnItemLongClickListener(longClickListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        atualizarAdapter();
        getListView().setItemChecked(position_selecionado, false);
        if (mActionMode != null) mActionMode.finish();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        id_selecionado = id;
        position_selecionado = position;
        if (mActionMode != null) {
            return;
        }

        Intent intent = new Intent(getActivity(), NovaDespesaActivity.class);
        despesa = despesaDAO.getDespesa((int) id_selecionado);
        despesa.setId((int)id_selecionado);
        intent.putExtra(Opcoes.isEditTag,true);
        intent.putExtra(Opcoes.despesaTag,despesa);
        startActivity(intent);
    }

    private void atualizarAdapter() {
        Cursor novo_cursor = despesaDAO.getDespesas(Opcoes.getIdViagem());
        despesas_cursor = adapter_despesas.swapCursor(novo_cursor);
        adapter_despesas.notifyDataSetChanged();
        if (adapter_despesas.isEmpty()) {
            Log.i(null, "LISTA VAZIA");
        }
    }

    private ActionMode.Callback mAntionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_cab_despesas,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.cab_item_delete:
                    CarteiraDAO carteiraDAO = new CarteiraDAO(getActivity());
                    despesa = despesaDAO.getDespesa((int) id_selecionado);
                    Carteira carteira = carteiraDAO.getCarteira(despesa.getPagoCom());
                    if (carteira != null) {
                        carteira.acrescentarValor(despesa.getValor());
                        carteiraDAO.editCarteira(carteira);
                    }
                    if (despesaDAO.delDespesa((int) id_selecionado)) {
                        Toast.makeText(getActivity(), "Apagado " + id_selecionado, Toast.LENGTH_SHORT).show();
                        atualizarAdapter();
                    } else {
                        Toast.makeText(getActivity(), "Erro ao apagar " + id_selecionado, Toast.LENGTH_SHORT).show();
                    }
                    mode.finish();
                    return true;
                case R.id.cab_item_edit:
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            getListView().setItemChecked(position_selecionado,false);
        }
    };
}
