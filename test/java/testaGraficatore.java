package letturaGrafo;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

public class testaGraficatore {

	@Test
	public void test() {
		Graficatore g = new Graficatore("src/grafo.xml");
		Vector<Nodo> grafo = g.getGrafo();  //leggi il grafo dall'xml competente
		stampaTwin(grafo,"grafo1");
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
