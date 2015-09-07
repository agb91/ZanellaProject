package spring;
// POSTULATI: 
//1)LO STATO ROOT è il primo scritto nell'xml in alto
//2)le parole Nodo e Stato sono usate in modo intercambiabile nei commenti
//3) i twin generati secondo l'algoritmo possono violare le regole imposte sul grafo iniziale
//(es: nel grafo iniziale ogni stato deve avere un'uscita, se nei twin capitasse uno stato
//senza uscite perchè ad esempio facendo il good twin l'unica uscita con transizione guasto 
//viene scartata) esso verrebbe accettato comunque])


//COSA VIENE CONTROLLATO DI UN GRAFICO:
//1) che non sia vuoto
//2) che sia ben formato
//3) che sia conforme ad un documento xsd (nomi-quantità-rapporto contenitore
//contenuto di ogni tag)
//4) che ogni stato abbia uscite che portano ad uno stato esistente
//5) che ogni stato sia raggiungibile
//6) che da ogni stato si possa raggiungere una transizione osservabile
//7) che non ci siamo stati doppione o transizioni doppione tranne che abbiamo guasto diverso	
//8) i guasti sono non osservabili!
import java.util.Vector;

import org.springframework.beans.factory.*;
import org.springframework.beans.factory.xml.*;
import org.springframework.core.io.ClassPathResource;

import letturaGrafo.*;
import stampa.*;

public class Regista {

	static Vector<Nodo> grafo = new Vector<Nodo>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BeanFactory ctx = new XmlBeanFactory(new ClassPathResource("beans.xml"));
        
		Graficatore g = (Graficatore) ctx.getBean("Graficatore");
		grafo = g.getGrafo();  //leggi il grafo dall'xml competente
		boolean ok = false;
		Controllore c = new Controllore(grafo);  //controlla se il grafico è accettabile
		if(c.valida())
		{
			System.out.println("lettura ok; ho letto il grafico seguente \n\n");
			Stampa.stampaTwin(grafo, "GrafoLetto:");
			System.out.println("\n\n\n---------------------------------------------\n\n\n");
		}

	}

}
