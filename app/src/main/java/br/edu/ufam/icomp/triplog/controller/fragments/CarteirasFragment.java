package br.edu.ufam.icomp.triplog.controller.fragments;


import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
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
import br.edu.ufam.icomp.triplog.controller.NovaCarteiraActivity;
import br.edu.ufam.icomp.triplog.controller.PrincipalViagemActivity;
import br.edu.ufam.icomp.triplog.dao.CarteiraDAO;
import br.edu.ufam.icomp.triplog.dao.DespesaDAO;
import br.edu.ufam.icomp.triplog.model.Carteira;
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

    private ActionMode mActionMode;

    private long id_selecionado;
    private int position_selecionado;

    private Carteira carteira;

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
                    ((TextView) view).setText("Disponível: R$ " + String.format("%.2f", cursor.getDouble(columnIndex)));
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
    }

    private void atualizarAdapter() {
        Cursor novo_cursor = carteiraDAO.getCarteiras(Opcoes.getIdViagem());
        carteiras_cursor = adapter.swapCursor(novo_cursor);
        adapter.notifyDataSetChanged();
        if (adapter.isEmpty()) {
            Log.i(null, "LISTA VAZIA");
        }
    }

    private ActionMode.Callback mAntionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_cab_lista_viagens,menu);
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
                    if (carteiraDAO.delCarteira((int)id_selecionado)) {
                        Toast.makeText(getActivity(), "Apagado " + id_selecionado, Toast.LENGTH_SHORT).show();
                        atualizarAdapter();
                    } else {
                        Toast.makeText(getActivity(), "Erro ao apagar " + id_selecionado, Toast.LENGTH_SHORT).show();
                    }
                    mode.finish();
                    return true;
                case R.id.cab_item_edit:
                    Intent intent = new Intent(getActivity(), NovaCarteiraActivity.class);
                    carteira = carteiraDAO.getCarteira((int)id_selecionado);
                    carteira.setId((int)id_selecionado);
                    intent.putExtra(Opcoes.isEditTag, true);
                    intent.putExtra(Opcoes.carteiraTag,carteira);
                    startActivity(intent);
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
