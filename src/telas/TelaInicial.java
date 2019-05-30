package telas;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import negocio.Controle;

public class TelaInicial {

	private JFrame framePrincipal;
	private Controle meuChefe;
	private JLabel lblArquivo;
	private JLabel lblAutor;
	private JLabel lblTitulo;

	public TelaInicial(Controle meuChefe) {
		this.meuChefe = meuChefe;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		framePrincipal = new JFrame();
		framePrincipal.setTitle("Visualizador/Conversor C60");
		framePrincipal.setBounds(100, 100, 300, 300);
		framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		framePrincipal.setVisible(true);
		
		lblTitulo = new JLabel();
		lblTitulo.setFont(new Font("Times new Roman", Font.BOLD, 18));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setVerticalAlignment(SwingConstants.TOP);
		lblTitulo.setEnabled(true);
		lblTitulo.setBounds(0, 10, 300, 20);
		lblTitulo.setText("Selecione arquivos *.c60 ou *.csv");
		framePrincipal.getContentPane().add(lblTitulo);
		
		
		JButton btnSelecionaArquivo = new JButton("Selecionar arquivo");
		btnSelecionaArquivo.setBounds(64, 66, 150, 23);
		btnSelecionaArquivo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser("C:\\");
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileNameExtensionFilter c60 = new FileNameExtensionFilter("Arquivos Condutivímetro", "c60");
				FileNameExtensionFilter csv = new FileNameExtensionFilter("Arquivos CSV", "csv");
				jfc.addChoosableFileFilter(c60);
				jfc.addChoosableFileFilter(csv);
				jfc.setFileFilter(c60);
				jfc.setAcceptAllFileFilterUsed(false);
				jfc.showOpenDialog(framePrincipal);
				File arquivoOrigem = jfc.getSelectedFile();
				if (arquivoOrigem != null) {
					lblArquivo.setText(arquivoOrigem.getName());
					meuChefe.setPastaOrigem(arquivoOrigem);
				}
			}
		});
		
		framePrincipal.getContentPane().setLayout(null);
		framePrincipal.getContentPane().add(btnSelecionaArquivo);
		
		JButton btnVisualizar = new JButton("Visualizar Gráfico");
		btnVisualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meuChefe.mostraGrafico();
			}
		});
		
		JButton btnConverter = new JButton("Converter p/ CSV");
		btnConverter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean OK = meuChefe.converteArquivo();
				if (OK) JOptionPane.showMessageDialog(framePrincipal, "Conversao concluída", "Conversao concluída", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		
		btnConverter.setBounds(64, 126, 150, 23);
		framePrincipal.getContentPane().add(btnConverter);
		
		btnVisualizar.setBounds(64, 156, 150, 23);
		framePrincipal.getContentPane().add(btnVisualizar);
		
		lblArquivo = new JLabel("");
		lblArquivo.setHorizontalAlignment(SwingConstants.CENTER);
		lblArquivo.setEnabled(false);
		lblArquivo.setBounds(64, 100, 150, 14);
		framePrincipal.getContentPane().add(lblArquivo);
		
		lblAutor = new JLabel("<html>Autor: Gustavo G. Ditzel<br/>email: gustavo.ditzel@eletrosul.gov.br</html>");
		lblAutor.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutor.setEnabled(true);
		lblAutor.setBounds(05, 206, 290, 28);
		framePrincipal.getContentPane().add(lblAutor);
		
		framePrincipal.repaint();
	}
	
	public void mostraErro(String msg) {
		JOptionPane.showMessageDialog(framePrincipal, msg, "Erro", JOptionPane.ERROR_MESSAGE);
	}
}
