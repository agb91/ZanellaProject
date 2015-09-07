package letturaGrafo;
import static org.junit.Assert.*;

import org.junit.Test;


public class testaControllore {
//	Graficatore g1 = new Graficatore("src/grafoDoppi.xml");
//	Controllore c1 = new Controllore(g1.getGrafo());
//	
//	Graficatore g2 = new Graficatore("src/grafoSconnessi.xml");
//	Controllore c2 = new Controllore(g2.getGrafo());
//	
//	Graficatore g3 = new Graficatore("src/grafoNoUscite.xml"); //si nodi ma alcuni senza uscita
//	Controllore c3 = new Controllore(g3.getGrafo());
//	
//	Graficatore g4 = new Graficatore("src/grafoVuoto.xml");  //no nodi
//	Controllore c4 = new Controllore(g4.getGrafo());
//	
//	Graficatore g5 = new Graficatore("src/grafo.xml");  //OTTIMO
//	Controllore c5 = new Controllore(g5.getGrafo()); //grafo tutto giusto per controprova
//
//	Graficatore g6 = new Graficatore("src/grafoNoProblemiOss.xml");  //no problemi raggiungibilita stato osservabile
//	Controllore c6 = new Controllore(g6.getGrafo()); 
//
//	Graficatore g7 = new Graficatore("src/grafoSiProblemiOsservabilita.xml");  //si  problemi raggiungibilita stato osservabile
//	Controllore c7 = new Controllore(g7.getGrafo()); //

	Graficatore g10 = new Graficatore("src/grafoMorto.xml");  //si  problemi raggiungibilita stato osservabile
	Controllore c10 = new Controllore(g10.getGrafo()); //
	
	@Test
	public void testValida() {
		c10.valida();
//		//guardo se trova errori
//		assertEquals("error su doppioni", false, c1.valida());
//		assertEquals("error su sconnessione grafo", false, c2.valida());
//		assertEquals("error su uscite da ogni stato", false, c3.valida());
//		assertEquals("error su nessun nodo", false, c4.valida());
////		
//		assertEquals("error su raggiungere osservabilità da ogni uscita",false,c7.valida());
//		assertEquals("error su raggiungere osservabilità da ogni uscita",true,c6.valida());
////		
////		
////		//guardo che non ne trovi su un grafo giusto
//		assertEquals("error su doppioni", true, c5.valida());
//		assertEquals("error su sconnessione grafo", true, c5.valida());
//		assertEquals("error su uscite da ogni stato", true, c5.valida());
//		assertEquals("error su nessun nodo", true, c5.valida());
	}
}
