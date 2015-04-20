package es.unileon.happycow.database.concreteDatabase;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.LinkedList;

import es.unileon.happycow.database.DataBaseOperations;
import es.unileon.happycow.database.PonderationDB;
import es.unileon.happycow.database.ValorationDB;
import es.unileon.happycow.database.prototype.CriterionPrototype;
import es.unileon.happycow.handler.Category;
import es.unileon.happycow.handler.IdCategory;
import es.unileon.happycow.handler.IdCriterion;
import es.unileon.happycow.handler.IdEvaluation;
import es.unileon.happycow.handler.IdFarm;
import es.unileon.happycow.handler.IdHandler;
import es.unileon.happycow.handler.IdUser;
import es.unileon.happycow.handler.IdValoration;
import es.unileon.happycow.model.Farm;
import es.unileon.happycow.model.InformationEvaluation;
import es.unileon.happycow.model.Rol;
import es.unileon.happycow.model.User;
import es.unileon.happycow.model.composite.Criterion;
import es.unileon.happycow.model.composite.Valoration;
import es.unileon.happycow.model.facade.EvaluationModel;
import es.unileon.happycow.model.facade.InterfaceEvaluationModel;

/**
 * Created by dorian on 18/04/15.
 */
public class AndroidSQLite extends SQLiteOpenHelper implements DataBaseOperations {
    private static final String DATABASE_NAME = "HappyCow";
    private static final int DATABASE_VERSION = 1;
    private Context contextApp;

    private static Cursor result = null;
    private SQLiteDatabase connection;
    private User user;
    private int nextIdValoration = -1;
    private CriterionPrototype criterions;
    private boolean criterionInitialized;

    public AndroidSQLite (Context contexto) {
        super(contexto, DATABASE_NAME, null, DATABASE_VERSION);
        criterions = new CriterionPrototype();
        criterionInitialized = false;
        contextApp=contexto;
    }

        /**
         * Crea las tablas si no existen
         *
         * @throws Exception excepción con el mensaje de error
         */
        private void crearBaseDatos(SQLiteDatabase sqLiteDatabase) {
        System.out.println("* Creating table...");

        //tabla con los usuarios y las contraseñas
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS USUARIO ("
                + "  NOMBREUSUARIO NVARCHAR2(50) UNIQUE,"
                + "  CONTRASENIA NVARCHAR2(50),"
                + "  ROL NVARCHAR2(50),"
                + "  CONSTRAINT PK_USUARIO PRIMARY KEY (NOMBREUSUARIO));");

            //tablas con los datos de los códigos y su usuario
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS FARM ("
                    + "  IDGRANJA NUMBER(38, 0),"
                    + "  NOMBREGRANJA NVARCHAR2(50),"
                    + "  NOMBREUSUARIO NVARCHAR2(50),"
                    + "  DIRECCION NVARCHAR2(100),"
                    + "  NOMBREGANADERO NVARCHAR2(100),"
                    + "  NUMEROVACAS NUMBER(38, 0),"
                    + "  IDENTIFICADORGRANJA NVARCHAR2(50),"
                    + "  OTROSDATOS NVARCHAR2(350),"
                    + "  DNIGANADERO NVARCHAR2(50),"
                    + "  ENABLED BOOL NOT NULL  DEFAULT TRUE,"
                    + "  CONSTRAINT PK_FARM PRIMARY KEY (IDGRANJA, NOMBREUSUARIO),"
                    + "  CONSTRAINT FK_FARM_USUARIO_NOMBREUSUARIO FOREIGN KEY (NOMBREUSUARIO)"
                    + "  REFERENCES USUARIO(NOMBREUSUARIO) ON DELETE CASCADE,"
                    + "  CONSTRAINT KEY_FARM_IDGRANJA UNIQUE (IDGRANJA));");


            //tablas con registro de los tipos de codigos usados y su usuario
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS EVALUATION ("
                    + "  IDEVALUATION NUMBER(38, 0),"
                    + "  IDGRANJA NUMBER(38, 0),"
                    + "  USUARIO NVARCHAR2(50),"
                    + "  NOTA FLOAT(32),"
                    + "  ALIMENTACION FLOAT(32),"
                    + "  SALUD FLOAT(32),"
                    + "  COMFORT FLOAT(32),"
                    + "  COMPORTAMIENTO FLOAT(32),"
                    + "  FECHA DATE,"
                    + "  NUMEROVACAS NUMBER(38,0),"
                    + "  CONSTRAINT PK_EVALUATION PRIMARY KEY (IDEVALUATION, USUARIO),"
                    + "CONSTRAINT FK_EVALUATION_NOMBREUSUARIO FOREIGN KEY (USUARIO) "
                    + "REFERENCES USUARIO(NOMBREUSUARIO) ON DELETE CASCADE,"
                    + "  CONSTRAINT KEY_EVALUATION_IDEVALUACION UNIQUE (IDEVALUATION));");

            //tablas con registro de los tipos de codigos usados y su usuario
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS CRITERION ("
                    + "  NOMBRECRITERIO NVARCHAR2(100) UNIQUE,"
                    + "  DESCRIPCION NVARCHAR2(300),"
                    + "  HELP CLOB,"
                    + "  CATEGORIA NVARCHAR2(50),"
                    + "  CONSTRAINT PK_CRITERION PRIMARY KEY (NOMBRECRITERIO));");

            //tablas con registro de los tipos de codigos usados y su usuario
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS VALORATION ("
                    + "  IDVALORATION NUMBER(38, 0),"
                    + "  NOMBRECRITERIO NVARCHAR2(100),"
                    + "  IDEVALUATION NUMBER(38, 0),"
                    + "  NOTA FLOAT(32),"
                    + "  PONDERACION FLOAT(32),"
                    + "  CONSTRAINT PK_VALORATION PRIMARY KEY (IDVALORATION, NOMBRECRITERIO, IDEVALUATION),"
                    + "  CONSTRAINT FK_VALORATION_CRITERION FOREIGN KEY (NOMBRECRITERIO)"
                    + "    REFERENCES CRITERION(NOMBRECRITERIO) ON DELETE CASCADE,"
                    + "  CONSTRAINT FK_VALORATION_EVALUATION FOREIGN KEY (IDEVALUATION)"
                    + "    REFERENCES EVALUATION(IDEVALUATION) ON DELETE CASCADE,"
                    + "  CONSTRAINT KEY_VALORATION_IDVALORATION UNIQUE (IDVALORATION));");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS PONDERACIONCATEGORIA "
                    + "(IDEVALUATION NUMBER(38, 0), "
                    + "CATEGORIA NVARCHAR2(50),  "
                    + "PONDERACION FLOAT,  "
                    + "CONSTRAINT PK_PONDERACIONCATEGORIA PRIMARY KEY (IDEVALUATION, CATEGORIA))");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS PONDERACIONCRITERIO (  "
                    + "IDEVALUATION NUMBER(38, 0), "
                    + "NOMBRECRITERIO NVARCHAR2(50),  "
                    + "PONDERACION FLOAT(126),  "
                    + "CONSTRAINT PK_PONDERACIONCRITERIO PRIMARY KEY (IDEVALUATION, NOMBRECRITERIO))");

