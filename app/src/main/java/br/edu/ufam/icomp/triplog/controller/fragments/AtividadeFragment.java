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
import br.edu.ufam.icomp.triplog.controller.NovaAtividadeActivity;
import br.edu.ufam.icomp.triplog.dao.AtividadeDAO;
import br.edu.ufam.icomp.triplog.model.Atividade;
import br.edu.ufam.icomp.triplog.util.BancoDeDados;
import br.edu.ufam.icomp.triplog.util.DateHandler;
import br.edu.ufam.icomp.triplog.util.Opcoes;

/**
 * A simple {@link Fragment} subclass.
 */
public class AtividadeFragment extends ListFragment {

    AtividadeDAO atividadeDAO;
    Cursor cursor;
    SimpleCursorAdapter cursorAdapter;

    private ActionMode mActionMode;

    private long id_selecionado;
    private int position_selecionado;

    private Atividade atividade;

    @Override
    public void onStart() {
        super.onStart();
        setEmptyText("Esta lista está vazia");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        atividadeDAO = new AtividadeDAO(getActivity());

        String[] colunasDe = {BancoDeDados.ATIVIDADE_COL_NOME, BancoDeDados.ATIVIDADE_COL_DATA, BancoDeDados.ATIVIDADE_COL_HORA};
        int[] colunasPara = {R.id.textView5, R.id.textView6, R.id.textView7};

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
                if (columnIndex == cursor.getColumnIndex(BancoDeDados.ATIVIDADE_COL_DATA)) {
                    try {
                        String s = DateHandler.dateFormatDefault.format(DateHandler.sdf.parse(cursor.getString(columnIndex)));
                        ((TextView) view).setText(getResources().getString(R.string.et_data) + ": " + s);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                if (columnIndex == cursor.getColumnIndex(BancoDeDados.ATIVIDADE_COL_HORA)) {
                    try {
                        String s = DateHandler.timeFormat.format(DateHandler.sdf_time.parse(cursor.getString(columnIndex)));
                        ((TextView)view).setText(getResources().getString(R.string.et_hora)+ ": " + s);
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

    private void atualizarAdapter() {
        Cursor novo = atividadeDAO.getAtividades(Opcoes.getIdViagem());
        cursor = cursorAdapter.swapCursor(novo);
        cursorAdapter.notifyDataSetChanged();
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
                    if (atividadeDAO.delAtividade((int)id_selecionado)) {
                        Toast.makeText(getActivity(), "Apagado " + id_selecionado, Toast.LENGTH_SHORT).show();
                        atualizarAdapter();
                    } else {
                        Toast.makeText(getActivity(), "Erro ao apagar " + id_selecionado, Toast.LENGTH_SHORT).show();
                    }
                    mode.finish();
                    return true;
                case R.id.cab_item_edit:
                    Intent intent = new Intent(getActivity(), NovaAtividadeActivity.class);
                    atividade = atividadeDAO.getAtividade((int) id_selecionado);
                    atividade.setId((int)id_selecionado);
                    intent.putExtra(Opcoes.isEditTag, true);
                    intent.putExtra(Opcoes.atividadeTag, atividade);
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
