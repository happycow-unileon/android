package es.unileon.happycow.controller.admin;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.io.File;

import es.unileon.happycow.R;
import es.unileon.happycow.procedures.Backup;
import es.unileon.happycow.utils.SimpleFileDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class BackupFragment extends Fragment {
    private final Backup procedure;

    private File export;
    private File importBackup;

    public BackupFragment() {
        procedure=new Backup();
    }

    private void setFileExport(View view){
        final TextView text=(TextView)view.findViewById(R.id.textRealizar);
        //Create FileOpenDialog and register a callback
        SimpleFileDialog fileOpenDialog =  new SimpleFileDialog(
                getActivity(),
                "FileOpen",
                new SimpleFileDialog.SimpleFileDialogListener()
                {
                    @Override
                    public void onChosenDir(String chosenDir)
                    {
                        export=new File(chosenDir);//fileChooser.getSelectedFile();
                        text.setText(chosenDir);
//                        // The code in this function will be executed when the dialog OK button is pushed
//                        editFile.setText(chosenDir);
                    }
                }
        );

        fileOpenDialog.chooseFile_or_Dir();
    }

    private void setFileImport(View view){
        final TextView text=(TextView)view.findViewById(R.id.textRecuperar);
        //Create FileOpenDialog and register a callback
        SimpleFileDialog fileOpenDialog =  new SimpleFileDialog(
                getActivity(),
                "FolderChoose",
                new SimpleFileDialog.SimpleFileDialogListener()
                {
                    @Override
                    public void onChosenDir(String chosenDir)
                    {
                        importBackup=new File(chosenDir);//fileChooser.getSelectedFile();
                        text.setText(chosenDir);
//                        // The code in this function will be executed when the dialog OK button is pushed
//                        editFile.setText(chosenDir);
                    }
                }
        );

        fileOpenDialog.chooseFile_or_Dir();
        //You can change the default filename using the public variable "Default_File_Name"
//        fileOpenDialog.default_file_name = editFile.getText().toString();
//        fileOpenDialog.chooseFile_or_Dir(fileOpenDialog.default_file_name);
    }

    private void export(View view){
        TextView text=(TextView)view.findViewById(R.id.errorBackup);
        if(export!=null){
            if(export.exists() && export.isDirectory()){
                if(!procedure.backup(export)){
                    text.setText(procedure.getEstado());
                }else{
                    text.setText("Operación realizada correctamente");
                }
            }else{
                text.setText("Debe ser una carpeta y ha de existir");
            }
        }else{
            text.setText("Seleccione primero una ruta");
        }
    }

    private void importBackup(View view){
        TextView text=(TextView)view.findViewById(R.id.errorRecover);
        if(importBackup!=null){
            if(importBackup.exists() && importBackup.isFile()){
                if(!procedure.recuperarBackup(importBackup)){
                    text.setText(procedure.getEstado());
                }else{
                    text.setText("Operación realizada correctamente");
                }
            }else{
                text.setText("Debe ser un fichero y ha de existir");
            }
        }else{
            text.setText("Seleccione primero una ruta");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_backup, container, false);

        TextView text=(TextView)rootView.findViewById(R.id.folder);
        text.setPaintFlags(text.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        text=(TextView)rootView.findViewById(R.id.file);
        text.setPaintFlags(text.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Button button = (Button) rootView.findViewById(R.id.selectFolder);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFileExport(rootView);
            }
        });

        button = (Button) rootView.findViewById(R.id.selectFile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFileImport(rootView);
            }
        });

        button = (Button) rootView.findViewById(R.id.backup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                export(rootView);
            }
        });

        button = (Button) rootView.findViewById(R.id.recover);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                importBackup(rootView);
            }
        });


        return rootView;
    }


}
