package com.example.test01;

import android.os.Bundle;
import android.R.string;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.view.View.OnClickListener;

import java.lang.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.os.AsyncTask;
import android.renderscript.Element;


//public static final String URL = "http://download.finance.yahoo.com/d/quotes.csv?s=INFY.BO,RELIANCE.NS,TCS.BO&f=sl1d1t1c1ohgv&e=.csv' and columns='symbol,price,date,time,change,col1,high,low,col2";
//"http://10.0.2.2:8080/HttpGetServlet/HelloWorldServlet";

public class MainActivity extends Activity implements OnClickListener {

	private EditText txt01;	
	private EditText txt02;
	private EditText txtStockCode;
	Button btnGet;
	Button btnRun;
	
	public DocumentBuilderFactory factory;
	public DocumentBuilder docBuilder;
	public Document doc;
	
	// YQL for Stock Quote
	//public static final String URL = "http://www.google.com";
	//public static final String URL = "http://download.finance.yahoo.com/d/quotes.csv?s=INFY.BO,RELIANCE.NS,TCS.BO&f=sl1d1t1c1ohgv&e=.csv' and columns='symbol,price,date,time,change,col1,high,low,col2";
	//public static final String URL = "http://finance.yahoo.com/d/quotes.csv?s=RHT+MSFT&f=sb2b3jk";
	public static final String URL1 = "http://finance.yahoo.com/d/quotes.csv?s=";
	public static final String URL2 = "&f=sb2b3jk";
	public static String URL = "";
	public static String YQL = "http://query.yahooapis.com/v1/public/yql?q=use%20%22http%3A%2F%2Fgithub.com%2Fspullara%2Fyql-tables%2Fraw%2Fd60732fd4fbe72e5d5bd2994ff27cf58ba4d3f84%2Fyahoo%2Ffinance%2Fyahoo.finance.quotes.xml%22%20as%20quotes%3B%0Aselect%20*%20from%20quotes%20where%20symbol%20in%20(%22AAPL%22)&diagnostics=true";
	public static String YQL1 = "http://query.yahooapis.com/v1/public/yql?q=use%20%22http%3A%2F%2Fgithub.com%2Fspullara%2Fyql-tables%2Fraw%2Fd60732fd4fbe72e5d5bd2994ff27cf58ba4d3f84%2Fyahoo%2Ffinance%2Fyahoo.finance.quotes.xml%22%20as%20quotes%3B%0Aselect%20*%20from%20quotes%20where%20symbol%20in%20(%22";
	public static String YQL2 = "%22)&diagnostics=true";
	
	
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt01 = (EditText) this.findViewById(R.id.txt01);
        txtStockCode = (EditText) this.findViewById(R.id.txtStockCode);
        txt02 = (EditText) this.findViewById(R.id.txt02);
        btnGet = (Button) this.findViewById(R.id.btnGet); 
        //btnRun = (Button) this.findViewById(R.id.btnRun); 
       
        //Calling OnClick Asynchronously
        btnGet.setOnClickListener(this);
        //btnRun.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /*
    public void btnRun_OnClick(View view)
    {    	    	    	
    	txt01.setText(String .valueOf(931));
    }
    */
      
    
    // Implement Overriding OnClickListener Method
	@Override
	public void onClick(View v) {
		//URL = URL1 + txtStockCode.getText() + URL2;
		URL = "";
		URL = YQL1 + txtStockCode.getText() + YQL2;
		// TODO Auto-generated method stub
		GetXMLTask task = new GetXMLTask();
        task.execute(new String[] { URL });
	}
	
    
	// Method Calling the Http Address
    private class GetXMLTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String output = null;
            for (String url : urls) {
                output = getOutputFromUrl(url);
            }
            return output;
        }
 
        private String getOutputFromUrl(String url) {                	
  /*
        	String output = "";
            try {
                InputStream stream = getHttpConnection(url);
                BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
                String s = "";
                while ((s = buffer.readLine()) != null)
                    output+=s;
            } catch (IOException e1) {
                e1.printStackTrace();
                output = e1.getMessage();
            }
            return output.toString();
*/
        	
            StringBuffer output = new StringBuffer("");
            try 
            {
                InputStream stream = getHttpConnection(url);
                
                BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
                String s = "";
                while ((s = buffer.readLine()) != null)
                    output.append(s);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return output.toString();
            
        }
 
        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString) throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
 
            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();
 
                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) 
                {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
 
        
        @Override
        protected void onPostExecute(String output) {
        	//print raw
        	txt02.setText(output);
        	
        	//txt01.setText(output);
        	try
        	{
	        	factory = DocumentBuilderFactory.newInstance();
	        	docBuilder = factory.newDocumentBuilder();
	        	StringReader sr = new StringReader(output);
	        	InputSource is = new InputSource(sr);
	        	doc = docBuilder.parse(is);
        	}
        	catch (Exception ex)
        	{
        		
        	}
        	
        	//retrieve value from xml      
        	NodeList nl = doc.getElementsByTagName("LastTradePriceOnly");    	//the tag name
        	int num = nl.getLength();                               //length of list
        	
        	String v1 = nl.item(0).getTextContent();
        	String v2 = nl.item(0).getNodeName();		
        	String strLastPrice = nl.item(0).getFirstChild().getNodeValue();
    
        	//print specifics
        	txt01.setText(strLastPrice);
        	
        }
        
        
        //***********************************************************************************************************************//
        /*
         * 
         * XML Parser Helper Functions
         * 
         * */
        public String getXmlFromUrl(String url) 
        {
            String xml = null;
     
            try {
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
     
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                xml = EntityUtils.toString(httpEntity);
     
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // return XML
            return xml;
        }
        
        public Document getDomElement(String xml)
        {
            Document doc = null;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            try {
     
                DocumentBuilder db = dbf.newDocumentBuilder();
     
                InputSource is = new InputSource();
                    is.setCharacterStream(new StringReader(xml));
                    doc = db.parse(is); 
     
                } catch (ParserConfigurationException e) {
                    Log.e("Error: ", e.getMessage());
                    return null;
                } catch (SAXException e) {
                    Log.e("Error: ", e.getMessage());
                    return null;
                } catch (IOException e) {
                    Log.e("Error: ", e.getMessage());
                    return null;
                }
                    // return DOM
                return doc;                
        }

        public String getValue(Element e, String str) 
        {              	
            NodeList n = ((Document) e).getElementsByTagName(str);        
            return this.getElementValue(n.item(0));
        }
        
        public final String getElementValue( Node elem ) 
        {
            Node child;
            if( elem != null){
                if (elem.hasChildNodes()){
                    for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                        if( child.getNodeType() == Node.TEXT_NODE  ){
                            return child.getNodeValue();
                        }
                    }
                }
            }
            return "";
        } 
        

         
        
      //***********************************************************************************************************************//
    }
    
    
    

}
