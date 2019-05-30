package negocio;

import javax.swing.UIManager;

public class Main {

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
