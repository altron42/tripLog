package br.edu.ufam.icomp.triplog.controller.fragments;


import android.app.ListFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.util.Opcoes;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuOpcoesFragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);

        setListAdapter(new ArrayAdapter<String>(getActivity(),
                R.layout.lista_menu_opcoes, R.id.tv_item_nome, new Opcoes(getActivity()).menu_opcoes_lista));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        TextView tv_opcao = (TextView) v.findViewById(R.id.tv_item_nome);
        Toast.makeText(getActivity(), "Opçao: " + tv_opcao.getText() + " ID: " + id + " position " + position, Toast.LENGTH_SHORT).show();
    }

}
