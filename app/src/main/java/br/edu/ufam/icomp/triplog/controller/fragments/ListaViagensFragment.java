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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.controller.PrincipalViagemActivity;
import br.edu.ufam.icomp.triplog.dao.ViagemDAO;
import br.edu.ufam.icomp.triplog.model.Viagem;
import br.edu.ufam.icomp.triplog.util.BancoDeDados;
import br.edu.ufam.icomp.triplog.util.DateHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaViagensFragment extends ListFragment {
    private ViagemDAO viagemDAO;
    private Cursor cursor_viagens;
    private SimpleCursorAdapter adapter_viagens;

    private ActionMode mActionMode;

    private long id_selecionado;
    private int position_selecionado;

    @Override
    public void onStart() {
        super.onStart();
        setEmptyText(Html.fromHtml("<br><br><p>Sua lista de viagens está vazia</p>"));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.viagemDAO = new ViagemDAO(getActivity());

        //cursor_viagens = viagemDAO.getViagens();

        String[] colunasDe = {BancoDeDados.VIAGEM_COL_NOME, BancoDeDados.VIAGEM_COL_COMECO,
                BancoDeDados.VIAGEM_COL_FIM, BancoDeDados.VIAGEM_COL_ICONE};
        int[] colunasPara = {R.id.tv_titulo_viagem, R.id.tv_comeco_viagem, R.id.tv_fim_viagem, R.id.iv_icone_viagem };

        adapter_viagens = new SimpleCursorAdapter(getActivity(),
                R.layout.lista_viagens,
                cursor_viagens,
                colunasDe,
                colunasPara,
                0);

        setListAdapter(adapter_viagens);

        adapter_viagens.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (columnIndex == cursor.getColumnIndex(BancoDeDados.VIAGEM_COL_ICONE)) {
                    String nome_imagem = cursor.getString(columnIndex);
                    ImageView iv_icone = (ImageView) view;

                    Log.i(null, cursor.getInt(0) + " Nome da imagem no DB: " + nome_imagem);

                    if (nome_imagem == null || nome_imagem.matches("null")) {
                        Log.d("ImageView", "Nome da imagem não especificado");
                        iv_icone.setImageResource(R.mipmap.ic_launcher);
                    } else {
                        int resource = getResources().getIdentifier(nome_imagem, "drawable", null);
                        iv_icone.setImageResource(resource);
                    }
                    return true;
                }
                if (columnIndex == cursor.getColumnIndex(BancoDeDados.VIAGEM_COL_COMECO)) {
                    try {
                        String data = DateHandler.dateFormatFull.format(DateHandler.sdf.parse(cursor.getString(columnIndex)));
                        ((TextView) view).setText(getString(R.string.et_comeco_viagem) + ": " + data);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                if (columnIndex == cursor.getColumnIndex(BancoDeDados.VIAGEM_COL_FIM)) {
                    try {
                        String data = DateHandler.dateFormatFull.format(DateHandler.sdf.parse(cursor.getString(columnIndex)));
                        ((TextView) view).setText(getString(R.string.et_fim_viagem) + ": " + data);
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
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        if (mActionMode != null) {
            id_selecionado = id;
            return;
        }

        Toast.makeText(getActivity(),"Clicado item id: "+ id + " POS: " + pos, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), PrincipalViagemActivity.class);
        Viagem viagem_selecionada = viagemDAO.getViagem((int)id);
        Log.i(null,"Id antes " + id);
        viagem_selecionada.setId((int)id);
        intent.putExtra("viagem_selecionada", viagem_selecionada);
        startActivity(intent);
    }

    private void atualizarAdapter() {
        Cursor novo_cursor = viagemDAO.getViagens();
        cursor_viagens = adapter_viagens.swapCursor(novo_cursor);
        adapter_viagens.notifyDataSetChanged();
        if (adapter_viagens.isEmpty()) {
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
                    if (viagemDAO.delViagem((int)id_selecionado)) {
                        Toast.makeText(getActivity(), "Apagado " + id_selecionado, Toast.LENGTH_SHORT).show();
                        atualizarAdapter();
                    } else {
                        Toast.makeText(getActivity(), "Erro ao apagar " + id_selecionado, Toast.LENGTH_SHORT).show();
                    }
                    mode.finish();
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
