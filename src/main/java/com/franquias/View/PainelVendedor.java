package com.franquias.View;

import javax.swing.*;
import java.awt.*;

import com.franquias.Controller.AplicacaoPrincipal;

public class PainelVendedor extends JPanel {

    private final int WIDTH = 500;
    private final int HEIGHT = 400;
    private final int V_GAP = 10;
    private final int H_GAP = 5;

    JPasswordField pfSenha;
    JTextField tfEmail;

    public PainelVendedor(AplicacaoPrincipal tela) {
        // this.setLayout(new GridBagLayout());
        this.setSize(WIDTH, HEIGHT);
        tela.telaPricipal.setDefaultCloseOperation(tela.telaPricipal.EXIT_ON_CLOSE);

        // Container painel = this.getContentPane();

        // painel.setLayout (new BorderLayout ());
        // painel.add (new JButton ("Button 1"), BorderLayout.NORTH);
        // painel.add (new JButton ("Button 2"), BorderLayout.CENTER);
        // painel.add (new JButton ("Button 3"), BorderLayout.WEST);
        // painel.add (new JButton ("Button 4"), BorderLayout.SOUTH);
        // painel.add (new JButton ("Button 5"), BorderLayout.EAST);
        
        // desenhathisLogin();
        desenhathisVendedor();

        this.setVisible(true);
    }

    public void desenhathisVendedor() {
        desenhaBotoes();
        desenhaFormularioDeProdutos();
        desenhaListaDeProdutos();    
        desenhaRodape();
    }

    private void desenhaBotoes() {
        JButton btVender = new JButton("Vender");
        JButton btRegistro = new JButton("Abrir Registro");
        JButton btDeslogar = new JButton("Deslogar");

        JPanel painelOpcoes = new JPanel();
        painelOpcoes.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0;
        painelOpcoes.add(btVender, gbc);
        
        gbc.gridx = 1;
        painelOpcoes.add(btRegistro, gbc);

        gbc.weightx = 1;      
        gbc.gridx = 3;
        // gbc.anchor = GridBagConstraints.EAST;
        gbc.anchor = GridBagConstraints.LINE_END;
        painelOpcoes.add(btDeslogar, gbc);
        
        this.add(painelOpcoes, BorderLayout.NORTH);
    }

    private void desenhaFormularioDeProdutos() {
        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new GridBagLayout());
        painelFormulario.setBackground(Color.lightGray);
        GridBagConstraints gbc = new GridBagConstraints();

        // Espaçamento
        // gbc.gridy = 0;
        // gbc.gridheight = 2;
        // gbc.gridwidth = 1;
        // painelFormulario.add(new JLabel(), gbc);

        // gbc = new GridBagConstraints();

        JLabel jlProd = new JLabel("Produto");
        JTextField tfProd = new JTextField(20);
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;

        gbc.gridx = 0;
        gbc.weightx = 0;
        painelFormulario.add(jlProd, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        painelFormulario.add(tfProd, gbc);

        JPanel painelQtdPreco = new JPanel();
        painelQtdPreco.setLayout(new FlowLayout());
        painelQtdPreco.add(new JLabel("Qtd"));
        painelQtdPreco.add(new JTextField(2));
        painelQtdPreco.add(new JLabel("Preço"));
        painelQtdPreco.add(new JTextField(8));

        gbc = new GridBagConstraints();
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        painelFormulario.add(painelQtdPreco, gbc);

        // Espaçamento
        gbc.gridy = 3;
        gbc.weighty = 1;
        painelFormulario.add(new JLabel(), gbc);

        JButton btnAdiciona = new JButton("Adiciona");
        gbc.gridy = 4;

        painelFormulario.add(btnAdiciona, gbc);

        this.add(painelFormulario, BorderLayout.WEST);
    }

