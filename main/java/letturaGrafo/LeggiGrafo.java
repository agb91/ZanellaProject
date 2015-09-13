package letturaGrafo;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import org.xml.sax.SAXException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LeggiGrafo {
	
	String path;
	static Boolean validato=false;
	
  public LeggiGrafo (String _path)  //costruito con il path di un file xml
  {
	  path=_path;
  }
  
 
  //con la validazione rispetto ad un documento xsd scremo errori grossolani come
  //tag con errori ortografici o xml non ben formato
  static boolean validate(String xml, String xsd)
  {
	  if(!validato)
	  {
		  boolean risp = false;
		      try {
			// 1. Lookup a factory for the W3C XML Schema language
		      SchemaFactory factory = 
		          SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		      
		      // 2. Compile the schema. 
		      // Here the schema is loaded from a java.io.File, but you could use 
		      // a java.net.URL or a javax.xml.transform.Source instead.
		      File schemaLocation = new File("src/"+xsd);
		      Schema schema = factory.newSchema(schemaLocation);
		  
		      // 3. Get a validator from the schema.
		      Validator validator = schema.newValidator();
		      
		      // 4. Parse the document you want to check.
		      Source source = new StreamSource(xml);
		      
		      // 5. Check the document
	
	          validator.validate(source);
	          System.out.println(xml + " is valid.");
	          risp = true;
	          return true;
	      }
	      catch (SAXException ex) {
	          System.out.println(xml + " is not valid because ");
	          System.out.println(ex.getMessage());
	          System.exit(1);
	          risp = false;
	          return false;
	      } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	        System.out.println(e.getMessage());
	        System.exit(1);
		}
			return risp;  
	  }
	  else return true;
  }
    
  public int getNumNodi() //per sapere quanti nodi ci sono nel file xml
  //NDR: INTENDO QUANTI TAG DI TIPO NODO!
  {
	  int num = 0;
	  try {		  
	    	File fXmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		 
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();	 
			NodeList nList = doc.getElementsByTagName("grafo");
			Node nNode = nList.item(0);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				num = eElement.getElementsByTagName("nodo").getLength();
			}
	  } catch (Exception e) {
			e.printStackTrace();
            System.out.println(e.getMessage());
            System.exit(1);
	  }
	  return num;
  }
 
  public String leggi(int num) {   //LEGGE il contenuto di ogni nodo con una stringa che poi
	  //verrà interpretata dal costruttore di Nodo
	  
	  int problemi = 0;
	  String contenuto = "";
	  try {		
		  if(validate(path,"validatore.xsd")){//se non supera la validazione base col documento non si procede..
			validato = true;
			File fXmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		 
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();	 
			NodeList nList = doc.getElementsByTagName("grafo");
			Node nNode = nList.item(0);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				Element interno = (Element) eElement.getElementsByTagName("nodo").item(num);

				// il numero delle transizioni è cooerente col  numero dei vari elementi che le caratterizzano?
				//altrimenti c'è un errore
		 		int nTransizioni = (interno.getElementsByTagName("*").getLength()-1)/4;
		 		int Nnomi = interno.getElementsByTagName("lettera").getLength();
		 		int Nlettere = interno.getElementsByTagName("osservabilita").getLength();
		 		int Nguasti = interno.getElementsByTagName("guasto").getLength();
		 		int Ndestinazioni = interno.getElementsByTagName("destinazione").getLength();

		 		if(!(nTransizioni==Nnomi && nTransizioni==Nlettere && nTransizioni == Nguasti && nTransizioni == Ndestinazioni))
		 		{
		 			System.out.println("pezzi mancanti in una o più transizioni o stati");
		 			System.exit(2);
		 			problemi=1;
		 		}
		 		String cNome = interno.getElementsByTagName("nome").item(0).getTextContent();
		 		if(cNome.length()==0)//verifica il nome
		 		{
		 			problemi = 1;
		 			System.out.println("ogni stato deve avere un nome.. non valgono nomi vuoti");
		 			System.exit(2);
		 		}
		 		contenuto += cNome + " "; 
		 		for (int x=0; x<nTransizioni; x++)
		 		{
		 			String cLettera = "";
		 			String cOsservabilita="";
		 			String cGuasto="";
		 			String cDestinazione="";	
			 	try{//valuto la congruenza dei vari componenti delle transazioni.. devono avere certe
			 		//caratteristiche
		 			cLettera = interno.getElementsByTagName("lettera").item(x).getTextContent();
		 			if(cLettera.length()!=1)
		 			{
		 				System.out.println("errore.. la lettera dovrebbe esistere ed essere di un solo carattere..");
		 				problemi=1;
		 			}
		 			cOsservabilita = interno.getElementsByTagName("osservabilita").item(x).getTextContent();
		 			if(!(cOsservabilita.equalsIgnoreCase("n") || cOsservabilita.equalsIgnoreCase("y")))
		 			{
		 				System.out.println("errore.. l'osservabilità è Y o N.. non altro");
		 				problemi=1;
		 			}
		 			cGuasto= interno.getElementsByTagName("guasto").item(x).getTextContent();
		 			if(!(cGuasto.equalsIgnoreCase("n") || cGuasto.equalsIgnoreCase("y")))
		 			{
		 				System.out.println("errore.. l'eventuale guasto è Y o N.. non altro");
		 				problemi=1;
		 			}
		 			cDestinazione = interno.getElementsByTagName("destinazione").item(x).getTextContent();
		 			if(cDestinazione.length()<1)
		 			{
		 				System.out.println("errore.. la destinazione deve esistere..");
		 				problemi=1;
		 			}
			 	}
		 		catch(Exception e){
		 			problemi=1;
		 			System.out.println("errore nella lettura di una o più transazioni");
		 			cLettera="";
		 			cOsservabilita = "";
		 			cGuasto ="";
		 			cDestinazione = "";
		 		}
		 		
		 		contenuto += cLettera + " " + cOsservabilita + " " + cGuasto +" " + cDestinazione +" ";
		 		}
			}
			else
			{
		    	System.out.println("xml non valido secondo l'xsd fornito!");
				System.exit(2);
		    	problemi=1;
			}
		 	} 
    } catch (Exception e) {
		e.printStackTrace();
	    System.out.println(e.getMessage());
	    System.exit(1);
    }
	 // System.out.println("LEGGI GRAFO RENDE:"+contenuto);
	  
	  if (problemi>0)
	  {
		  System.exit(2);
	  }
	return contenuto;
  }
 
}