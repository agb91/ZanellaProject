package letturaGrafo;
import java.util.Vector;

public class Controllore {
	
	private Vector<Nodo> grafo = new Vector<Nodo>();
	private Vector<Transizione> transizioni = new Vector<Transizione>();
	
	public Controllore(Vector<Nodo> g)
	{
		grafo = g;
		for (int i=0; i<grafo.size(); i++)
		{
			Nodo n = grafo.get(i);
			transizioni.addAll(n.getTransizioniTutte());
		}
	}
	
	public boolean valida() // conto i problemi: ogni funzione aggiunge 1 se c'è un problema
	{
		System.out.println("\n----------------------------------------------------------------------------\n");
		boolean risp = false;
		int problemi = 0;
		problemi += contaNodi();
		// SE OGNI STATO HA UN USCITA PER FORZA CHE È CICLICO!:
		if(uscitaOgniStato()==1) //se c'è un problema di uscite non mi spreco a controllare 
			//la condizione per capire se c'è la possibilità di raggiungere transionzioni osservabili...
		{
			problemi += 1;
		}
		else //se invece le uscite sono ok controllo l'altra condizione
		{
			//System.out.println("uscite:" + uscitaOgniStato());
			problemi += daOgniUscitaOsservabile(); // da ogni uscita si può raggiungere un evento osservabile
			//System.out.println("fatto");
		}
		problemi += ogniStatoRaggiungibile();//ogni stato deve essere raggiungibile
		problemi += guastiNonOsservabili();//i guasti devono essere non osservabili
		problemi += transizioniGemelle(); //niente transazioni gemelle (stesso nome, stessa sorgente, 
		//stessa destinazione, stessa situazione di guasto)
		problemi += noDoppioni();  //NO DOPPIONI NEL NOME DELLO STATO, SI NEL NOME DELLA TRANSIZIONI
		problemi += sconnesso(); //l'uscita non corrisponde a stati esistenti
				
		System.out.println("\n----------------------------------------------------------------------------\n");
		if (problemi==0)
		{
			risp=true;
			System.out.println("il grafo è conforme alle specifiche");
		}
		else
		{
			System.out.println("riscontati problemi durante il controllo dei requisiti");
			System.exit(2);
		}
		System.out.println("\n----------------------------------------------------------------------------\n");
		return risp;
	}
	
	private int ogniStatoRaggiungibile()
	{
		int ris = 0;
		for (int i = 1; i<grafo.size(); i++)//non controllo la sorgente!
		{
			int ragg = 1;
			String nome = grafo.get(i).getNome();
			for (int a=0; a<transizioni.size(); a++)
			{
				if(transizioni.get(a).getDest().equalsIgnoreCase(nome))
				{
					ragg = 0;
				}
			}
			if(ragg==1)
			{
				ris=1;
				System.out.println("errore stato "+ grafo.get(i).getNome()+" non raggiungibile");
			}
		}
		if(ris==0)
		{
			System.out.println("controllo raggiungibilità:   OK");
		}
		return ris;
	}
	
	private int guastiNonOsservabili() // 0 se ok 1 se ko
	{
		int ris=0;
		for(int i=0; i<transizioni.size(); i++)
		{
			if(transizioni.get(i).getGuasto())
			{
				if(transizioni.get(i).getOss())
				{
					ris = 1;
					System.out.println("una transizione di guasto DEVE essere non osservabile (specifiche)");
				}
			}
		}
		if(ris==0)
		{
			System.out.println("controllo guasti non osservabili:   OK");
		}
		return ris;
	}
	
	private int transizioniGemelle()//1 se ci sono transizioni identiche
	//(anche dal punto di vista del guasto{cosa non concessa} 0 se tutto ok)
	{
		int ris=0;
		for (int i=0; i<grafo.size(); i++) //per ogni nodo
		{
			Nodo n = grafo.get(i);
			Vector<Transizione> attuali = n.getTransizioniTutte();
			for (int a=0; a<attuali.size(); a++)
			{//per ogni transizione del nodi in questione
				Transizione t = attuali.get(a);
				for (int s=0; s<attuali.size(); s++)
				{
					Transizione conf = attuali.get(s);
					if(t.getDest().equalsIgnoreCase(conf.getDest())&&(s!=a))
					{
						//System.out.println("stessa transizione, guardo guasti:");
						if(t.getGuasto()==conf.getGuasto())
						{
							System.out.println("ci sono transazioni gemelle");
							ris = 1;
						}
					}
				}
			}
		}
		if(ris==0)
		{
			System.out.println("controllo assenza doppioni nelle transazioni:   OK");
		}
		return ris;
	}
	
	private int sconnesso() //0 se ok 1 se ko
	{
		int ris=0;
		for (int i=0; i<transizioni.size(); i++)
		{
			ris = 1; //per ogni transizione pongo a 1, se trovo una destinazione sensata rimetto a 0
			Transizione t = transizioni.get(i);
			for (int a=0; a<grafo.size(); a++)
			{
				Nodo n = grafo.get(a);
				if(n.getNome().equalsIgnoreCase(t.getDest()))
				{
					ris=0;  //c'è destinazione, rimetto a 0
				}
			}
			if(ris==1)
			{
				System.out.println("errore grafo sconnesso, blocco");
				System.exit(2);
			}
		}
		if(ris==0)
		{
			System.out.println("controllo connessione grafico:   OK");
		}
		return ris;
	}
	
