package negocio;

public class Elemento {
	
	int numero;
	double condutancia;
	double tensao;
	double densidade;
	
	public Elemento() {
		
	}
	
	public Elemento(int numero, double tensao, double condutancia, double densidade) {
		this.numero = numero;
		this.condutancia = condutancia;
		this.tensao = tensao;
		this.densidade = densidade;
	}

	public String getNumero() {
		return Integer.toString(numero);
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public double getCondutancia() {
		return condutancia;
	}

	public void setCondutancia(double condutancia) {
		this.condutancia = condutancia;
	}

	public double getTensao() {
		return tensao;
	}

	public void setTensao(double tensao) {
		this.tensao = tensao;
	}

	public double getDensidade() {
		return densidade;
	}

	public void setDensidade(double densidade) {
		this.densidade = densidade;
	}
	
	
}
