package br.edu.ufam.icomp.triplog.controller.fragments;


import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.controller.CarteirasActivity;
import br.edu.ufam.icomp.triplog.controller.DespesasActivity;
import br.edu.ufam.icomp.triplog.controller.HospedagemActivity;
import br.edu.ufam.icomp.triplog.controller.ModoViagemActivity;
import br.edu.ufam.icomp.triplog.util.Opcoes;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuOpcoesFragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);

        setListAdapter(new ArrayAdapter<String>(getActivity(),
                R.layout.lista_menu_opcoes, R.id.tv_item_nome, Opcoes.menu_opcoes_lista));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        switch (position) {
            case Opcoes.opcao_despesas:
                Intent intent_0 = new Intent(getActivity(), DespesasActivity.class);
                startActivity(intent_0);
                break;
            case Opcoes.opcao_hospedagem:
                Intent intent_1 = new Intent(getActivity(), HospedagemActivity.class);
                startActivity(intent_1);
                break;
            case Opcoes.opcao_atividades:
                break;
            case Opcoes.opcao_carteiras:
                Intent intent_4 = new Intent(getActivity(), CarteirasActivity.class);
                startActivity(intent_4);
                break;
            case Opcoes.opcao_modo:
                Intent intent_5 = new Intent(getActivity(), ModoViagemActivity.class);
                startActivity(intent_5);
                break;
            default:
                break;
        }
    }

}
