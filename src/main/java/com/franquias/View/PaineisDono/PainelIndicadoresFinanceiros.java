package com.franquias.View.PaineisDono;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.franquias.Controller.DonoController;
import com.franquias.Model.entities.Franquia;

public class PainelIndicadoresFinanceiros extends JPanel {
    private DonoController controller;
    
    public PainelIndicadoresFinanceiros(DonoController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        JPanel painelConteudo = new JPanel();
        painelConteudo.setLayout(new BoxLayout(painelConteudo, BoxLayout.Y_AXIS));

        painelConteudo.add(criarPainelFaturamentoBruto());
        painelConteudo.add(criarPainelTotalPedidos());
        painelConteudo.add(criarPainelTicketMedio());

        JScrollPane scrollPane = new JScrollPane(painelConteudo);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
    }

    private ChartPanel criarPainelFaturamentoBruto() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Franquia> franquias = controller.getUnidades();
        for(Franquia franquia : franquias) {
            if (franquia.getGerente() != null) {
                String nomeGerente = franquia.getGerente().getNome();
                dataset.setValue(controller.getReceita(franquia), "Faturamento", nomeGerente);
            }
        }

        JFreeChart chart = ChartFactory.createBarChart("Faturamento Bruto", "Franquia", "Valor (R$)",
                dataset, PlotOrientation.VERTICAL, false, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(380, 340)); // Dê um tamanho preferido
        return chartPanel;
    }

    private ChartPanel criarPainelTotalPedidos() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // TODO: A lógica aqui está incorreta. Você precisa calcular o NÚMERO DE PEDIDOS por franquia.
        // O código abaixo é apenas um placeholder.
        dataset.setValue(150, "Pedidos", "Franquia Centro");
        dataset.setValue(120, "Pedidos", "Franquia Zona Norte");

        JFreeChart chart = ChartFactory.createBarChart("Total de Pedidos", "Franquia", "Quantidade de Pedidos",
                dataset, PlotOrientation.VERTICAL, false, true, false);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(380, 340));
        return chartPanel;
    }

    private ChartPanel criarPainelTicketMedio() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // TODO: A lógica aqui também precisa ser implementada para calcular o TICKET MÉDIO (Faturamento / N° Pedidos).
        dataset.setValue(83.50, "Ticket Médio", "Franquia Centro");
        dataset.setValue(81.67, "Ticket Médio", "Franquia Zona Norte");
        
        JFreeChart chart = ChartFactory.createBarChart("Ticket Médio", "Franquia", "Valor (R$)",
                dataset, PlotOrientation.VERTICAL, false, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(380, 340));
        return chartPanel;
    }
}