package es.unileon.happycow.database;

import android.content.Context;

import es.unileon.happycow.database.concreteDatabase.AndroidSQLite;


/**
 *
 * @author dorian
 */
public class Database {
    private static DataBaseOperations instance;
    private static TypeDatabase type=TypeDatabase.ANDROID_SQLITE;
    
    private static void createDatabase(Context contexto){
        switch(type){
            case ANDROID_SQLITE:
                instance=new AndroidSQLite(contexto.getApplicationContext());
        }
    }

    public static DataBaseOperations getInstance(Context context) {
        if(instance==null){
            createDatabase(context);
        }
        return instance;
    }
}
