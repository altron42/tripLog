package br.edu.ufam.icomp.triplog.controller.fragments;


import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.viagemDAO = new ViagemDAO(getActivity());

        cursor_viagens = viagemDAO.getViagens();

        String[] colunasDe = {BancoDeDados.VIAGEM_COL_NOME, BancoDeDados.VIAGEM_COL_COMECO,
                BancoDeDados.VIAGEM_COL_FIM, BancoDeDados.VIAGEM_COL_ICONE};
        int[] colunasPara = {R.id.tv_titulo_viagem, R.id.tv_comeco_viagem, R.id.tv_fim_viagem, R.id.iv_icone_viagem };
        Log.i(null, "Debug 1");
        adapter_viagens = new SimpleCursorAdapter(getActivity(),
                R.layout.lista_viagens,
                cursor_viagens,
                colunasDe,
                colunasPara,
                0);
        Log.i(null, "Debug 2");
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
                if (columnIndex == cursor.getColumnIndex(BancoDeDados.VIAGEM_COL_COMECO) ||
                        columnIndex == cursor.getColumnIndex(BancoDeDados.VIAGEM_COL_FIM)) {
                    try {
                        String data = DateHandler.dateFormatFull.format(DateHandler.sdf.parse(cursor.getString(columnIndex)));
                        int stringId = columnIndex == cursor.getColumnIndex(BancoDeDados.VIAGEM_COL_COMECO) ?
                                R.string.et_comeco_viagem : R.string.et_fim_viagem;
                        ((TextView) view).setText(getString(stringId) + ": " + data);
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
        Cursor novo_cursor = viagemDAO.getViagens();

        cursor_viagens = adapter_viagens.swapCursor(novo_cursor);

        adapter_viagens.notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        Toast.makeText(getActivity(),"Clicado item id: "+ id + " POS: " + pos, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), PrincipalViagemActivity.class);
        Viagem viagem_selecionada = viagemDAO.getViagem(cursor_viagens.getInt(0));
        intent.putExtra("viagem_selecionada", viagem_selecionada);
        startActivity(intent);
    }
}
