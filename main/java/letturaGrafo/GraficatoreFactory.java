package letturaGrafo;

public class GraficatoreFactory {
	public static Graficatore createGraficatore() {
		Graficatore g = new Graficatore();
		String path = "src/oracolo.xml";
		g.setLeggiGrafo(new LeggiGrafo(path));
		return g;
	}
}
