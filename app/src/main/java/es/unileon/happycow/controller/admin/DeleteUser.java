package es.unileon.happycow.controller.admin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.LinkedList;

import es.unileon.happycow.R;
import es.unileon.happycow.database.Database;
import es.unileon.happycow.model.Rol;
import es.unileon.happycow.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteUser extends Fragment {


    public DeleteUser() {
        // Required empty public constructor
    }

    private void deleteUser(View view){
        Spinner spinner=(Spinner)view.findViewById(R.id.listUsers);
        String target=spinner.getSelectedItem().toString();
        boolean resultado=Database.getInstance(null).removeUser(new User(target, "something", Rol.VETERINARIO));
        if(resultado){
            addUsers(view);
        }else{
            TextView text=(TextView)view.findViewById(R.id.error);
            text.setText("Error eliminando el usuario");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_delete_user, container, false);
        Button button = (Button) rootView.findViewById(R.id.buttonDeleteUser);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(rootView);
            }
        });

        addUsers(rootView);

        return rootView;
    }

    private void addUsers(View view){
        Spinner spinner=(Spinner)view.findViewById(R.id.listUsers);
        LinkedList<User> list= Database.getInstance(null).getListUsers();
        LinkedList<String> listUsers=new LinkedList<>();
        for (User user:list){
            listUsers.add(user.getName());
        }
        spinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listUsers));
    }

}
