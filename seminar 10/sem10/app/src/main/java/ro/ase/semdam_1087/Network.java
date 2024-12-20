package ro.ase.semdam_1087;

import android.os.AsyncTask;

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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Network extends AsyncTask<URL, Void, InputStream> {

    InputStream ist = null;
    static String rezultat = "";
    CursValutar cv = null;

    @Override
    protected InputStream doInBackground(URL... urls) {

        HttpURLConnection conexiune = null;
        try {
            conexiune = (HttpURLConnection) urls[0].openConnection();
            conexiune.setRequestMethod("GET");
            ist = conexiune.getInputStream();

            //scenariul 1 - parsare InputStream ca XML
            Parsing(ist);

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

    public void Parsing(InputStream ist)
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document domDoc = db.parse(ist);
            domDoc.getDocumentElement().normalize();

            cv = new CursValutar();

            Node cube = getNodeByName("Cube", domDoc.getDocumentElement());
            if(cube!=null)
            {
                String dataCurs = getAttributeValue(cube, "date");
                cv.setDataCurs(dataCurs);

                NodeList listaCopii = cube.getChildNodes();
                for(int i=0;i<listaCopii.getLength();i++)
                {
                    Node rate = listaCopii.item(i);
                    String atribut = getAttributeValue(rate, "currency");
                    if(atribut.equals("EUR"))
                        cv.setCursEUR(rate.getTextContent());
                    if(atribut.equals("USD"))
                        cv.setCursUSD(rate.getTextContent());
                    if(atribut.equals("GBP"))
                        cv.setCursGBP(rate.getTextContent());
                    if(atribut.equals("XAU"))
                        cv.setCursXAU(rate.getTextContent());
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
    }
}