            System.out.println("* Tables created");

            Cursor result = sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM CRITERION", null);
            //Nos aseguramos de que existe al menos un registro
            if (result.moveToFirst()) {
                int number=result.getInt(0);

                if(number<=0){
                    InsertCriterion fill = new InsertCriterion(contextApp);
                    fill.execute();
                }else{
                    System.out.println("Error, metiendo los criterios ya están metidos");
                }
            }

    }


    /**
     *
     * @param toEncript
     * @return
     */
    public static String encript(String toEncript) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return new String(md.digest(toEncript.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.toString());
            return null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        crearBaseDatos(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        clearDB();
    }

    @Override
    public boolean openDB() {
        connection=getWritableDatabase();
        if(connection==null){
            return false;
        }

        //cargamos los criterios
        getListCriterion();
        newUser(new User("Admin", "Admin", Rol.ADMINISTRADOR));
        return false;
    }

    @Override
    public boolean closeDB() {
        connection.close();
        return true;
    }

    /**
     * Compruebo si existe el usuario en la base de datos
     *
     * @param usuario
     * @return true si existe el usuario, false si no hay results
     * @throws Exception cualquier error que pueda surgir en la conexión
     */
    protected boolean existUser(String usuario) throws Exception {
        try {
            String args[]={usuario};
            //recojo el usuario correspondiente
            result=connection.rawQuery("SELECT * FROM USUARIO WHERE NOMBREUSUARIO=?",args);

            return result.moveToFirst();
        } catch (Exception e) {
            System.out.println("Error comprobando usuario: ".concat(e.toString()));
            throw new Exception("Error comprobando usuario");
        }
    }

    @Override
    public boolean clearDB() {
        boolean correct = true;
            startTransaccion();

            int result=connection.delete("VALORATION", "1", null);
            if(result==0){
                rollback();
                correct=false;
                return correct;
            }
            result=connection.delete("PONDERACIONCRITERIO", "1", null);
            if(result==0){
                rollback();
                correct=false;
                return correct;
            }
            result=connection.delete("PONDERACIONCATEGORIA", "1", null);
            if(result==0){
                rollback();
                correct=false;
                return correct;
            }
            result=connection.delete("EVALUATION", "1", null);
            if(result==0){
                rollback();
                correct=false;
                return correct;
            }
            result=connection.delete("FARM", "1", null);
            if(result==0){
                rollback();
                correct=false;
                return correct;
            }
            result=connection.delete("USUARIO", "1", null);
            if(result==0){
                rollback();
                correct=false;
                return correct;
            }
            result=connection.delete("CRITERION", "1", null);
            if(result==0){
                rollback();
                correct=false;
                return correct;
            }

            commit();
        return correct;
    }

    @Override
    public void startTransaccion()  {
        connection.beginTransaction();
    }

    @Override
    public void rollback() {
        connection.endTransaction();
    }

    @Override
    public void commit() {
        connection.setTransactionSuccessful();
        connection.endTransaction();
    }

    @Override
    public void executeRawQuery(LinkedList<String> list) {
        if (list != null) {
            for (String sentence : list) {
                connection.execSQL(sentence);
            }
        } else {
            System.out.println("No tenemos los criterios");
        }
    }

    @Override
    public boolean saveEvaluationBackup(InterfaceEvaluationModel evaluation) {
        boolean resultSave = false;
        try {
            LinkedList<PonderationDB> ponderationCriterion = new LinkedList<>();
            LinkedList<PonderationDB> ponderationCategory = new LinkedList<>();
            if (saveEvaluation(evaluation.getInformation())) {
                for (Category cat : Category.values()) {
                    ponderationCategory.add(new PonderationDB(
                            Integer.parseInt(evaluation.getIdHandler().toString()),
                            Category.getName(cat),
                            evaluation.getWeighing(new IdCategory(cat))));
                    for (Criterion criterio : evaluation.getListCriterion(cat)) {
                        ponderationCriterion.add(new PonderationDB(
                                Integer.parseInt(evaluation.getIdHandler().toString()),
                                criterio.getName(), criterio.getWeighing()));
                        for (Valoration valoration : evaluation.listOfCriterion(criterio.getId())) {
                            if (!saveValoration(
                                    Integer.parseInt(evaluation.getIdHandler().toString()),
                                    criterio, valoration)) {
                                resultSave = false;
                                break;
                            }
                        }
                    }
                }
                saveCriterionPonderation(ponderationCriterion);
                saveCategoryPonderation(ponderationCategory);
                resultSave = true;
            }
        } catch (NumberFormatException ex) {
            rollback();
            System.out.println(ex.toString());
        }
        return resultSave;
    }

    @Override
    public String login(String user, String passwd) {
        String resultLogin = "";
        passwd = encript(passwd);
        try {
            //recojo el usuario con su contraseña
            String args[]={user.toString()};
            result=connection.rawQuery("SELECT * FROM USUARIO WHERE NOMBREUSUARIO=?", args);
            
            if (result.moveToFirst()) {
                //compruebo la contraseña
                if (result.getString(result.getColumnIndex("CONTRASENIA")).compareTo(passwd)!=0) {
                    resultLogin="Contraseña incorrecta";
                } else {
                    this.user = new User(user, passwd, result.getString(result.getColumnIndex("ROL")), true);
                }
            } else {
                resultLogin="El usuario no existe";
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            resultLogin="Error fatal comprobando el usuario";
        }
        return resultLogin;
    }

    @Override
    public User getUser() {
        return user;

    }

    @Override
    public void logout() {
        user = null;
    }

    @Override
    public LinkedList<Farm> getListFarms() {
        return getListFarms(user.getId());
    }

    @Override
    public LinkedList<Farm> getDisabledFarms() {

        LinkedList<Farm> list = new LinkedList<>();

            result=connection.rawQuery("SELECT * FROM FARM WHERE ENABLED='FALSE'",null);

            while (result.moveToNext()) {
                int idFarm = result.getInt(result.getColumnIndex("idgranja"));
                String nameFarm = result.getString(result.getColumnIndex("nombregranja"));
                String address = result.getString(result.getColumnIndex("direccion"));
                String nameFarmer = result.getString(result.getColumnIndex("nombreganadero"));
                String dniFarmer = result.getString(result.getColumnIndex("dniganadero"));
                int cowNumber = result.getInt(result.getColumnIndex("numerovacas"));
                //datos añadidos y necesarios
                String farmerIdentifier = result.getString(result.getColumnIndex("identificadorGranja"));
                //datos no necesarios para instanciar la granja
                String otrosDatos = result.getString(result.getColumnIndex("otrosdatos"));
                IdHandler id = new IdFarm(idFarm);

                LinkedList<InterfaceEvaluationModel> listEvaluations = new LinkedList<>();
                Farm farmDisabled = new Farm(id, nameFarm, farmerIdentifier, address,
                        nameFarmer, dniFarmer, cowNumber, user.getId(),
                        otrosDatos, listEvaluations);
                list.add(farmDisabled);
            }

        return list;
    }

    @Override
    public User getUser(IdHandler id) {

        try {
            //recojo el usuario con su contraseña
            String args[]={id.toString()};
            result=connection.rawQuery("SELECT * FROM USUARIO WHERE NOMBREUSUARIO=?", args);

            if (result.moveToFirst()) {
                return new User(id.toString(), result.getString(result.getColumnIndex("CONTRASENIA")),
                        result.getString(result.getColumnIndex("ROL")));
            }
        } catch (Exception e) {
            new AlertDialog.Builder(null)
                    .setTitle("Error")
                    .setMessage("Error fatal comprobando el usuario")
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
//            JOptionPane.showMessageDialog(null,
//                    "Error fatal comprobando el usuario.", "Error",
//                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public LinkedList<InterfaceEvaluationModel> getListEvaluations(IdHandler idFarm) {
        return getListEvaluations(user.getId(), idFarm);
    }

    @Override
    public InterfaceEvaluationModel getEvaluation(IdHandler id) {
        return getEvaluation(id, user);
    }

    @Override
    public Farm getFarm(IdHandler id) {
        return this.getFarm(this.user.getId(), id);
    }

    @Override
    public int getNumberCow(IdHandler id, IdHandler evaluation) {
        int number = 0;
            String args[]={id.toString(),evaluation.toString()};
            result=connection.rawQuery("SELECT NUMEROVACAS FROM FARM WHERE IDGRANJA=? AND IDEVALUATION=?",args);

            if (result.moveToFirst()) {
                number = result.getInt(result.getColumnIndex("NUMEROVACAS"));
            }

        return number;
    }

    @Override
    public int getNumberCow(IdHandler id) {
        int number = 0;
            String args[]={id.toString()};
            result=connection.rawQuery("SELECT NUMEROVACAS FROM FARM WHERE IDGRANJA=?",args);

            if (result.moveToFirst()) {
                number = result.getInt(result.getColumnIndex("NUMEROVACAS"));
            }

        return number;
    }

    @Override
    public boolean newUser(User user) {
        ContentValues register=new ContentValues();
        register.put("NOMBREUSUARIO", user.getName());
        register.put("CONTRASENIA", user.getPassword());
        register.put("ROL", user.getStringRol());

        long result=connection.insert("USUARIO", null, register);

        return result!=-1;
//        } catch (SQLException e) {
//            System.out.println(e.toString());
//            System.out.println(e.getErrorCode());
//            JOptionPane.showMessageDialog(null,
//                    "Error con la base de datos, pruebe de nuevo", "Error",
//                    JOptionPane.ERROR_MESSAGE);
//            return false;
//        } catch (Exception e) {
//            System.out.println("Error creando usuario: ".concat(e.toString()));
//            try {
//                if (existUser(user.getName())) {
//                    JOptionPane.showMessageDialog(null,
//                            "Ya existe ese usuario", "Usuario existente",
//                            JOptionPane.ERROR_MESSAGE);
//                } else {
//                    JOptionPane.showMessageDialog(null,
//                            "Error desconocido de la base de datos", "Error",
//                            JOptionPane.ERROR_MESSAGE);
//                }
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(null,
//                        "Error desconocido de la base de datos:\n" + ex.toString(), "Error",
//                        JOptionPane.ERROR_MESSAGE);
//            }
//            return false;
//        }
    }

    @Override
    public boolean changePassword(IdHandler user, String password) {
        ContentValues register=new ContentValues();
        register.put("CONTRASENIA", password);

        String args[]={user.toString()};
        long result=connection.update("USUARIO", register, "NOMBREUSUARIO=?", args);
        return result!=-1;

    }

    @Override
    public boolean removeUser(User user) {
        String args[]={user.getName()};
        long result=connection.delete("USUARIO", "WHERE NOMBREUSUARIO=?", args);
        return result!=-1;
    }

    @Override
    public boolean newCriterion(Criterion criterion) {
        ContentValues register=new ContentValues();
        register.put("NOMBRECRITERIO", criterion.getName());
        register.put("DESCRIPCION", criterion.getDescription());
        register.put("HELP", criterion.getHelp());
        register.put("CATEGORIA", criterion.getCategory().toString());

        long result=connection.insert("CRITERION",null,register);
        if(result!=-1){
            criterions.add(criterion);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Criterion getCriterion(IdHandler id) {
        if (criterions == null) {
            getListCriterion();
        }
        return criterions.clone(id.toString());
    }

    public InterfaceEvaluationModel getEvaluation(IdHandler id, User user) {
        InformationEvaluation information = null;
        InterfaceEvaluationModel evaluation = null;

        String args[]={id.toString(),id.toString(),id.toString()};
        result=connection.rawQuery(
                    "SELECT "
                            + "E.IDEVALUATION, E.FECHA, E.NUMEROVACAS, E.IDGRANJA, E.NOTA AS NOTAEV, E.ALIMENTACION, E.SALUD, E.COMFORT, E.COMPORTAMIENTO,"
                            + "C.CATEGORIA, PCA.PONDERACION AS PONCATEGORIA, C.NOMBRECRITERIO, PCR.PONDERACION AS PONCRITERIO, V.NOTA, "
                            + "V.IDVALORATION "
                            + "FROM "
                            + "EVALUATION AS E "
                            + "LEFT JOIN CRITERION AS C "
                            + "LEFT JOIN PONDERACIONCATEGORIA AS PCA ON PCA.CATEGORIA=C.CATEGORIA "
                            + "LEFT JOIN VALORATION AS V  ON C.NOMBRECRITERIO=V.NOMBRECRITERIO "
                            + "LEFT JOIN PONDERACIONCRITERIO AS PCR ON PCR.NOMBRECRITERIO=V.NOMBRECRITERIO AND PCR.IDEVALUATION=V.IDEVALUATION "
                            + "WHERE V.IDEVALUATION=? AND PCA.IDEVALUATION=? AND E.IDEVALUATION=?", args);

            boolean firstTime = true;
            while (result.moveToNext()) {
                if (firstTime) {
                    IdHandler idFarm = new IdFarm(result.getInt(result.getColumnIndex("IDGRANJA")));
                    float nota = result.getFloat(result.getColumnIndex("NOTAEV"));
                    float alimentacion = result.getFloat(result.getColumnIndex("ALIMENTACION"));
                    float salud = result.getFloat(result.getColumnIndex("SALUD"));
                    float comfort = result.getFloat(result.getColumnIndex("COMFORT"));
                    float comportamiento = result.getFloat(result.getColumnIndex("COMPORTAMIENTO"));
                    Date fecha = new Date(result.getLong(result.getColumnIndex("FECHA")));
                    int numberCow = result.getInt(result.getColumnIndex("NUMEROVACAS"));
                    information = new InformationEvaluation(
                            id, idFarm, user.getId(), nota, alimentacion,
                            salud, comfort, comportamiento, fecha, numberCow);
                    evaluation = new EvaluationModel(true, information);
                    firstTime = false;
                }

                IdHandler category = new IdCategory(result.getString(result.getColumnIndex("CATEGORIA")));
                evaluation.setWeighing(category, result.getFloat(result.getColumnIndex("PONCATEGORIA")));
                if (result.getString(result.getColumnIndex("NOMBRECRITERIO")) != null) {
                    Criterion criterion = criterions.clone(result.getString(result.getColumnIndex("NOMBRECRITERIO")));
                    criterion.setWeighing(result.getFloat(result.getColumnIndex("PONCRITERIO")));
                    evaluation.add(category, criterion);

                    Valoration valoration = new Valoration(
                            new IdValoration(result.getInt(result.getColumnIndex("IDVALORATION"))),
                            result.getInt(result.getColumnIndex("NOTA")));

                    evaluation.add(category, criterion.getId(), valoration);
                }
            }


        return evaluation;
    }

    private boolean saveEvaluation(InformationEvaluation evaluation) {
            ContentValues register=new ContentValues();
            register.put("IDEVALUATION", Integer.parseInt(evaluation.getIdEvaluation().toString()));
            register.put("IDGRANJA", Integer.parseInt(evaluation.getIdFarm().toString()));
            register.put("USUARIO", evaluation.getIdUser().toString());
            register.put("NOTA", evaluation.getNota());
            register.put("ALIMENTACION", evaluation.getAlimentacion());
            register.put("SALUD", evaluation.getSalud());
            register.put("COMFORT", evaluation.getComfort());
            register.put("COMPORTAMIENTO", evaluation.getComportamiento());
            register.put("FECHA", evaluation.getFecha().getTime());
            register.put("NUMEROVACAS", evaluation.getNumberCows());

            long result=connection.insert("EVALUATION",null,register);

            return result!=-1;
    }

    @Override
    public boolean saveEvaluation(InterfaceEvaluationModel evaluation) {
        boolean resultSave = false;
        try {
            startTransaccion();
            LinkedList<PonderationDB> ponderationCriterion = new LinkedList<>();
            LinkedList<PonderationDB> ponderationCategory = new LinkedList<>();

            if (saveEvaluation(evaluation.getInformation())) {
                for (Category cat : Category.values()) {
                    ponderationCategory.add(new PonderationDB(
                            Integer.parseInt(evaluation.getIdHandler().toString()),
                            Category.getName(cat),
                            evaluation.getWeighing(new IdCategory(cat))));

                    for (Criterion criterio : evaluation.getListCriterion(cat)) {
                        ponderationCriterion.add(new PonderationDB(
                                Integer.parseInt(evaluation.getIdHandler().toString()),
                                criterio.getName(), criterio.getWeighing()));
                        for (Valoration valoration : evaluation.listOfCriterion(criterio.getId())) {
                            if (!saveValoration(
                                    Integer.parseInt(evaluation.getIdHandler().toString()),
                                    criterio, valoration)) {
                                rollback();
                                break;
                            }
                        }
                    }
                }
                saveCriterionPonderation(ponderationCriterion);
                saveCategoryPonderation(ponderationCategory);
                resultSave = true;
                commit();
            }
        } catch (NumberFormatException ex) {
            rollback();
            System.out.println(ex.toString());
        }
        return resultSave;
    }

    public LinkedList<Valoration> getAllValorationsFrom(IdHandler idEvaluation) {
        LinkedList<Valoration> valores = new LinkedList<>();

            String args[]={idEvaluation.toString()};
            result = connection.rawQuery(
                    "SELECT V.IDVALORATION, V.IDEVALUATION, V.NOTA,C.CATEGORIA, V.NOMBRECRITERIO AS NOMBRE "
                            + "FROM VALORATION AS V INNER JOIN CRITERION AS C ON V.NOMBRECRITERIO "
                            + " = C.NOMBRECRITERIO AND V.IDEVALUATION = ?", args);

            while (result.moveToNext()) {
                String nombreCriterio = result.getString(result.getColumnIndex("nombre"));
                IdHandler idCriterio = new IdCriterion(nombreCriterio);
                String categoria = result.getString(result.getColumnIndex("categoria"));
                IdHandler idCategoria = new IdCategory(categoria);
                int numValoration = result.getInt(result.getColumnIndex("IDEVALUATION"));
                IdHandler idValoration = new IdEvaluation(numValoration);
                float nota = result.getFloat(result.getColumnIndex("NOTA"));
                Valoration val = new Valoration(idValoration, nota);
                valores.add(val);
            }

        return valores;

    }

    @Override
    public boolean saveModifiedEvaluation(InterfaceEvaluationModel evaluation) {
        boolean resultSave = true;
        startTransaccion();
        resultSave = resultSave & removeEvaluation(evaluation.getIdHandler());
        if (resultSave == false) {
            rollback();
        } else {
            resultSave = resultSave & saveEvaluation(evaluation);
        }
        return resultSave;
    }

    @Override
    public boolean newFarm(Farm farm) {
        ContentValues register=new ContentValues();
        register.put("IDGRANJA", farm.getIdFarm().toString());
        register.put("NOMBREGRANJA", farm.getFarmName());
        register.put("NOMBREUSUARIO", farm.getNameUser());
        register.put("DIRECCION", farm.getAddress());
        register.put("NOMBREGANADERO", farm.getFarmerName());
        register.put("DNIGANADERO", farm.getDniFarmer());
        register.put("NUMEROVACAS", farm.getCowNumber());
        register.put("IDENTIFICADORGRANJA", farm.getFarmIdentifier());
        register.put("OTROSDATOS", farm.getOtherData());

        long result=connection.insert("FARM", null, register);
        return result!=-1;
    }

    private boolean switchFarm(IdHandler id, boolean enabled) {
        String isEnabled;
        if (enabled) {
            isEnabled = "TRUE";
        } else {
            isEnabled = "FALSE";
        }

        String args[]={id.toString()};
        ContentValues register=new ContentValues();
        register.put("ENABLED", isEnabled);
        long result=connection.update("FARM", register, "IDGRANJA=?", args);
        return result!=-1;
    }

    @Override
    public boolean removeFarm(Farm farm) {
        String args[]={farm.getIdFarm().toString()};
        long result=connection.delete("FARM", "IDGRANJA=?", args);
        return result!=-1;
    }

    @Override
    public boolean modifiedFarm(Farm farm) {
        String args[]={farm.getIdFarm().toString()};
        ContentValues register=new ContentValues();
        register.put("NOMBREGRANJA", farm.getFarmName());
        register.put("DIRECCION", farm.getAddress());
        register.put("NOMBREGANADERO", farm.getFarmerName());
        register.put("IDENTIFICADORGRANJA", farm.getFarmIdentifier());
        register.put("OTROSDATOS", farm.getOtherData());
        register.put("DNIGANADERO", farm.getDniFarmer());
        register.put("NUMEROVACAS", farm.getCowNumber());
        long result=connection.update("FARM", register, "IDGRANJA=?", args);
        return result!=-1;
    }

    @Override
    public boolean disableFarm(IdHandler id) {
        return switchFarm(id, false);
    }

    @Override
    public boolean enableFarm(IdHandler id) {
        return switchFarm(id, true);
    }

    private boolean isCriterionInitialized() {
        return criterionInitialized;
    }

    @Override
    public LinkedList<Criterion> getListCriterion() {
        if (!isCriterionInitialized()) {
            criterionInitialized = true;

                result = connection.rawQuery("SELECT * FROM CRITERION",null);

                while (result.moveToNext()) {
                    Criterion criterion = new Criterion(
                            result.getString(result.getColumnIndex("NOMBRECRITERIO")),
                            new IdCategory(result.getString(result.getColumnIndex("CATEGORIA"))),
                            result.getString(result.getColumnIndex("DESCRIPCION")),
                            result.getString(result.getColumnIndex("HELP")));

                    criterions.add(criterion);
                }
        }
        return criterions.getList();
    }

    @Override
    public boolean removeCriterion(IdHandler idCriterion) {
        String args[]={idCriterion.toString()};
        connection.delete("CRITERION","NOMBRECRITERIO=?", args);
        criterions.remove(new Criterion(idCriterion, null));
        return true;
    }

    @Override
    public int nextIdEvaluation() {
        int id = 1;
            result = connection.rawQuery("SELECT MAX(IDEVALUATION)AS IDEVALUATION FROM EVALUATION",null);

            if (result.moveToFirst()) {
                id = result.getInt(result.getColumnIndex("idevaluation")) + 1;
            }
        return id;
    }

    @Override
    public int nextIdFarm() {
        int id = 1;
            result = connection.rawQuery("SELECT MAX(IDGRANJA) AS IDGRANJA FROM FARM",null);

            if (result.moveToFirst()) {
                id = result.getInt(result.getColumnIndex("IDGRANJA")) + 1;
            }

        return id;
    }

    @Override
    public int nextIdValoration() {
        if (nextIdValoration == -1) {
                result=connection.rawQuery("SELECT MAX(IDVALORATION) AS IDVALORATION FROM VALORATION",null);
                if (result.moveToFirst()) {
                    nextIdValoration = result.getInt(result.getColumnIndex("idvaloration")) + 1;
                }

        } else {
            nextIdValoration++;
        }
        return nextIdValoration;
    }

    @Override
    public Farm getFarm(IdHandler idUser, IdHandler idFarm)    {
        Farm farm = null;
        String args[]={idFarm.toString()};
        result=connection.rawQuery("SELECT * FROM FARM WHERE IDGRANJA=?",args);
            if (result.moveToFirst()) {
                farm = new Farm(idFarm, result.getString(result.getColumnIndex("NOMBREGRANJA")),
                        result.getString(result.getColumnIndex("IDENTIFICADORGRANJA")),
                        result.getString(result.getColumnIndex("DIRECCION")),
                        result.getString(result.getColumnIndex("NOMBREGANADERO")),
                        result.getString(result.getColumnIndex("DNIGANADERO")),
                        result.getInt(result.getColumnIndex("NUMEROVACAS")), idUser,
                        result.getString(result.getColumnIndex("OTROSDATOS")));
            }

        return farm;
    }

    @Override
    public LinkedList<Farm> getListFarms(IdHandler idUser) {

        LinkedList<Farm> lista = new LinkedList<>();
        String args[]={idUser.toString()};
            result=connection.rawQuery("SELECT * FROM FARM WHERE NOMBREUSUARIO=? AND ENABLED='TRUE'", args);

            while (result.moveToNext()) {
                int idGranja = result.getInt(result.getColumnIndex("IDGRANJA"));
                String nombreGranja = result.getString(result.getColumnIndex("NOMBREGRANJA"));
                String direccion = result.getString(result.getColumnIndex("DIRECCION"));
                String nombreGanadero = result.getString(result.getColumnIndex("NOMBREGANADERO"));
                String dniGanadero = result.getString(result.getColumnIndex("DNIGANADERO"));
                int numeroVacas = result.getInt(result.getColumnIndex("NUMEROVACAS"));
                String nombreUsuario = result.getString(result.getColumnIndex("NOMBREUSUARIO"));
                String identificadorGranja = result.getString(result.getColumnIndex("IDENTIFICADORGRANJA"));
                String otrosDatos = result.getString(result.getColumnIndex("OTROSDATOS"));

                IdHandler usuario = new IdUser(nombreUsuario);
                IdHandler granja = new IdFarm(usuario, idGranja);

                Farm farm = new Farm(granja, nombreGranja, identificadorGranja,
                        direccion, nombreGanadero, dniGanadero, numeroVacas,
                        usuario, otrosDatos);

                lista.add(farm);
            }

            for (Farm farm : lista) {
                LinkedList<InterfaceEvaluationModel> listado = getListEvaluations(farm.getIdFarm());
                for (InterfaceEvaluationModel interfaceEvaluationModel : listado) {
                    farm.addEvaluation(interfaceEvaluationModel);
                }
            }



        return lista;
    }

    @Override
    public LinkedList<User> getListUsers() {

        LinkedList<User> listUsers = new LinkedList<>();
        result=connection.rawQuery("SELECT * FROM USUARIO",null);

            while (result.moveToNext()) {
                String a = result.getString(result.getColumnIndex("nombreusuario"));
                String b = result.getString(result.getColumnIndex("contrasenia"));
                String c = result.getString(result.getColumnIndex("rol"));
                Rol rol;
                if (c.equalsIgnoreCase("veterinario")) {
                    rol = Rol.VETERINARIO;
                } else {
                    rol = Rol.ADMINISTRADOR;
                }

                IdHandler idUser = new IdUser(a);
                User actual = new User(idUser, b, rol, true);
                listUsers.add(actual);

            }

        return listUsers;
    }

    @Override
    public LinkedList<InterfaceEvaluationModel> getListEvaluations(IdHandler idUser, IdHandler idFarm) {
        LinkedList<InterfaceEvaluationModel> lista = null;
        String args[]={idUser.toString(),idFarm.toString()};
            result=connection.rawQuery("SELECT * FROM EVALUATION WHERE USUARIO=? AND IDGRANJA=?", args);

            lista = new LinkedList<>();
            while (result.moveToNext()) {
                lista.add(new EvaluationModel(false,
                        new InformationEvaluation(
                                (IdHandler) new IdEvaluation(result.getInt(result.getColumnIndex("IDEVALUATION"))),
                                idFarm, result.getFloat(result.getColumnIndex("NOTA")),
                                result.getFloat(result.getColumnIndex("ALIMENTACION")),
                                result.getFloat(result.getColumnIndex("SALUD")),
                                result.getFloat(result.getColumnIndex("COMFORT")),
                                result.getFloat(result.getColumnIndex("COMPORTAMIENTO")),
                                new Date(result.getLong(result.getColumnIndex("FECHA"))),
                                result.getInt(result.getColumnIndex("NUMEROVACAS")))));
            }

        return lista;
    }

    @Override
    public LinkedList<Farm> getAllFarms() {
        LinkedList<Farm> farms = new LinkedList<>();

            result=connection.rawQuery("SELECT * FROM FARM",null);

            while (result.moveToNext()) {
                Farm farm = new Farm(new IdFarm(result.getInt(result.getColumnIndex("IDGRANJA"))),
                        result.getString(result.getColumnIndex("NOMBREGRANJA")),
                        result.getString(result.getColumnIndex("IDENTIFICADORGRANJA")),
                        result.getString(result.getColumnIndex("DIRECCION")),
                        result.getString(result.getColumnIndex("NOMBREGANADERO")),
                        result.getString(result.getColumnIndex("DNIGANADERO")),
                        result.getInt(result.getColumnIndex("NUMEROVACAS")),
                        new IdUser(result.getString(result.getColumnIndex("NOMBREUSUARIO"))),
                        result.getString(result.getColumnIndex("OTROSDATOS")),
                        result.getInt(result.getColumnIndex("ENABLED"))>0);
                farms.add(farm);
            }

        return farms;
    }

    @Override
    public LinkedList<EvaluationModel> getAllEvaluations() {
        LinkedList<EvaluationModel> model = new LinkedList<>();

            result=connection.rawQuery("SELECT * FROM EVALUATION", null);

            while (result.moveToNext()) {
                InformationEvaluation info = new InformationEvaluation(
                        new IdEvaluation(result.getInt(result.getColumnIndex("IDEVALUATION"))),
                        new IdFarm(result.getInt(result.getColumnIndex("IDGRANJA"))),
                        new IdUser(result.getString(result.getColumnIndex("USUARIO"))),
                        result.getInt(result.getColumnIndex("NOTA")),
                        result.getInt(result.getColumnIndex("ALIMENTACION")),
                        result.getInt(result.getColumnIndex("SALUD")),
                        result.getInt(result.getColumnIndex("COMFORT")),
                        result.getInt(result.getColumnIndex("COMPORTAMIENTO")),
                        new Date(result.getLong(result.getColumnIndex("FECHA"))), //FECHAAA!!!
                        result.getInt(result.getColumnIndex("NUMEROVACAS")));
                EvaluationModel mo = new EvaluationModel(false, info);
                model.add(mo);
            }

        return model;
    }

    @Override
    public LinkedList<ValorationDB> getAllValorations() {
        LinkedList<ValorationDB> valores = new LinkedList<>();

            result = connection.rawQuery("SELECT * FROM VALORATION", null);
            while (result.moveToNext()) {
                int numValoration = result.getInt(result.getColumnIndex("idvaloration"));
                IdHandler idValoration = new IdEvaluation(numValoration);
                float nota = result.getFloat(result.getColumnIndex("nota"));
                ValorationDB val = new ValorationDB(idValoration,
                        new IdCriterion(result.getString(result.getColumnIndex("NOMBRECRITERIO"))),
                        new IdEvaluation(result.getInt(result.getColumnIndex("IDEVALUATION"))),
                        nota);
                valores.add(val);
            }


        return valores;
    }

    @Override
    public boolean saveValoration(int idEvaluation, Criterion criterion, Valoration valoration) {
        ContentValues register=new ContentValues();
        register.put("IDVALORATION", Integer.parseInt(valoration.getId().toString()));
        register.put("NOMBRECRITERIO", criterion.getIdCriterion().toString());
        register.put("IDEVALUATION", idEvaluation);
        register.put("NOTA", valoration.getNota());
        register.put("PONDERACION", valoration.getWeighing());

        long result=connection.insert("VALORATION",null,register);
        return result!=-1;
    }

    @Override
    public boolean saveValoration(ValorationDB valoration) {
        ContentValues register=new ContentValues();
        register.put("IDVALORATION", Integer.parseInt(valoration.getIdValoration().toString()));
        register.put("NOMBRECRITERIO", valoration.getIdCriterion().toString());
        register.put("IDEVALUATION", Integer.parseInt(valoration.getIdEvaluation().toString()));
        register.put("NOTA", valoration.getNota());
        register.put("PONDERACION", valoration.getWeighing());

        long result=connection.insert("VALORATION",null,register);
        return result!=-1;
    }

    @Override
    public LinkedList<PonderationDB> getCriterionPonderation() {
        LinkedList<PonderationDB> lista = new LinkedList<>();

            result=connection.rawQuery("SELECT * FROM PONDERACIONCRITERIO", null);

            while (result.moveToNext()) {
                int idEval = result.getInt(result.getColumnIndex("idevaluation"));
                String categoria = result.getString(result.getColumnIndex("nombrecriterio"));
                float b = result.getFloat(result.getColumnIndex("ponderacion"));
                PonderationDB pon = new PonderationDB(idEval, categoria, b);
                lista.add(pon);
            }


        return lista;
    }

    @Override
    public LinkedList<PonderationDB> getCategoryPonderation() {
        LinkedList<PonderationDB> lista = new LinkedList<>();
            result = connection.rawQuery("SELECT * FROM PONDERACIONCATEGORIA",null);
            while (result.moveToNext()) {
                int idEval = result.getInt(result.getColumnIndex("idevaluation"));
                String categoria = result.getString(result.getColumnIndex("categoria"));
                float b = result.getFloat(result.getColumnIndex("ponderacion"));
                PonderationDB pon = new PonderationDB(idEval, categoria, b);

                lista.add(pon);
            }

        return lista;
    }

    @Override
    public boolean saveCategoryPonderation(LinkedList<PonderationDB> list) {
        boolean correct = true;
        for (PonderationDB ponderationDB : list) {
            correct = correct && newPonderacionCategoria(
                    ponderationDB.getId(), ponderationDB.getName(), ponderationDB.getPonderation());
        }
        return correct;
    }

    @Override
    public boolean saveCriterionPonderation(LinkedList<PonderationDB> list) {
        boolean correct = true;
        for (PonderationDB ponderationDB : list) {
            correct = correct && newPonderacionCriterio(
                    ponderationDB.getId(), ponderationDB.getName(), ponderationDB.getPonderation());
        }
        return correct;
    }

    private boolean newPonderacionCategoria(int idEvaluation, String idCategoria, float ponderacion) {
            ContentValues register=new ContentValues();
            register.put("IDEVALUATION", idEvaluation);
            register.put("CATEGORIA", idCategoria.toString());
            register.put("PONDERACION", ponderacion);

            long result=connection.insert("PONDERACIONCATEGORIA",null,register);

            return result!=-1;
    }

    @Override
    public boolean removeEvaluation(IdHandler id) {
        String args[]={id.toString()};

        connection.delete("VALORATION", "IDEVALUATION=?", args);
        connection.delete("PONDERACIONCRITERIO", "IDEVALUATION=?", args);
        connection.delete("PONDERACIONCATEGORIA", "IDEVALUATION=?", args);
        connection.delete("EVALUATION", "IDEVALUATION=?", args);

        return true;
    }

    private boolean newPonderacionCriterio(int idEvaluation, String idCriterio, float ponderacion) {
            ContentValues register=new ContentValues();
            register.put("IDEVALUATION", idEvaluation);
            register.put("NOMBRECRITERIO", idCriterio.toString());
            register.put("PONDERACION", ponderacion);

            long result=connection.insert("PONDERACIONCRITERIO",null,register);

        return result!=-1;

    }
}
