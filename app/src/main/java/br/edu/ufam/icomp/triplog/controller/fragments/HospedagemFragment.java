package br.edu.ufam.icomp.triplog.controller.fragments;


import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
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
import br.edu.ufam.icomp.triplog.controller.NovaHospedagemActivity;
import br.edu.ufam.icomp.triplog.dao.HospedagemDAO;
import br.edu.ufam.icomp.triplog.model.Hospedagem;
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

    private ActionMode mActionMode;

    private long id_selecionado;
    private int position_selecionado;

    private Hospedagem hospedagem;

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
                        String s = DateHandler.dateFormatDefault.format(DateHandler.sdf.parse(cursor.getString(columnIndex))) + " " +
                                DateHandler.timeFormat.format(DateHandler.sdf_time.parse(cursor.getString(cursor.getColumnIndex(BancoDeDados.HOSPEDAGEM_COL_HORA_CHECKIN))));
                        ((TextView) view).setText(getResources().getString(R.string.tv_check_in) + ": " + s);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                if (columnIndex == cursor.getColumnIndex(BancoDeDados.HOSPEDAGEM_COL_DATA_CHECKOUT)) {
                    try {
                        String s = DateHandler.dateFormatDefault.format(DateHandler.sdf.parse(cursor.getString(columnIndex))) + " " +
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
        Cursor novo_cursor = hospedagemDAO.getHospedagens(Opcoes.getIdViagem());
        cursor = cursorAdapter.swapCursor(novo_cursor);
        cursorAdapter.notifyDataSetChanged();
        if (cursorAdapter.isEmpty()) {
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
                    if (hospedagemDAO.delHospedagem((int)id_selecionado)) {
                        Toast.makeText(getActivity(), "Apagado " + id_selecionado, Toast.LENGTH_SHORT).show();
                        atualizarAdapter();
                    } else {
                        Toast.makeText(getActivity(), "Erro ao apagar " + id_selecionado, Toast.LENGTH_SHORT).show();
                    }
                    mode.finish();
                    return true;
                case R.id.cab_item_edit:
                    Intent intent = new Intent(getActivity(), NovaHospedagemActivity.class);
                    hospedagem = hospedagemDAO.getHospedagem((int)id_selecionado);
                    hospedagem.setId((int)id_selecionado);
                    intent.putExtra(Opcoes.isEditTag, true);
                    intent.putExtra(Opcoes.hospedagemTag,hospedagem);
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
