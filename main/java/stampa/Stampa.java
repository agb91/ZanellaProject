package stampa;

import java.util.Vector;
import letturaGrafo.*;

public class Stampa {
	
	public static void stampaTwin(Vector<Nodo> a, String nome)
	{
		System.out.println("sono il grafo: "+ nome);
		for (int i=0; i<a.size(); i++)
		{
			System.out.println(a.get(i).getNome());
			Vector<Transizione> v = a.get(i).getTransizioniTutte();
			for (int s=0; s<v.size(); s++)
			{
				System.out.println("    lettera:      "+v.get(s).getLettera());
				System.out.println("    osservabilità:"+v.get(s).getOss());
				System.out.println("    guasto:       "+v.get(s).getGuasto());
				System.out.println("    destinazione: "+v.get(s).getDest());
			}			
		}
	}


}
