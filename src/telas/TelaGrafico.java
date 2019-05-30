package telas;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;

@SuppressWarnings("serial")
public class TelaGrafico extends JFrame {

	public TelaGrafico(ChartPanel chart) {
		setSize(new Dimension(800, 600));
		setLayout(new BorderLayout());
		setVisible(true);
		add(chart);
	}

}
