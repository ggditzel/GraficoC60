package negocio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import telas.TelaGrafico;
import telas.TelaInicial;

public class Controle {
		
	private OutputStream os;
	private OutputStreamWriter osw;
	private InputStream is;
	private InputStreamReader isr;
	private BufferedWriter bw;
	private BufferedReader br;
	private File arquivoOrigem;
	private TelaInicial tela;
	private String[] marcador;

	public Controle() {
		tela = new TelaInicial(this);
	}
	
	public void mostraGrafico() {
		
		ArrayList<Elemento> lista = new ArrayList<>();
		lista = leDadosArquivo();
		
		if (lista != null) {
			new TelaGrafico(new GraficoBarras().criarGrafico(lista));			
		} else {
			tela.mostraErro("Formato de arquivo desconhecido");
		}	
	}

	public void setPastaOrigem(File pastaOrigem) {
		this.arquivoOrigem = pastaOrigem;
	}

	private boolean abreArquivoLeitura() {

		boolean ok = false;
		if (arquivoOrigem != null) {
			try {
				is = new FileInputStream(arquivoOrigem.getAbsolutePath());
				ok = true;
			} catch (FileNotFoundException exception) {
				tela.mostraErro("Problema na abertura do arquivo");
				ok = false;
			}
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
		}
		return ok;
	}

	private boolean fechaArquivoLeitura() {

		boolean ok = false;
		try {
			br.close();
			isr.close();
			is.close();
			ok = true;
		} catch (IOException e1) {
			tela.mostraErro("Erro de leitura/escrita ao fechar arquivos");
			ok = false;
		}

		return ok;
	}
	
	private ArrayList<Elemento> leDadosArquivo() {
		if (arquivoOrigem != null) {
			if (retornaFormato(arquivoOrigem).contentEquals("c60")) {
				return leDadosC60();
			} else if (retornaFormato(arquivoOrigem).contentEquals("csv")) {
				return leDadosCsv();
			}
		}
		return null;
		
	}

	private ArrayList<Elemento> leDadosCsv() {
		ArrayList<Elemento> listaElementos = new ArrayList<>();
		Elemento e;
		abreArquivoLeitura();
		String s ="";
		try {
			s = br.readLine(); // descarta primeira linha, cabecalhos do csv
			s = br.readLine(); // elemento;tensao;condutancia;densidade
			while (s != null) {
				marcador = s.split(";");
					e = new Elemento();
					e.setNumero(Integer.parseInt(marcador[0]));
					e.setTensao(Double.parseDouble(marcador[1].replace(',', '.')));
					e.setCondutancia(Double.parseDouble(marcador[2].replace(',', '.')));
					e.setDensidade((marcador.length > 3) ? Double.parseDouble(marcador[3].replace(',', '.')) : 0.0);

					listaElementos.add(e);
					s = br.readLine();
			}
				s = br.readLine();

		} catch (IOException e1) {
			tela.mostraErro("Erro de leitura/escrita");

		} finally {
			fechaArquivoLeitura();
		}

		return listaElementos;
	}

	private ArrayList<Elemento> leDadosC60() {
		ArrayList<Elemento> listaElementos = new ArrayList<>();
		Elemento e;
		if (arquivoOrigem != null) {
			abreArquivoLeitura();
			String s ="";
			try {
				s = br.readLine();
				while (s != null) {
					marcador = s.split(";");
					if (marcador[0].equals("Voltagem do Elemento")) {
						e = new Elemento();
						e.setNumero(Integer.parseInt(marcador[1]));
						e.setTensao(Double.parseDouble(marcador[2].replace(',', '.')));
						
						s = br.readLine(); // descarta
						s = br.readLine(); // linha da condutancia
						
						marcador = s.split(";");
						
						e.setCondutancia(Double.parseDouble(marcador[2].replace(',', '.')));
						
						s = br.readLine(); // descarta
						s = br.readLine(); // descarta
						s = br.readLine(); // linha da densidade
						
						marcador = s.split(";");
						
						e.setDensidade((marcador.length > 2) ? Double.parseDouble(marcador[2].replace(',', '.')) : 0.0);

						listaElementos.add(e);
						
						s = br.readLine();
					}
					s = br.readLine();
				}
				
			} catch (IOException e1) {
				tela.mostraErro("Erro de leitura/escrita");

			} finally {
				fechaArquivoLeitura();
			}
		}
		
		return listaElementos;
	}

