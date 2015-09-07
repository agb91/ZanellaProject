package letturaGrafo;

public class Transizione { //arco tra 2 stati (nodi)
	
	String lettera;
	boolean osservabilita;
	String destinazione;
	boolean guasto;
	
	public Transizione(String let, String oss, String gu, String dest)
	{
		lettera = let;
		if(oss.equalsIgnoreCase("y"))
		{
			osservabilita = true;
		}
		else
		{
			osservabilita = false;
		}
		if(!osservabilita)  // se non è osservabile non dovrei vedere la lettera..
			// ma ha comunque una lettera
		{
			lettera += "-ma è un segreto!" ;
		}
		destinazione = dest;
		if(gu.equalsIgnoreCase("y"))
		{
			guasto = true;
		}
		else
		{
			guasto = false;
		}
	}
	
	public boolean getGuasto()
	{
		return guasto;
	}
	
	public String getLettera()
	{
		return lettera;
	}	
	
	public boolean getOss()
	{
		return osservabilita;
	}
	
	public String getDest()
	{
		return destinazione;
	}

}
