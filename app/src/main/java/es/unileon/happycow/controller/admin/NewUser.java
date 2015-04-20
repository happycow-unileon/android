package es.unileon.happycow.controller.admin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import es.unileon.happycow.R;
import es.unileon.happycow.database.Database;
import es.unileon.happycow.handler.IdHandler;
import es.unileon.happycow.handler.IdUser;
import es.unileon.happycow.model.Rol;
import es.unileon.happycow.model.User;

public class NewUser extends Fragment {

    public NewUser(){

    }

    private void addUser(View view){
        EditText text;

        text=(EditText)view.findViewById(R.id.passwordUser);
        String password=text.getText().toString();

        Spinner spinner=(Spinner)view.findViewById(R.id.rolUser);
        String rol=spinner.getSelectedItem().toString();

        text=(EditText)view.findViewById(R.id.nameUser);
        String nameUser=text.getText().toString();

        IdHandler id=new IdUser(nameUser);
        User user=Database.getInstance(null).getUser(id);

        if(user!=null){
            text.setError("Ya existe el usuario, introduzca otro nombre.");
            text.requestFocus();
        }else{
            user=new User(id, password, rol);
            TextView read=(TextView)view.findViewById(R.id.message);
            if(Database.getInstance(null).newUser(user)){
                read.setText("Usuario " + nameUser + " correctamente añadido.");

            }else{
                read.setText("Fallo al guardar el usuario. Pruebe otra vez.");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_new_user, container, false);
        Button button = (Button) rootView.findViewById(R.id.buttonNewUser);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser(rootView);
            }
        });

        Spinner spinner=(Spinner)rootView.findViewById(R.id.rolUser);
        spinner.setAdapter(new ArrayAdapter<Rol>(getActivity(), android.R.layout.simple_list_item_1, Rol.values()));

        return rootView;
    }
}
