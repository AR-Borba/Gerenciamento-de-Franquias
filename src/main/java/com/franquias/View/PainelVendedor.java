package com.franquias.View;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

import com.franquias.Controller.*;
import com.franquias.Model.Produto;

public class PainelVendedor extends JPanel {

    AplicacaoPrincipal telaPrincipal;

    JList<Produto> produtos;

    private final int WIDTH = 500;
    private final int HEIGHT = 400;
    // private final int V_GAP = 10;
    // private final int H_GAP = 5;

    public PainelVendedor(AplicacaoPrincipal telaPrincipal) {
        this.telaPrincipal = telaPrincipal;
        this.setLayout(new BorderLayout());
        this.setSize(WIDTH, HEIGHT);

        desenhaBotoes();
        desenhaFormularioDeProdutos();
        desenhaListaDeProdutos();    
        desenhaRodape();

        this.setVisible(true);
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

        btDeslogar.addActionListener(e -> {
            telaPrincipal.cardLayout.show(telaPrincipal.painelDeConteudo, "LOGIN");
        });

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
        painelFormulario.setLayout(new BorderLayout());
        painelFormulario.setBackground(Color.lightGray);
        
        JPanel painelAux = new JPanel();
        painelAux.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy = 0;

        JLabel jlProd = new JLabel("Produto");
        gbc.gridwidth = 2;
        JTextField tfProd = new JTextField(20);

        painelAux.add(jlProd, gbc);
        painelAux.add(tfProd, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        painelAux.add(new JLabel("Qtd"), gbc);
        JTextField tfQtd = new JTextField(2);
        painelAux.add(tfQtd, gbc);
        painelAux.add(new JLabel("Preço"), gbc);
        JTextField tfPreco = new JTextField(8);
        painelAux.add(tfPreco, gbc);

        painelFormulario.add(painelAux, BorderLayout.CENTER);

        JButton btnAdiciona = new JButton("Adiciona");
        btnAdiciona.addActionListener(e -> {
            String produto = tfProd.getText();
            int quantidade = Integer.parseInt(tfQtd.getText());
            double preco = Double.parseDouble(tfPreco.getText());

            DefaultListModel<Produto> model = (DefaultListModel<Produto>)produtos.getModel();
            model.addElement(new Produto(produto, preco, quantidade));
        });

        painelFormulario.add(btnAdiciona, BorderLayout.SOUTH);

        this.add(painelFormulario, BorderLayout.WEST);
    }

    private void desenhaListaDeProdutos() {
        JPanel painelLista = new JPanel(new BorderLayout());

        DefaultListModel<Produto> model = new DefaultListModel<>();

        produtos = new JList<>(model);

        painelLista.add(new JScrollPane(produtos), BorderLayout.CENTER);

        JPanel painelRodape = new JPanel(new FlowLayout());
        painelRodape.add(new JLabel("Total: "));
        painelRodape.add(new JTextArea("1000"));
        painelLista.add(painelRodape, BorderLayout.SOUTH);

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
        String[] formaPagamento = {"PIX", "DINHEIRO", "CARTAO_DEBITO", "CARTAO_CREDITO"};
        painelCliente.add(new JComboBox<>(formaPagamento));
        gbc.gridy = 0;

        painelRodape.add(painelCliente, gbc);

        JPanel painelEntregaTaxa = new JPanel();
        painelEntregaTaxa.setLayout(new FlowLayout());
        String[] formaEntrega = {"RETIRADA_NA_LOJA", "ENTREGA_CLIENTE"};
        painelEntregaTaxa.add(new JComboBox<>(formaEntrega));
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
    