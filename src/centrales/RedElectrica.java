package centrales;

//PlanoElectrico
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class RedElectrica {

	private File entrada;
	private File salida;
	private int cantidadCiudades;
	private int cantidadCentrales;
	private int[] nodosCentrales;
	private int[][] matrizAdyacencia;
	private boolean matrizDeRecorrido[][];
	private boolean[] recorridoRealizado;
	private int matrizResultado[][];
	private int peso = 0;

	public RedElectrica(final File entrada, final File salida) {
		this.entrada = entrada;
		this.salida = salida;
		try {
			this.leerArchivo();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	private void leerArchivo() throws IOException {
		try {
			Scanner sc = new Scanner(this.entrada);
			this.cantidadCiudades = sc.nextInt();
			this.cantidadCentrales = sc.nextInt();
			if (this.cantidadCentrales > this.cantidadCiudades && this.cantidadCiudades > 100) {
				sc.close();
				throw new ArithmeticException("Error en centrales y ciudades.");
			}
			this.nodosCentrales = new int[this.cantidadCentrales];
			for (int i = 0; i < this.cantidadCentrales; i++) {
				this.nodosCentrales[i] = sc.nextInt();
			}
			this.matrizAdyacencia = new int[this.cantidadCiudades][this.cantidadCiudades];
			for (int i = 0; i < this.cantidadCiudades; i++) {
				for (int j = 0; j < this.cantidadCiudades; j++) {
					this.matrizAdyacencia[i][j] = sc.nextInt();
					if (i != j && this.matrizAdyacencia[i][j] <= 0) {
						sc.close();
						throw new ArithmeticException("Error en peso de ciudades.");
					}
				}
				if (this.matrizAdyacencia[i][i] != 0) {
					sc.close();
					throw new ArithmeticException("Error en central redundante.");
				}
			}
			sc.close();
		} catch (FileNotFoundException | ArithmeticException e) {
			throw new IOException(e.getMessage());
		}
	}

	private void grabarArchivo() {
		try {
			PrintWriter salida = new PrintWriter(this.salida);
			salida.println(this.peso);
			for (int i = 0; i < this.cantidadCiudades; i++) {
				for (int j = 0; j < this.cantidadCiudades; j++) {
					if (this.matrizResultado[i][j] != 0) {
						salida.println((i + 1) + " " + (j + 1));
					}
				}
			}
			salida.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error al grabar archivo.");
		}
	}

	public void resolver() {
		this.recorridoRealizado = new boolean[this.cantidadCiudades];
		this.matrizDeRecorrido = new boolean[this.cantidadCiudades][this.cantidadCiudades];
		this.matrizResultado = new int[this.cantidadCiudades][this.cantidadCiudades];
		int pesoCentral;
		int pesoAux;
		int pivote = -1;
		for (int i = 0; i < this.cantidadCentrales; i++) {
			this.completarCentralesVisitadas();
			this.prim(this.nodosCentrales[i] - 1);
		}
		for (int j = 0; j < this.cantidadCiudades; j++) {
			if (!verificarSiEsCentral(j)) {
				pesoAux = Integer.MAX_VALUE;
				for (int k = 0; k < this.cantidadCiudades; k++) {
					if (verificarSiEsCentral(k)) {
						pesoCentral = this.matrizAdyacencia[j][k];
						if (pesoAux > pesoCentral) {
							pesoAux = pesoCentral;
							pivote = k;
						}
					}
				}
				this.matrizResultado[j][pivote] = pesoAux;
				this.peso += pesoAux;
			}
		}
		this.grabarArchivo();
	}

	public void completarCentralesVisitadas() {
		for (int i = 0; i < this.cantidadCiudades; i++) {
			this.recorridoRealizado[i] = false;
			for (int j = 0; j < this.cantidadCiudades; j++) {
				this.matrizDeRecorrido[i][j] = false;
				this.matrizDeRecorrido[j][i] = false;
			}
		}
	}

	public void prim(int nodoinicio) {
		this.recorridoRealizado[nodoinicio] = true;
		this.matrizDeRecorrido[nodoinicio][nodoinicio] = true;
		int aux = 0;
		for (int j = 0; j < this.cantidadCiudades; j++) {
			aux = this.menorPeso();
			if (this.recorridoRealizado[aux] == false) {
				this.recorridoRealizado[aux] = true;
			}
		}
	}

	private int menorPeso() {
		int min = Integer.MAX_VALUE;
		int nodo1 = 0, nodo2 = 0;
		for (int i = 0; i < this.cantidadCiudades; i++) {
			if (this.recorridoRealizado[i] == true) {
				for (int j = 0; j < this.cantidadCiudades; j++) {
					if (this.recorridoRealizado[j] == false && this.matrizAdyacencia[i][j] != 0
							&& this.matrizAdyacencia[i][j] < min && !this.verificarSiEsCentral(j)) {
						min = this.matrizAdyacencia[i][j];
						nodo1 = j;
						nodo2 = i;
					}
				}
			}
		}
		this.matrizDeRecorrido[nodo1][nodo2] = true;
		return nodo1;
	}

	public boolean verificarSiEsCentral(int nodo) {
		for (int i = 0; i < this.cantidadCentrales; i++) {
			if (nodo == this.nodosCentrales[i] - 1) {
				return true;
			}
		}
		return false;
	}
}