    private void desenhaListaDeProdutos() {
        JPanel painelLista = new JPanel(new GridBagLayout());
        painelLista.setBackground(Color.red);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        
        JList<DefaultListModel> jlProdutos = new JList<>();
        // jlProdutos.addListSelectionListener(new SelecionarProduto());
        painelLista.add(new JScrollPane(jlProdutos), gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;

        JPanel painelRodape = new JPanel(new FlowLayout());
        painelRodape.add(new JLabel("Total: "));
        painelRodape.add(new JTextArea("1000"));
        painelLista.add(painelRodape, gbc);

        this.add(painelLista, BorderLayout.EAST);
    }

    private void desenhaRodape() {
        JPanel painelRodape = new JPanel();
        painelRodape.setLayout(new GridBagLayout());

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

     public void desenhathisLogin() {
        // A criação do painel está correta
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Login"));
        painel.setBackground(Color.lightGray);
        // setPreferredSize é opcional se você quer que o pack() ajuste o tamanho
        painel.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT / 2));

        GridBagConstraints gbc; // Apenas declare aqui

        //--- Linha 0: Email ---
        // Rótulo "Email"
        gbc = new GridBagConstraints(); // Reseta para os padrões
        gbc.insets = new Insets(5, 5, 5, 5); // Define uma margem
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 0; // Linha 0
        gbc.anchor = GridBagConstraints.WEST; // Alinha o texto à direita
        painel.add(new JLabel("Email:"), gbc);

        // Campo de texto para o Email
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; // Coluna 1
        gbc.gridy = 1; // Linha 0
        gbc.fill = GridBagConstraints.HORIZONTAL; // Faz o campo esticar horizontalmente
        gbc.weightx = 1.0; // Permite que a coluna 1 cresça com a janela
        tfEmail = new JTextField(15);
        painel.add(tfEmail, gbc);

        //--- Linha 1: Senha (ORDEM CORRIGIDA) ---
        // Rótulo "Senha"
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 2; // Linha 1
        gbc.anchor = GridBagConstraints.WEST;
        painel.add(new JLabel("Senha:"), gbc);

        // Campo de texto para a Senha
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; // Coluna 1
        gbc.gridy = 3; // Linha 1
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        pfSenha = new JPasswordField(15);
        painel.add(pfSenha, gbc);

        //--- Linha 2: Botão Entrar ---
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 5, 5); // Mais margem no topo
        gbc.gridx = 0; // Começa na coluna 0
        gbc.gridy = 4; // Linha 2
        gbc.gridwidth = 2; // << AQUI SIM: Ocupa 2 colunas para centralizar
        JButton btEntrar = new JButton("Entrar");
        // btEntrar.addActionListener(new Logar(this));
        painel.add(btEntrar, gbc);

