package es.unileon.happycow.procedures;

//import es.unileon.happycow.database.PonderationDB;
//import es.unileon.happycow.database.Database;
//import es.unileon.happycow.database.ValorationDB;
import es.unileon.happycow.model.Farm;
import es.unileon.happycow.model.User;
import es.unileon.happycow.model.composite.Criterion;
import es.unileon.happycow.model.facade.EvaluationModel;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author amdiaz8
 */
public class Backup {
    /*private StringBuilder estado;

    public String rutaTemporalExportar = System.getProperty("java.io.tmpdir")
            .concat("/HappyCowExport");
    public String rutaTemporalImportar = System.getProperty("java.io.tmpdir")
            .concat("/HapppyCowImport");

    public Backup() {
        this.estado = new StringBuilder();
    }

    public String getEstado() {
        String result=estado.toString();
        estado=new StringBuilder();
        return result;
    }
    
    public boolean backup(File carpeta) {
        boolean result=true;
        //Establezco la ruta temporal y creo la carpeta
        File temporal = new File(rutaTemporalExportar);
        temporal.mkdirs();

        try {
            File criterios = new File(temporal, "criterios");
            File usuarios = new File(temporal, "usuarios");
            File granjas = new File(temporal, "granjas");
            File evaluaciones = new File(temporal, "evaluaciones");
            File notas = new File(temporal, "notas");
            File ponderacionCriterio = new File(temporal, "ponCategorias");
            File ponderacionCategoria = new File(temporal, "ponCriterios");
            // Se abre el fichero donde se hará la copia
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usuarios));
            LinkedList<User> users = Database.getInstance().getListUsers();
            for (User user : users) {
                oos.writeObject(user);
            }
            oos.close();

            oos = new ObjectOutputStream(new FileOutputStream(granjas));
            LinkedList<Farm> farms = Database.getInstance().getAllFarms();
            for (Farm farm : farms) {
                oos.writeObject(farm);
            }
            oos.close();

            oos = new ObjectOutputStream(new FileOutputStream(evaluaciones));
            LinkedList<EvaluationModel> evals = Database.getInstance().getAllEvaluations();
            for (EvaluationModel model : evals) {
                oos.writeObject(model);
            }
            oos.close();
            
            oos = new ObjectOutputStream(new FileOutputStream(notas));
            LinkedList<ValorationDB> valorations = Database.getInstance().getAllValorations();
            for (ValorationDB valoration : valorations) {
                oos.writeObject(valoration);
            }
            oos.close();
            
            oos = new ObjectOutputStream(new FileOutputStream(criterios));
            LinkedList<Criterion> crit = Database.getInstance().getListCriterion();
            for (Criterion criterion : crit) {
                oos.writeObject(criterion);
            }
            oos.close();

            //quedan las ponderaciones
            oos = new ObjectOutputStream(new FileOutputStream(ponderacionCategoria));
            LinkedList<PonderationDB> cat = Database.getInstance().getCategoryPonderation();
            for (PonderationDB pon : cat) {
                oos.writeObject(pon);
            }
            oos.close();
            
            oos = new ObjectOutputStream(new FileOutputStream(ponderacionCriterio));
            LinkedList<PonderationDB> ponderation = Database.getInstance().getCriterionPonderation();
            for (PonderationDB poncri : ponderation) {
                oos.writeObject(poncri);
            }
            oos.close();
            
            exportar(carpeta.toString(), new Date().toString());
            
        } catch (Exception e) {
            result=false;
            estado.append("\nError exportando la base de datos:\n");
            estado.append(e.toString());
            estado.append("\n");
        }
        
        return result;
    }

    private void exportar(String carpeta, String fecha) throws Exception {

        //crea el comprimido
        File comprimido = new File(carpeta.concat("/backup-" + fecha + ".backup"));

        int contador = 1;
            //compruebo si no existe, si existe, se crea un nuevo nombre al comprimido
        //codigos (1).zip...
        boolean exist=true;
        while (exist) {
            if (comprimido.exists()) {
                comprimido = new File(carpeta.concat("/backup-" + fecha
                        .concat(Integer.toString(contador))
                        .concat(").backup")));
                contador++;
            } else {
                exist=false;
            }
        }
        //comprimo los ficheros exportados
        comprimir(comprimido);

        //borro todos los ficheros temporales
        borrarDirectorio(new File(rutaTemporalExportar));
    }

    *//**
     * Comprime los ficheros exportados y los deja en la ruta especificada
     *
     * @param ruta donde se dejará el comprimido
     * @param llave la llave para la encriptación
     *//*
    private void comprimir(File comprimido) throws Exception {
        //lista los ficheros a comprimir
        File[] ficheros = new File(rutaTemporalExportar).listFiles();

        try {
            //creo los stream necesarios para crear el fichero comprimido
            FileOutputStream salidaFichero = new FileOutputStream(comprimido);
            ZipOutputStream salidaZip = new ZipOutputStream(salidaFichero);
            salidaZip.setLevel(9);

            //por cada fichero lo añado al comprimido
            for (int i = 0; i < ficheros.length; i++) {
                //cojo el fichero
                ZipEntry ze = new ZipEntry(ficheros[i].getName());
                FileInputStream fin = new FileInputStream(ficheros[i].toString());
                //lo paso por el encriptador
                try {
                    System.out.println("Compressing " + ficheros[i].toString());
                    //lo escribo en el comprimido
                    salidaZip.putNextEntry(ze);
                    for (int c = fin.read(); c != -1; c = fin.read()) {
                        salidaZip.write(c);
                    }
                }finally {
                    fin.close();
                }
            }
            //cierro el comprimido terminado
            salidaZip.close();
        } catch (IOException x) {
            throw new Exception("Error manejando el archivo: "+x.toString());
        }

    }

    *//**
     * Se borra los ficheros temporales, método recursivo para borrar también
     * las posibles carpetas que haya dentro del directorio dado
     *
     * @param directorio el directorio a borrar con todo su contenido
     *//*
    private void borrarDirectorio(File directorio) {
        //cojo todos los ficheros/directorios que hay
        File[] ficheros = directorio.listFiles();

        //por cada fichero, si es directorio ejecuto de nuevo el método
        //para que elimine todo su interior y posteriormente lo elimino
        for (int x = 0; x < ficheros.length; x++) {
            if (ficheros[x].isDirectory()) {
                borrarDirectorio(ficheros[x]);
            }
            ficheros[x].delete();
        }

        //borro el propio directorio una vez terminado con su contenido
        directorio.delete();
    }

    
    
    *//**
     * Realiza los pasos necesarios para importar el código
     *
     * @param comprimido el fichero comprimido
     * @param contrasena la contraseña del fichero
     *//*
    private boolean importar(File comprimido) {

        //establezco la ruta temporal y creo las carpetas
        File temporal = new File(rutaTemporalImportar);
        temporal.mkdirs();

        boolean correcto = false;
        try {
            //creo los stream necesarios
            FileInputStream fis = new FileInputStream(comprimido);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry entry;
            


            int buffer = 2048;
            byte data[] = new byte[buffer];
            //por cada fichero que está en el comprimido
            while ((entry = zis.getNextEntry()) != null) {
                correcto = true;
                System.out.println("Extracting: " + entry);
                //escribo el fichero pasandolo por el desencriptador
                FileOutputStream fos = new FileOutputStream(rutaTemporalImportar
                        .concat("/").concat(entry.getName()));
                
                int count;

                while ((count = zis.read(data, 0, buffer)) != -1) {
                    fos.write(data, 0, count);
                }
                fos.close();
            }
            zis.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            return correcto;
        }
    }
    
    public boolean recuperarBackup(File fichero){
        boolean result=true;
        if(fichero.exists()){
            importar(fichero);
            try{
                
                Database.getInstance().startTransaccion();
                
                if(Database.getInstance().clearDB()){
                    File temporal=new File(rutaTemporalImportar);
                    File criterios = new File(temporal, "criterios");
                    File usuarios = new File(temporal, "usuarios");
                    File granjas = new File(temporal, "granjas");
                    File evaluaciones = new File(temporal, "evaluaciones");
                    File notas = new File(temporal, "notas");
                    File ponderacionCriterio = new File(temporal, "ponCategorias");
                    File ponderacionCategoria = new File(temporal, "ponCriterios");


                    // Se abre el fichero donde se hará la copia
                    ObjectInputStream oos = new ObjectInputStream(new FileInputStream(usuarios));
                    try{
                        User user=(User)oos.readObject();
                        while(user!=null){
                            result=result & Database.getInstance().newUser(user);
                            user=(User)oos.readObject();
                        }
                    }catch(EOFException o){
                        System.out.println(o.toString());
                    }
                    oos.close();

                    oos = new ObjectInputStream(new FileInputStream(granjas));
                    try{
                        Farm farm=(Farm)oos.readObject();
                        while(farm!=null){
                            result=result & Database.getInstance().newFarm(farm);
                            farm=(Farm)oos.readObject();
                        }
                    }catch(EOFException o){
                        System.out.println(o.toString());
                    }
                    oos.close();

                    oos = new ObjectInputStream(new FileInputStream(criterios));
                    try{
                        Criterion criterion=(Criterion)oos.readObject();
                        while(criterion!=null){
                            result=result & Database.getInstance().newCriterion(criterion);
                            criterion=(Criterion)oos.readObject();
                        }
                    }catch(EOFException o){
                        System.out.println(o.toString());
                    }
                    oos.close();

                    oos = new ObjectInputStream(new FileInputStream(evaluaciones));
                    try{
                        EvaluationModel model=(EvaluationModel)oos.readObject();
                        while(model!=null){
                            result=result & Database.getInstance().saveEvaluationBackup(model);
                            model=(EvaluationModel)oos.readObject();
                        }
                    }catch(EOFException o){
                        System.out.println(o.toString());
                    }
                    oos.close();

                    oos = new ObjectInputStream(new FileInputStream(notas));
                    try{
                    ValorationDB valoration=(ValorationDB)oos.readObject();
                        while(valoration!=null){
                            result=result & Database.getInstance().
                                    saveValoration(valoration);
                            valoration=(ValorationDB)oos.readObject();
                        }
                    }catch(EOFException o){
                        System.out.println(o.toString());
                    }
                    oos.close();
                    
                    oos = new ObjectInputStream(new FileInputStream(ponderacionCategoria));
                    LinkedList<PonderationDB> listC=new LinkedList<>();
                    try{
                    PonderationDB pon=(PonderationDB)oos.readObject();
                        while(pon!=null){
                            listC.add(pon);
                            pon=(PonderationDB)oos.readObject();
                        }
                        
                    }catch(EOFException o){
                        System.out.println(o.toString());
                    }
                    result=result & Database.getInstance().saveCategoryPonderation(listC);
                    oos.close();
                    
                    oos = new ObjectInputStream(new FileInputStream(ponderacionCriterio));
                    LinkedList<PonderationDB> list=new LinkedList<>();
                    try{
                    PonderationDB pon=(PonderationDB)oos.readObject();
                        while(pon!=null){
                            list.add(pon);
                            pon=(PonderationDB)oos.readObject();
                        }
                        
                    }catch(EOFException o){
                        System.out.println(o.toString());
                    }
                    result=result & Database.getInstance().saveCriterionPonderation(list);
                    oos.close();
                }
            }catch(IOException | ClassNotFoundException e){
                result=false;
                estado.append("\nError exportando la base de datos:\n");
                estado.append(e.toString());
                estado.append("\n");
            } catch (SQLException ex) {
                Logger.getLogger(Backup.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            finImportacion();
        }else{
            result=false;
            estado.append("\nEl fichero a importar no existe o no es correcto\n");
        }
        
        if(result){
            Database.getInstance().commit();
            System.out.println("Exito");
        }else{
            Database.getInstance().rollback();
            System.out.println("Fracaso");
        }
        return result;
    }

    *//**
     * Cuando se termina con la importación, borro los ficheros temporales
     *//*
    private void finImportacion() {
        borrarDirectorio(new File(rutaTemporalImportar));
    }
*/
}