	public boolean converteArquivo() {
		boolean ok = false;
		if (arquivoOrigem != null) {
			if (retornaFormato(arquivoOrigem).equals("c60")) {
				//System.out.println("reconheceu c60");
				return ok = converteC60();
			} else if (retornaFormato(arquivoOrigem).equals("csv")){
				tela.mostraErro("Este arquivo já está no formato CSV.");
				return false;
			} else {
				tela.mostraErro("Formato de arquivo desconhecido.");
				return false;
			}
		}
		return ok;
	}

	private boolean converteC60() {
		boolean ok = false;
		if (arquivoOrigem != null) {
			abreArquivoLeitura();
			abreArquivoEscrita();

			String s ="";
			try {

				bw.write("elemento;tensao;condutancia;densidade");
				bw.newLine();
				s = br.readLine();
				String novaLinha = "";
				while (s != null) {
					marcador = s.split(";");
					if (marcador[0].equals("Voltagem do Elemento")) {
						novaLinha += marcador[1] + ";" + marcador[2];
						s = br.readLine();
						s = br.readLine();
						marcador = s.split(";");
						novaLinha += ";" + marcador[2];
						s = br.readLine();
						s = br.readLine();
						s = br.readLine();
						marcador = s.split(";");

						if (marcador.length > 2) {
							novaLinha += ";" + marcador[2];
						} else {
							novaLinha += ";";
						}

						bw.write(novaLinha);
						bw.newLine();
						novaLinha = "";
						s = br.readLine();
					}
					s = br.readLine();
				}
				ok = true;
			} catch (IOException e1) {
				tela.mostraErro("Erro de leitura/escrita");
				ok = false;

			} finally {
				fechaArquivoLeitura();
				fechaArquivoEscrita();
			}
		}

		return ok;
	}

	private String retornaFormato(File arquivo) {
		
		abreArquivoLeitura();
		String s ="";
		try {
			s = br.readLine();
			if (s != null) {
				marcador = s.split(";");
				if (marcador[0].equals("Version Stamp")) {
					return "c60";
				}
				
				if (marcador[0].equals("elemento")) {
					return "csv";
				}
				
				return "desc";
				
			}
			
		} catch (IOException e1) {
			tela.mostraErro("Erro de leitura/escrita");

		} finally {
			fechaArquivoLeitura();
		}
		
		return "desc";

	}

	private boolean abreArquivoEscrita() {

		boolean ok = false;
		try {
			String nomeCompleto = arquivoOrigem.getName();
			int ultimaLetra = nomeCompleto.length() - 3;
			String nomeSemExtensao = nomeCompleto.substring(0, ultimaLetra);
			os = new FileOutputStream(arquivoOrigem.getParent() + "\\" + nomeSemExtensao + "csv");
			ok = true;
		} catch (FileNotFoundException e1) {
			tela.mostraErro("Problema na abertura do arquivo para gravação");
			ok = false;
		}
		osw = new OutputStreamWriter(os);
		bw = new BufferedWriter(osw);

		return ok;
	}

	private boolean fechaArquivoEscrita() {

		boolean ok = false;
		try {

			bw.close();
			osw.close();
			os.close();
			ok = true;
		} catch (IOException e1) {
			tela.mostraErro("Erro de leitura/escrita ao fechar arquivos");
			ok = false;
		}

		return ok;
	}

}