package com.franquias.View;

import javax.swing.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import com.franquias.Controller.*;
import com.franquias.Model.Produto;
import com.franquias.Model.entities.Vendedor;
import com.franquias.Model.enums.FormaDePagamento;
import com.franquias.Model.enums.ModalidadeEntrega;

public class PainelVendedor extends PainelBase {

    private VendedorController controller;
    private Map<Produto, Integer> itensDoPedido;
    private JList<String> listaProdutosVisual;
    private DefaultListModel<String> listModel;

    JLabel lblTotalValor;

    private final int WIDTH = 500;
    private final int HEIGHT = 400;
    // private final int V_GAP = 10;
    // private final int H_GAP = 5;

    public PainelVendedor(AplicacaoPrincipal app, VendedorController controller) {
        super(app);
        this.controller = controller;
        this.setLayout(new BorderLayout(5, 5));

        this.itensDoPedido = new HashMap<>();
        this.listModel = new DefaultListModel<>();


        desenhaBotoes();
        desenhaFormularioDeProdutos();
        desenhaListaDeProdutos();    
        desenhaRodape();

        this.setVisible(true);
    }

    @Override
    protected JPanel criarPainelAcoes() {
        JPanel painelAcoes = new JPanel();
        painelAcoes.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton btVender = new JButton("Nova Venda");
        painelAcoes.add(btVender);
        btVender.addActionListener(e -> {
            // ação para iniciar a venda
            controller.iniciarVenda();
        });

        JButton btRegistro = new JButton("Abrir Registro");
        painelAcoes.add(btRegistro);
        btRegistro.addActionListener(e -> {
            // Ação para abrir o registro
        });

        return painelAcoes;
    }

    private void desenhaBotoes() {
        JButton btVender = new JButton("Vender");

        btVender.addActionListener(e -> {
            // ação para iniciar a venda
        });

        JButton btRegistro = new JButton("Abrir Registro");

        btRegistro.addActionListener(e -> {
            // Ação para abrir o registro
        });

        JButton btDeslogar = new JButton("Deslogar");

        btDeslogar.addActionListener(e -> controller.deslogar());

        JPanel painelOpcoes = new JPanel();
        painelOpcoes.setLayout(new GridBagLayout());
        painelOpcoes.setPreferredSize(new Dimension(WIDTH, HEIGHT / 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START;
        
        gbc.gridx = 0;
        painelOpcoes.add(btVender, gbc);
        
        gbc.gridx = 1;
        painelOpcoes.add(btRegistro, gbc);
        
        gbc.weightx = 1;
        gbc.gridx = 2;
        painelOpcoes.add(new JPanel(), gbc);

        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        painelOpcoes.add(btDeslogar, gbc);
        
        this.add(painelOpcoes, BorderLayout.NORTH);
    }

    private void desenhaFormularioDeProdutos() {
        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // linha 0: código do produto
        gbc.gridy = 0;
        
        gbc.gridx = 0;
        painelFormulario.add(new JLabel("Código do Produto: "), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JTextField tfCodigo = new JTextField(15);
        painelFormulario.add(tfCodigo, gbc);

        // linha 1: quantidade
        gbc.gridy = 1;

        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelFormulario.add(new JLabel("Qtd: "), gbc);

        gbc.gridx = 1;
        JTextField tfQtd = new JTextField("1", 3);
        painelFormulario.add(tfQtd, gbc);

        // linha 2: botão adicionar
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btAdicionar = new JButton("Adicionar Produto");
        
        btAdicionar.addActionListener(e -> controller.adicionarItemAoPedido(tfCodigo.getText(), Integer.parseInt(tfQtd.getText())));

        painelFormulario.add(btAdicionar, gbc);

        this.add(painelFormulario, BorderLayout.WEST);
    }

    private void desenhaListaDeProdutos() {
        JPanel painelLista = new JPanel(new BorderLayout());

        listaProdutosVisual = new JList<>(listModel);

        painelLista.add(new JScrollPane(listaProdutosVisual), BorderLayout.CENTER);

        JPanel painelTotalLista = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelTotalLista.add(new JLabel("Total do Pedido: "));
        lblTotalValor = new JLabel("R$ 0,00");
        lblTotalValor.setFont(new Font("Arial", Font.BOLD, 16));
        painelTotalLista.add(lblTotalValor);

        painelLista.add(painelTotalLista, BorderLayout.SOUTH);

        this.add(painelLista, BorderLayout.EAST);
    }

    private void desenhaRodape() {
        JPanel painelRodape = new JPanel();
        painelRodape.setLayout(new GridBagLayout());
        painelRodape.setPreferredSize(new Dimension(WIDTH, HEIGHT / 5));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JPanel painelCliente = new JPanel();
        painelCliente.setLayout(new FlowLayout());
        painelCliente.add(new JLabel("Cliente: "));
        painelCliente.add(new JTextField(15));
        JComboBox<FormaDePagamento> formaPagamento = new JComboBox<>(FormaDePagamento.values());
        painelCliente.add(formaPagamento);
        gbc.gridy = 0;

        painelRodape.add(painelCliente, gbc);

        JPanel painelEntregaTaxa = new JPanel();
        painelEntregaTaxa.setLayout(new FlowLayout());
        JComboBox<ModalidadeEntrega> formaEntrega = new JComboBox<>(ModalidadeEntrega.values());
        painelEntregaTaxa.add(formaEntrega);
        painelEntregaTaxa.add(new JLabel("Taxa"));
        painelEntregaTaxa.add(new JTextField(8));
        
        gbc.gridy = 1;
        painelRodape.add(painelEntregaTaxa, gbc);

        JPanel painelButton = new JPanel();
        painelButton.setLayout(new GridBagLayout());
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.gridwidth = 3;
        painelButton.add(new JLabel(), gbc);
        gbc.gridx = 3;
        gbc.weightx = 0;
        gbc.gridwidth = 0;
        painelButton.add(new JButton("Finalizar"), gbc);

        gbc = new GridBagConstraints();
        gbc.gridy = 1;
        painelRodape.add(painelButton, gbc);

        this.add(painelRodape, BorderLayout.SOUTH);
    }
}
    