	private int noDoppioni()
	{
		int r=0;
		//controllo che tutti gli stati abbiano nome diverso
		for (int i=0; i<grafo.size(); i++)
		{
			for (int a=0; a<grafo.size(); a++)
			{
				if(grafo.get(i).getNome().equalsIgnoreCase(grafo.get(a).getNome()) && a!=i)
				{
					r=1;
					System.out.println("errori stati doppione");
				}
			}
		}
		if(r==0)
		{
			System.out.println("controllo omonimia stati:   OK");
		}
		return r;
	}
	
	private void azzeraContatoreDL() //pone a zero il contatore di cicli di ogni nodo
	{
		for(int i=0; i<grafo.size(); i++)//per ogni nodo
		{
			grafo.get(i).azzeraCicli();
		}
	}
	
	private int daOgniUscitaOsservabile() // controllo che da ogni 
	//stato si raggiunga una transizione osservabile prima o poi
	{
		int ris = 0;
		for(int i=0; i<grafo.size(); i++)
		{
			azzeraContatoreDL();//questo contatore serve a fermare la ricerca che non può
			//continuare in eterno: se in 100 ricorsioni non si trova una transizione osservabile
			//do per scontato che non esista
			if(problemiRaggiungereOsservabile(grafo.get(i)))//questo nodo ha problemi a raggiungere
				// una transizione osservabile?
			{
				ris=1;
				System.out.println("dallo stato: " + grafo.get(i).getNome() +  " non si può raggiungere una transazione osservabile");
			}
		}
		if(ris==0)
		{
			System.out.println("controllo raggiungibilità di transizione osservabile da ogni uscita:   OK");
		}
		return ris;
	}
	
	//idea di fondo: se c'è in quelle immediate ok, altrimenti cerca nelle prossime ricorsivamente
	private boolean problemiRaggiungereOsservabile(Nodo n)  //false se tutto ok, true se problemi
	//NDR: SE IN 100 (DA DEFINITE) TRANSIZIONI NON HO RICAVATO NIENTE DI CONCRETO DO PER SCONTATO CHE 
	//CHE QUESTO CICLO NON ABBIA TRANSIZIONI RAGGIUNGIBILI
	{
		boolean problemi = true;
		if(n.getCicli()>100)
		{
			//System.out.println("sono il nodo: "+ n.getNome() + " è la 100 volta..");
			return true;
		}
		n.addCicli();
		if(osservabile(n.getTransizioniTutte())) // se c'è tra le sue transizioni è ok..
		{
			problemi=false;
		}
		else  //se non c'è tra quelle del nodo cerco ricorsivamente tra i prossimi nodi
		{
			Vector<Nodo> prossimi = getRaggiungibili(n); // prendo i nodi raggiungibili da n
//			System.out.println("sono il nodo:"+n.getNome() + " ed i miei figli sono:");
//			for(int i=0; i<prossimi.size(); i++)
//			{
//				System.out.println("figlio:  "+prossimi.get(i).getNome());
//			}

			for(int a=0; a<prossimi.size(); a++) //tra i prossimi ce ne è uno da cui si può
				// raggiungere qualcosa di osservabile? se si anche dal nodo attuale si
				// può raggiungere (tramite i prossimi) qualcosa di osservabile
			{
				if(!problemiRaggiungereOsservabile(prossimi.get(a)))
				{
					problemi = false;
				}
			}
		}
		return problemi;
	}
	
	private Vector<Nodo> getRaggiungibili(Nodo n) //rendo il vettore di stati raggiungibili da n
	{
		Vector<Nodo> ris = new Vector<Nodo>();
		//System.out.println("sono il nodo" + n.getNome());
		Vector<String> destinazioni = n.getTutteDest();
		//System.out.println("le mie destinazioni sono n°"+destinazioni.size());
		for (int i=0; i<destinazioni.size(); i++)
		{
			ris.add(getNodoByName(destinazioni.get(i)));
		}
		return ris;
	}
	
	private Nodo getNodoByName(String n)
	{
		Nodo ris = null;
		for(int i=0; i<grafo.size(); i++)
		{
			if(grafo.get(i).getNome().equalsIgnoreCase(n))
			{
				ris = grafo.get(i);
			}
		}
		return ris;
	}
	
	private boolean osservabile(Vector<Transizione> v) // ce ne è almeno 1 osservabile?
	{
		boolean osservabile = false;
		for(int i=0; i<v.size(); i++)
		{
			if(v.get(i).getOss())
			{
				osservabile = true;
			}
		}
		return osservabile;
	}

	private int uscitaOgniStato() // 0 se ok 1 se problema
	{
		int r = 0;
		for (int i = 0 ; i<grafo.size(); i++)
		{
			Nodo n = grafo.get(i);
			if(cieco(n))
			{
				r=1;
				System.out.println("ci sono stati senza uscite");
			}
		}
		if(r==0)
		{
			System.out.println("controllo esistenza di almeno un uscita per ogni stato:   OK");
		}
		return r;
	}
	
	private boolean cieco(Nodo n)// vero se cieco, falso se ha uscite
	{
		boolean r = true;
			if(n.quanteTransizioni()>0)
			{
				r = false;
				//System.out.println("nodo:"+n.getNome()+";;;"+n.quanteTransizioni());
			}
		//System.out.println("sono il nodo"+n.getNome()+"e la mi cecità è : "+r);	
		return r;
	}
	
	private int contaNodi() // 0 se ok 1 se problema
	{
		int r = 1;
		if (grafo.size()>0)
		{
			r=0;
		}
		if(r==1)
		{
			System.out.println("grafo di dimensione nulla");
		}
		return r;
	}
}
