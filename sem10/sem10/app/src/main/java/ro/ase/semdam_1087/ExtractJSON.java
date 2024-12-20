package ro.ase.semdam_1087;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ExtractJSON extends AsyncTask<URL, Void, String> {

    public List<Masina> masinaListJSON = new ArrayList<>();

    @Override
    protected String doInBackground(URL... urls) {

        HttpURLConnection conexiune = null;
        try {
            conexiune = (HttpURLConnection) urls[0].openConnection();
            conexiune.setRequestMethod("GET");
            InputStream ist = conexiune.getInputStream();

            //scenariul 2 - conversie InputStream in String
            InputStreamReader isr = new InputStreamReader(ist);
            BufferedReader br =  new BufferedReader(isr);
            String linie = null;
            String rezultat = "";
            while ((linie = br.readLine())!=null)
                rezultat+=linie;

            //parsare JSON
            parsareJSON(rezultat);

            return rezultat;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //https://pastebin.com/raw/L84K1DxZ

    private void parsareJSON(String jsonStr){
        if(jsonStr!=null)
        {
            try {
                JSONObject jsonObject=new JSONObject(jsonStr);
                JSONArray masini=jsonObject.getJSONArray("masini");
                for(int i=0;i<masini.length();i++){
                    JSONObject obj=masini.getJSONObject(i);

                    String marca=obj.getString("Marca");
                    Date dataFabricatiei=new Date(obj.getString("DataFabricatiei"));
                    String culoare=obj.getString("Culoare");
                    float pret=Float.parseFloat(obj.getString("Pret"));
                    String motorizare=obj.getString("Motorizare");

                    Masina masina=new Masina(marca,dataFabricatiei,pret,culoare,motorizare);
                    masinaListJSON.add(masina);
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
