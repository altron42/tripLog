package br.edu.ufam.icomp.triplog.controller.fragments;


import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.controller.NovoModoViagemActivity;
import br.edu.ufam.icomp.triplog.dao.ModoViagemDAO;
import br.edu.ufam.icomp.triplog.model.ModoViagem;
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

    private ActionMode mActionMode;

    private long id_selecionado;
    private int position_selecionado;

    private ModoViagem modoViagem;

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

    public void atualizarAdapter() {
        Cursor novo_cursor = modoViagemDAO.getModoViagemLista(Opcoes.getIdViagem());
        cursor = simpleCursorAdapter.swapCursor(novo_cursor);
        simpleCursorAdapter.notifyDataSetChanged();
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
                    if (modoViagemDAO.delModoViagem((int)id_selecionado)) {
                        Toast.makeText(getActivity(), "Apagado " + id_selecionado, Toast.LENGTH_SHORT).show();
                        atualizarAdapter();
                    } else {
                        Toast.makeText(getActivity(), "Erro ao apagar " + id_selecionado, Toast.LENGTH_SHORT).show();
                    }
                    mode.finish();
                    return true;
                case R.id.cab_item_edit:
                    Intent intent = new Intent(getActivity(), NovoModoViagemActivity.class);
                    modoViagem = modoViagemDAO.getModoViagem((int) id_selecionado);
                    modoViagem.setId((int)id_selecionado);
                    intent.putExtra(Opcoes.isEditTag, true);
                    intent.putExtra(Opcoes.modoTag, modoViagem);
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

