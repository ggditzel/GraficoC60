/*
 * Programa "as is", feito sem muito critério e planejamento,
 * conforme algumas necessidades foram surgindo.
 * 
 */

package negocio;

import javax.swing.UIManager;

public class Main {
	
	public static final String versao = "Versão 1.0";

	public static void main(String[] args) throws Exception {

		// muda a aparência das janelas
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			// poderia mudar o look and feel aqui; se não mexer, acho que fica no padrao
		}

		new Controle();
	}

}
