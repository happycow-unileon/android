package es.unileon.happycow.database.concreteDatabase;

import es.unileon.happycow.R;
import android.content.Context;
import android.os.AsyncTask;
import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Xml;
import org.xml.sax.Attributes;
import java.io.InputStream;
import java.util.LinkedList;

import es.unileon.happycow.database.Database;

/**
 *
 * @author dorian
 */
public class InsertCriterion  extends AsyncTask<Void,Void,Boolean> {

    private final LinkedList<String> inserts;
    private StringBuilder aSentence;
    private Context context;

    public InsertCriterion(Context context) {
        this.inserts = new LinkedList<>();
        this.context=context;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        RootElement root = new RootElement("databasecriterion");
        Element criterion = root.getChild("criterion");


        criterion.setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                aSentence=new StringBuilder();
                aSentence.append("INSERT INTO CRITERION (NOMBRECRITERIO,DESCRIPCION,HELP,CATEGORIA) VALUES ");
            }
        });

        criterion.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                aSentence.append(")");
                inserts.add(aSentence.toString());
            }
        });

        criterion.getChild("name").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String s) {
                aSentence.append("('");
                aSentence.append(s);
                aSentence.append("','");
            }
        });

        criterion.getChild("description").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String s) {
                aSentence.append(s);
                aSentence.append("','");
            }
        });


        criterion.getChild("help").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String s) {
                aSentence.append(s);
                aSentence.append("','");
            }
        });


        criterion.getChild("category").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String s) {
                aSentence.append(s);
                aSentence.append("'");
            }
        });

        try
        {
            Xml.parse(this.getInputStream(),
            Xml.Encoding.UTF_8,
            root.getContentHandler());

        }catch (Exception ex){
        }

        return true;
    }

    private InputStream getInputStream(){
            InputStream fraw =context.getResources().openRawResource(R.raw.criterionbase);
            return fraw;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if(aBoolean){
            //guardar en base de datos
            Database.getInstance(null).executeRawQuery(inserts);
        }
    }

}
