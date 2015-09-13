package letturaGrafo;
import java.util.Vector;


public class Nodo {
	
	private String nome;
	private int contaCicli = 0; //a volte è utile un contatore per capire se un ciclo 
	//passa troppe volte per il nodo e smascherare un deathlock
	
	//private boolean raggiungibile = false;  // è raggiungibile? lo decide un programma esterno
	//poi setta la risposta (ovviamente il programma esterno conosce l'intero grafo.. nodo no)
	Vector <Transizione> transizioni = new Vector<Transizione>(); // ogni nodo ha transizioni USCENTI

	public Nodo(String str) {  //splitta la stringa per ottenerne un vettore che costurisce
		//gli oggetti Transizione. 1 nodo ha tante transizioni
		String[] v = str.split(" ");
		nome = v[0];
		for (int i=1; i<v.length-1; i=i+4)
		{
			Transizione a = new Transizione(v[i],v[i+1],v[i+2], v[i+3]);
			transizioni.add(a);
		}
	}
	
	public Nodo()  // serve a volte costruirlo vuoto
	{
		
	}
	
	public void svuotaTransizioni()
	{
		transizioni.clear();
	}
	
	public void azzeraCicli()
	{
		contaCicli=0;
	}
	
	public void addCicli()
	{
		contaCicli++;
	}
	
	public int getCicli()
	{
		return contaCicli;
	}
	
	public void setTransizioni(Vector<Transizione> t)  // se l'ho costruito vuoto le transazioni
	//dovrò pur mettergliele in qualche modo, con questo metodo
	{
		transizioni = t;
	}
	
	public void setNome(String n)
	{
		nome=n;
	}
	
	public Vector<String> getTutteDest()  //rendi tutte le destinazioni, cioè tutti i nodi che
	// si possono raggiungere da questo nodo seguendo una transazione anche di guasto
	{
		Vector<String> ris = new Vector<String>();
		for (int i=0; i<transizioni.size(); i++)
		{
		//	System.out.println("ciclo getdest");
	//		System.out.println("aggiungo:"+transizioni.get(i).getDest());
			ris.add(transizioni.get(i).getDest());
		//	System.out.println("size:"+ris.size());
		}
		return ris;
	}
	
	public Vector<Transizione> getTransizioniOsservabili()  //rendi tutte le transazioni osservabili
	{
		Vector <Transizione> t = new Vector<Transizione>();
		for (int i =0; i<transizioni.size(); i++)
		{
			if(transizioni.get(i).getOss())
			{
				t.add(transizioni.get(i));
			}
		}
		return t;		
	}
	
	public Vector<Transizione> getTransizioniNonOsservabili()  //rendi tutte le transazioni osservabili
	{
		Vector <Transizione> t = new Vector<Transizione>();
		for (int i =0; i<transizioni.size(); i++)
		{
			if(!transizioni.get(i).getOss())
			{
				t.add(transizioni.get(i));
			}
		}
		return t;		
	}
	
	public Vector<Transizione> getTransizioniNonGuaste()  //ovvio
	{
		Vector <Transizione> t = new Vector<Transizione>();
		for (int i =0; i<transizioni.size(); i++)
		{
			if(!transizioni.get(i).getGuasto())
			{
				t.add(transizioni.get(i));
			}
		}
		return t;		
	}
	
	public Vector<Transizione> getTransizioniGuaste()  //ovvio
	{
		Vector <Transizione> t = new Vector<Transizione>();
		for (int i =0; i<transizioni.size(); i++)
		{
			if(transizioni.get(i).getGuasto())
			{
				t.add(transizioni.get(i));
			}
		}
		return t;		
	}
	
	public Vector<Transizione> getTransizioniTutte()  //ovvio
	{
		return transizioni;		
	}
	
	/*//la raggiungibilità viene decretata da una classe esterna che ne sa di più
	public void setRaggiungibile(boolean b)
	{
		raggiungibile = b;
	}
	
	// è un getter
	public boolean getRaggiungibile()
	{
		return raggiungibile;
	}*/
	
	public String getNome()
	{
		return nome;
	}
	
	public Transizione getTransazione(int indice)
	{
		return transizioni.get(indice);
	}
	
	public int quanteTransizioni()
	{
		return transizioni.size();
	}
	
	public int quanteTransizioniOsservabili()
	{
		int cont = 0;
		for (int i=0; i<transizioni.size(); i++)
		{
			if(transizioni.get(i).getOss())
			{
				cont++;
			}
		}
		//System.out.println("sono il nodo"+nome+"con tos:"+cont);
		return cont;
	}

}
