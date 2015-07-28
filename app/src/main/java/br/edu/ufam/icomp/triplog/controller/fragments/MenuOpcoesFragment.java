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
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import br.edu.ufam.icomp.triplog.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuOpcoesFragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);

        String[] itens_lista = {
                "Hospedagem",
                "Gastos",
                "Modo de viagem",
                "Notas de viagem",
                "Atividades"
        };

        ArrayList<String> lista = new ArrayList<>();
        for (String s : itens_lista) {
            lista.add(s);
        }

        setListAdapter(new ArrayAdapter<String>(getActivity(),
                R.layout.lista_menu_opcoes, R.id.tv_item_nome, lista));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("FragmentList", "Item clicado: " + id);
        Toast.makeText(getActivity(), "Item clicado" + id, Toast.LENGTH_SHORT).show();
    }

}