        // A sua lógica de "wrapper" para centralizar o painel está perfeita!
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.add(painel, new GridBagConstraints()); // Adiciona o painel ao wrapper
        this.add(wrapperPanel, BorderLayout.CENTER); // Adiciona o wrapper à this
    }

    public void desenhathisVendedor1() {

        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new GridBagLayout());
        panelButtons.setBackground(Color.lightGray);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridheight = 2;
        gbc.weightx = 1;
        // gbc.weighty = 0;
        
        JButton btnVender = new JButton("Vender");
        JButton btnVendas = new JButton("Vendas Realizadas");
        JButton btnLogOut = new JButton("Deslogar");

        panelButtons.add(btnVender, gbc);

        panelButtons.add(btnVendas, gbc);

        panelButtons.add(btnLogOut, gbc);

        gbc = new GridBagConstraints();
        this.add(panelButtons, BorderLayout.NORTH);

        JPanel painelVenda = new JPanel();
        painelVenda.setLayout(new GridBagLayout());
        painelVenda.setBackground(Color.GREEN);

        gbc.weightx = 1;
        gbc.gridy = 0;
        JLabel lbProduto = new JLabel("Produto");
        JTextField tfProduto = new JTextField(15);
        gbc.gridx = 0;
        painelVenda.add(lbProduto, gbc);
        gbc.gridx = 1;
        painelVenda.add(tfProduto, gbc);

        JLabel lbPreco = new JLabel("Preço");
        JTextField tfPreco = new JTextField(5);
        gbc.gridx = 2;
        painelVenda.add(lbPreco, gbc);
        gbc.gridx = 3;
        painelVenda.add(tfPreco, gbc);
        
        JLabel lbQuantidade = new JLabel("Quantidade");
        JTextField tfQuantidade = new JTextField(2);
        gbc.gridx = 4;
        painelVenda.add(lbQuantidade, gbc);
        gbc.gridx = 5;
        painelVenda.add(tfQuantidade, gbc);

        gbc.gridy = 2;
        JLabel lbCliente = new JLabel("Cliente");        
        JTextField tfCliente = new JTextField();
        gbc.gridx = 0;
        painelVenda.add(lbCliente, gbc);
        gbc.gridx = 1;
        painelVenda.add(tfCliente, gbc);

        JLabel lbData = new JLabel("Data");
        JTextField tfData= new JTextField();
        gbc.gridx = 2;
        painelVenda.add(lbData, gbc);
        gbc.gridx = 3;
        painelVenda.add(tfData, gbc);

        JLabel lbHora = new JLabel("Hora");
        JTextField tfHora = new JTextField();
        gbc.gridx = 4;
        painelVenda.add(lbHora, gbc);
        gbc.gridx = 5;
        painelVenda.add(tfHora, gbc);

        gbc.gridy = 4;
        JLabel lbFormaPagamento = new JLabel("Forma de pagamento");
        JTextField tfFormaPagamento= new JTextField();
        gbc.gridx = 0;
        painelVenda.add(lbFormaPagamento, gbc);
        gbc.gridx = 1;
        painelVenda.add(tfFormaPagamento, gbc);

        JLabel lbTipoEntrega = new JLabel("Tipo de entrega");
        JTextField tfTipoEntrega = new JTextField();
        gbc.gridx = 2;
        painelVenda.add(lbTipoEntrega, gbc);
        gbc.gridx = 3;
        painelVenda.add(tfTipoEntrega, gbc);

        JLabel lbTaxas = new JLabel("Taxas");
        JTextField  tfTaxas = new JTextField();
        gbc.gridx = 4;
        painelVenda.add(lbTaxas, gbc);
        gbc.gridx = 5;
        painelVenda.add(tfTaxas, gbc);

        gbc.gridy = 6;
        JLabel lbValorTotal = new JLabel("Valor total");
        JTextField tfValorTotal  = new JTextField();
        gbc.gridx = 0;
        painelVenda.add(lbValorTotal, gbc);
        gbc.gridx = 1;
        painelVenda.add(tfValorTotal, gbc);

        this.add(painelVenda, BorderLayout.CENTER);
    }

    public void desenhathisVendedor2() {

    // Define o layout principal do frame como BorderLayout
    this.setLayout(new BorderLayout(5, 5));

    // --- PAINEL DE BOTÕES (usando FlowLayout para simplicidade) ---
    JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panelButtons.setBackground(Color.lightGray);
    panelButtons.add(new JButton("Vender"));
    panelButtons.add(new JButton("Vendas Realizadas"));
    panelButtons.add(new JButton("Deslogar"));
    this.add(panelButtons, BorderLayout.NORTH);

    // --- PAINEL DO FORMULÁRIO ---
    JPanel painelVenda = new JPanel(new GridBagLayout());
    painelVenda.setBorder(BorderFactory.createTitledBorder("Registro de Venda"));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento entre os componentes

    //==============================================
    // LINHA 0: Produto, Preço, Quantidade
    //==============================================
    gbc.gridy = 0; // Trabalhando na linha 0

    // --- Configuração para RÓTULOS (não esticam) ---
    gbc.fill = GridBagConstraints.NONE;
    gbc.weightx = 0; // Colunas dos rótulos não crescem
    gbc.anchor = GridBagConstraints.EAST; // Alinha à direita da célula

    gbc.gridx = 0;
    painelVenda.add(new JLabel("Produto:"), gbc);
    gbc.gridx = 2;
    painelVenda.add(new JLabel("Preço:"), gbc);
    gbc.gridx = 4;
    painelVenda.add(new JLabel("Quantidade:"), gbc);

    // --- Configuração para CAMPOS DE TEXTO (esticam) ---
    gbc.fill = GridBagConstraints.HORIZONTAL; // << FAZ ESTICAR
    gbc.weightx = 1.0; // << PERMITE A COLUNA CRESCER
    gbc.anchor = GridBagConstraints.WEST; // Alinha à esquerda da célula

    gbc.gridx = 1;
    JTextField tfProduto = new JTextField(15);
    painelVenda.add(tfProduto, gbc);

    gbc.gridx = 3;
    JTextField tfPreco = new JTextField(5);
    painelVenda.add(tfPreco, gbc);

    gbc.gridx = 5;
    JTextField tfQuantidade = new JTextField(3);
    painelVenda.add(tfQuantidade, gbc);


    //==============================================
    // LINHA 1: Cliente, Data, Hora
    //==============================================
    gbc.gridy = 1; // Próxima linha

    // Configuração para Rótulos
    gbc.fill = GridBagConstraints.NONE;
    gbc.weightx = 0;
    gbc.anchor = GridBagConstraints.EAST;

    gbc.gridx = 0;
    painelVenda.add(new JLabel("Cliente:"), gbc);
    gbc.gridx = 2;
    painelVenda.add(new JLabel("Data:"), gbc);
    gbc.gridx = 4;
    painelVenda.add(new JLabel("Hora:"), gbc);

    // Configuração para Campos de Texto
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    gbc.anchor = GridBagConstraints.WEST;

    gbc.gridx = 1;
    JTextField tfCliente = new JTextField(15);
    painelVenda.add(tfCliente, gbc);

    gbc.gridx = 3;
    JTextField tfData = new JTextField(5);
    painelVenda.add(tfData, gbc);

    gbc.gridx = 5;
    JTextField tfHora = new JTextField(3);
    painelVenda.add(tfHora, gbc);


    //==============================================
    // LINHA 2: Pagamento, Entrega, Taxas
    //==============================================
    gbc.gridy = 2; // Próxima linha

    // Configuração para Rótulos
    gbc.fill = GridBagConstraints.NONE;
    gbc.weightx = 0;
    gbc.anchor = GridBagConstraints.EAST;
    
    gbc.gridx = 0;
    painelVenda.add(new JLabel("Forma de Pagamento:"), gbc);
    gbc.gridx = 2;
    painelVenda.add(new JLabel("Tipo de Entrega:"), gbc);
    gbc.gridx = 4;
    painelVenda.add(new JLabel("Taxas:"), gbc);

    // Configuração para Campos de Texto
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    gbc.anchor = GridBagConstraints.WEST;

    gbc.gridx = 1;
    JTextField tfFormaPagamento = new JTextField();
    painelVenda.add(tfFormaPagamento, gbc);

    gbc.gridx = 3;
    JTextField tfTipoEntrega = new JTextField();
    painelVenda.add(tfTipoEntrega, gbc);

    gbc.gridx = 5;
    JTextField tfTaxas = new JTextField();
    painelVenda.add(tfTaxas, gbc);


    //==============================================
    // LINHA 3: Valor Total (com um destaque)
    //==============================================
    gbc.gridy = 3; // Próxima linha
    
    // Configuração para o rótulo
    gbc.gridx = 4;
    gbc.fill = GridBagConstraints.NONE;
    gbc.weightx = 0;
    gbc.anchor = GridBagConstraints.EAST;
    JLabel lbValorTotal = new JLabel("Valor Total:");
    lbValorTotal.setFont(lbValorTotal.getFont().deriveFont(Font.BOLD));
    painelVenda.add(lbValorTotal, gbc);

    // Configuração para o campo
    gbc.gridx = 5;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    gbc.anchor = GridBagConstraints.WEST;
    JTextField tfValorTotal = new JTextField();
    tfValorTotal.setEditable(false);
    tfValorTotal.setFont(tfValorTotal.getFont().deriveFont(Font.BOLD, 14f));
    painelVenda.add(tfValorTotal, gbc);

    // Adiciona o formulário de venda no CENTRO da this
    this.add(painelVenda, BorderLayout.CENTER);
}
}

