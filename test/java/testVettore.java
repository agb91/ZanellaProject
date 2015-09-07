package letturaGrafo;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

public class testVettore {

	@Test
	public void test() {
		Graficatore g = new Graficatore("src/grafo.xml");
		Vector<Nodo> sorgente = g.getGrafo();  //leggi il grafo dall'xml competente
		System.out.println("originale"+sorgente.size());
		
		Vector<Nodo> copia = new Vector<Nodo>();
		copia.addAll(sorgente);
		System.out.println("copia"+copia.size());
		
		copia.add(sorgente.get(2));
		System.out.println("originale"+sorgente.size());
		System.out.println("copia"+copia.size());
		
		System.out.println("copia deep " + copia.get(2).getTransazione(0).getLettera());
	}
	
	private void stampaTwin(Vector<Nodo> a, String nome)
	{
		System.out.println("sono il twin: "+ nome);
		for (int i=0; i<a.size(); i++)
		{
			System.out.println(a.get(i).getNome());
			Vector<Transizione> v = a.get(i).getTransizioniTutte();
			for (int s=0; s<v.size(); s++)
			{
				System.out.println("    "+v.get(s).getLettera());
				System.out.println("    "+v.get(s).getOss());
				System.out.println("    "+v.get(s).getGuasto());
				System.out.println("    "+v.get(s).getDest());
			}			
		}
	}


}
