package com.franquias.View.PaineisDono;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
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
        
        Map<Long, BigDecimal> faturamentoPorFranquia = controller.calcularFaturamentoPorFranquia();
        List<Franquia> franquias = controller.getUnidades();

        for (Franquia franquia : franquias) {
            if (franquia.getGerente() != null) {
                BigDecimal faturamento = faturamentoPorFranquia.getOrDefault(franquia.getId(), BigDecimal.ZERO);
                
                dataset.setValue(faturamento, "Faturamento", franquia.getCidade());
            }
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Faturamento Bruto por Unidade", "Franquia", "Valor (R$)",
                dataset, PlotOrientation.VERTICAL, false, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(380, 340));
        return chartPanel;
    }

    private ChartPanel criarPainelTotalPedidos() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        Map<Long, Long> totalPedidosPorFranquia = controller.calcularTotalPedidosPorFranquia();
        List<Franquia> franquias = controller.getUnidades();

        for (Franquia franquia : franquias) {
            if (franquia.getGerente() != null) {
                Long contagem = totalPedidosPorFranquia.getOrDefault(franquia.getId(), 0L);
                
                dataset.setValue(contagem, "Pedidos", franquia.getCidade());
            }
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Total de Pedidos por Unidade", "Franquia", "Quantidade de Pedidos",
                dataset, PlotOrientation.VERTICAL, false, true, false);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(380, 340));
        return chartPanel;
    }

    private ChartPanel criarPainelTicketMedio() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<Long, BigDecimal> ticketMedioPorFranquia = controller.calcularTicketMedioPorFranquia();
        List<Franquia> franquias = controller.getUnidades();

        for (Franquia franquia : franquias) {
            if (franquia.getGerente() != null) {              
                BigDecimal ticketMedio = ticketMedioPorFranquia.getOrDefault(franquia.getId(), BigDecimal.ZERO);
                
                dataset.setValue(ticketMedio, "Ticket Médio", franquia.getCidade());
            }
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Ticket Médio por Unidade", "Franquia", "Valor (R$)",
                dataset, PlotOrientation.VERTICAL, false, true, false);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(380, 340));
        return chartPanel;
    }
}