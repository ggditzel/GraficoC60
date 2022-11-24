package negocio;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.util.ArrayList;

public class GraficoBarras {
	
	public GraficoBarras() {
		
		
	}
	
	private CategoryDataset createDataSetCond(ArrayList<Elemento> listaElementos) {
		
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		for (Elemento e : listaElementos) {
			dataSet.addValue(e.getTensao(), "Tens�o", e.getNumero());
			dataSet.addValue(e.getCondutancia(), "Condut�ncia", e.getNumero());
			if (e.getDensidade() != 0) {
				dataSet.addValue(e.getDensidade(), "Densidade", e.getNumero());
			}
			
		}
		return dataSet;
	}
	
	private JFreeChart createBarChart (CategoryDataset dataSet) {
		
		JFreeChart graficoBarras = ChartFactory.createBarChart("Gr�fico dos Valores Medidos", "Elementos", "Valores", dataSet, PlotOrientation.VERTICAL, true, true, false);
		return graficoBarras;
	}
	
	public ChartPanel criarGrafico(ArrayList<Elemento> listaElementos) {
		
		CategoryDataset dataSet = this.createDataSetCond(listaElementos);
		JFreeChart grafico = this.createBarChart(dataSet);
		ChartPanel panelDoGrafico = new ChartPanel(grafico);
		panelDoGrafico.setPreferredSize(new Dimension(800, 800));
		
		return panelDoGrafico;
	}

}
