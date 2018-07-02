package centrales;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.junit.Test;

public class TestRedElectrica {

	private static final String PATH_IN = "Preparacion de la Prueba/Lotes de Prueba/Entrada/";

	private static final String PATH_OUT = "Ejecucion de la Prueba/Salida obtenida/";

	@Test
	public void testEnunciado() {
		RedElectrica redElectrica = new RedElectrica(new File(PATH_IN + "00_Enunciado.in"),
				new File(PATH_OUT + "00_Enunciado.out"));
		redElectrica.resolver();
	}

	@Test
	public void testLineasParalelas() {
		RedElectrica redElectrica = new RedElectrica(new File(PATH_IN + "01_Lineas_Paralelas.in"),
				new File(PATH_OUT + "01_Lineas_Paralelas.out"));
		redElectrica.resolver();
	}

	@Test
	public void testUnaCentral() {
		RedElectrica redElectrica = new RedElectrica(new File(PATH_IN + "02_Una_Central.in"),
				new File(PATH_OUT + "02_Una_Central.out"));
		redElectrica.resolver();
	}

	@Test
	public void testFatiga() {
		RedElectrica redElectrica = new RedElectrica(new File(PATH_IN + "03_Fatiga.in"),
				new File(PATH_OUT + "03_Fatiga.out"));
		redElectrica.resolver();
	}
}
