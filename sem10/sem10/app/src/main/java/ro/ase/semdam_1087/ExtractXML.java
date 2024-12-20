package ro.ase.semdam_1087;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ExtractXML extends AsyncTask<URL, Void, InputStream> {

    InputStream ist = null;

    public List<Masina> masinaList = new ArrayList<>();

    @Override
    protected InputStream doInBackground(URL... urls) {

        HttpURLConnection conexiune = null;
        try {
            conexiune = (HttpURLConnection) urls[0].openConnection();
            conexiune.setRequestMethod("GET");
            ist = conexiune.getInputStream();

            //scenariul 1 - parsare InputStream ca XML
            masinaList = Parsing(ist);

            //scenariul 2 - conversie InputStream in String
            /*InputStreamReader isr = new InputStreamReader(ist);
            BufferedReader br =  new BufferedReader(isr);
            String linie = null;
            while ((linie = br.readLine())!=null)
                rezultat+=linie;*/

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ist;
    }

    public static Node getNodeByName(String nodeName, Node parentNode)
            throws Exception
    {
        if(parentNode.getNodeName().equals(nodeName))
            return parentNode;
        NodeList list = parentNode.getChildNodes();
        for(int i=0;i<list.getLength();i++)
        {
            Node node = getNodeByName(nodeName, list.item(i));
            if(node!=null)
                return node;
        }
        return null;
    }

    public static String getAttributeValue(Node node, String attrName)
    {
        try
        {
            return ((Element)node).getAttribute(attrName);
        }
        catch (Exception ex)
        {
            return "";
        }
    }

    public List<Masina> Parsing(InputStream ist)
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document domDoc = db.parse(ist);
            domDoc.getDocumentElement().normalize();

            Node parinte = getNodeByName("Masini", domDoc.getDocumentElement());
            if(parinte!=null)
            {
                NodeList listaCopii = parinte.getChildNodes();
                for(int i=0;i<listaCopii.getLength();i++)
                {
                    Node copil = listaCopii.item(i);
                    if(copil!=null && copil.getNodeName().equals("Masina"))
                    {
                        Masina masina = new Masina();

                        NodeList taguri = copil.getChildNodes();
                        for(int j=0;j<taguri.getLength();j++)
                        {
                            Node tag = taguri.item(j);

                            String atribut = getAttributeValue(tag, "atribut");
                            if(atribut.equals("Marca"))
                                masina.setMarca(tag.getTextContent());
                            if(atribut.equals("DataFabricatiei"))
                                masina.setDataFabricatiei(new Date(tag.getTextContent()));
                            if(atribut.equals("Pret"))
                                masina.setPret(Float.parseFloat(tag.getTextContent()));
                            if(atribut.equals("Culoare"))
                                masina.setCuloare(tag.getTextContent());
                            if(atribut.equals("Motorizare"))
                                masina.setMotorizare(tag.getTextContent());
                        }
                        masinaList.add(masina);
                    }
                }
            }

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return masinaList;
    }
}
