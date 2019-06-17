/*
 * Programa "as is", feito sem muito crit�rio e planejamento,
 * conforme algumas necessidades foram surgindo.
 * 
 */

package negocio;

import javax.swing.UIManager;

public class Main {
	
	public static final String versao = "Vers�o 1.0";

	public static void main(String[] args) throws Exception {

		// muda a apar�ncia das janelas
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			// poderia mudar o look and feel aqui; se n�o mexer, acho que fica no padrao
		}

		new Controle();
	}

}
