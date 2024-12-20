package com.example.cavaleralexandru;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ExtractCSV extends AsyncTask<URL,Void,String> {
    public List<Job> jobListCSV=new ArrayList<>();
    @Override
    protected String doInBackground(URL... urls) {
        HttpURLConnection conexiune=null;
        try {
            conexiune=(HttpURLConnection) urls[0].openConnection();
            conexiune.setRequestMethod("GET");

            InputStream is=conexiune.getInputStream();
            InputStreamReader isr=new InputStreamReader(is);
            BufferedReader bf=new BufferedReader(isr);

            String linie=null;
            String rezultat="";
            while((linie=bf.readLine())!=null)
                rezultat+=linie+"\n";

            parsareCSV(rezultat);
            return rezultat;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void parsareCSV(String csvStr) {
        if(csvStr!=null){
            String[] linii = csvStr.split("\n");

            for (int i =0; i<linii.length; i++) {
                String linie = linii[i].trim();

                String[] valori = linie.split(",");
                //cum parsez int?
                int id=Integer.parseInt(valori[0].trim());
                String denumire=valori[1].trim();
                String firma=valori[2].trim();
                float salariu=Float.parseFloat(valori[3].trim());

                Job job=new Job(id,denumire,firma,salariu);
                jobListCSV.add(job);

            }
        }
    }

}
