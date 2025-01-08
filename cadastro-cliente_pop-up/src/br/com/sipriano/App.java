package br.com.sipriano;

import br.com.sipriano.dao.ClienteMapDAO;
import br.com.sipriano.dao.IClienteDAO;
import br.com.sipriano.domain.Cliente;

import javax.swing.*;

public class App {

    private static IClienteDAO iClienteDAO;

    public static void main(String[] args) {
        iClienteDAO = new ClienteMapDAO();

        String opcao = JOptionPane.showInputDialog(null,
                "1 - cadastro, 2 - consultar cpf, 3 - excluir, 4 - alteração, 5 - Buscar todos, 6 - Sair",
                "Cadastro", JOptionPane.INFORMATION_MESSAGE);

        while (!isOpcaoValida(opcao)) {
            if ("".equals(opcao)) {
                sair();
            }
            opcao = JOptionPane.showInputDialog(null,
                    "Opção inválida! digite 1 - cadastro, 2 - consultar cpf, 3 - excluir, 4 - alteração, 5 - Buscar todos, 6 - Sair",
                    "Cadastro", JOptionPane.INFORMATION_MESSAGE);
        }

        while (isOpcaoValida(opcao)) {
            if (isOpcaoSair(opcao)) {
                sair();

            } else if (isCadastro(opcao)) {

                cadastrar(obterDados());

            } else if (isConsultar(opcao)) {
                String dados = JOptionPane.showInputDialog(null,
                        "Digite o cpf",
                        "Consultar", JOptionPane.INFORMATION_MESSAGE);

                consultar(dados);

            } else if (isExcluir(opcao)) {
                String cpf = JOptionPane.showInputDialog(null,
                        "Digite o Cpf do cliente o qual quer excluir: ",
                        "Exclusão", JOptionPane.INFORMATION_MESSAGE);
                excluir(cpf);

            } else if (isAlterar(opcao)) {
                String cpf = JOptionPane.showInputDialog(null,
                        "Digite o cpf do usuário o qual quer alterar: ",
                        "Alterar", JOptionPane.INFORMATION_MESSAGE);
                alterar(cpf);

            } else if (isBuscarTodos(opcao)) {
                buscarTodos();
            }

            opcao = JOptionPane.showInputDialog(null,
                    "1 - cadastro, 2 - consultar cpf, 3 - excluir, 4 - alteração, 5 - Buscar todos, 6 - Sair",
                    "Cadastro", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private static void cadastrar(String[] dadosSeparados) {

        Cliente cliente = instanciaCliente(dadosSeparados);
        Boolean isCadastrado = iClienteDAO.cadastrar(cliente);
        if (isCadastrado) {
            JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso ", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Cliente já se encontra cadastrado", "Erro", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void consultar(String dados) {
        Long cpf = validarCPF(dados);
        while (cpf == null) {
            dados = JOptionPane.showInputDialog(null,
                    "Digite o cpf",
                    "Consultar", JOptionPane.INFORMATION_MESSAGE);
            cpf = validarCPF(dados);
        }
        Cliente cliente = iClienteDAO.consultar(cpf);
        if (cliente != null) {
            JOptionPane.showMessageDialog(null, "Cliente encontrado: " + cliente.toString(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Cliente não encontrado: ", "Fail", JOptionPane.INFORMATION_MESSAGE);
        }


    }

    private static void excluir(String cpf) {
        Cliente cliente = iClienteDAO.consultar(Long.parseLong(cpf));
        if (cliente != null) {
            iClienteDAO.excluir(cliente.getCpf());
            JOptionPane.showMessageDialog(null, "Cliente deletado", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Cliente não encontrado", "Falha", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void alterar(String cpf) {
        Cliente cliente = iClienteDAO.consultar(Long.parseLong(cpf));
        if (cliente != null) {
            String[] dadosSeparados = obterDados();
            iClienteDAO.alterar(instanciaCliente(dadosSeparados));
            JOptionPane.showMessageDialog(null, "Cliente alterado", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Cliente não encontrado", "Falha", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void buscarTodos() {
        JOptionPane.showMessageDialog(null, iClienteDAO.buscarTodos(), "Consultar tods", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void sair() {
        JOptionPane.showMessageDialog(null, "Até logo: ", "Sair", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private static boolean isOpcaoValida(String opcao) {
        if ("1".equals(opcao) || "2".equals(opcao)
                || "3".equals(opcao) || "4".equals(opcao)
                || "5".equals(opcao) || "6".equals(opcao)) {
            return true;
        }
        return false;
    }

    private static boolean isCadastro(String opcao) {
        if ("1".equals(opcao)) {
            return true;
        }
        return false;
    }

    private static boolean isConsultar(String opcao) {
        if ("2".equals(opcao)) {
            return true;
        }
        return false;
    }

    private static boolean isExcluir(String opcao) {
        if ("3".equals(opcao)) {
            return true;
        }
        return false;
    }

    private static boolean isAlterar(String opcao) {
        if ("4".equals(opcao)) {
            return true;
        }
        return false;
    }

    private static boolean isBuscarTodos(String opcao) {
        if ("5".equals(opcao)) {
            return true;
        }
        return false;
    }

    private static boolean isOpcaoSair(String opcao) {
        if ("6".equals(opcao)) {
            return true;
        }
        return false;
    }

    private static String[] obterDados() {
        String dados = JOptionPane.showInputDialog(null,
                "Digite os dados do cliente separados por vígula, conforme exemplo: CPF, Nome, Telefone, Endereço, Número, Cidade, Estado",
                "Cadastro", JOptionPane.INFORMATION_MESSAGE);
        String[] dadosSeparados = new String[7];
        String[] temp = dados.split(",");
        System.arraycopy(temp, 0, dadosSeparados, 0, temp.length);

        while (!validarDados(dadosSeparados)) {
            dados = JOptionPane.showInputDialog(null,
                    "Dados faltando! Digite os dados do cliente separados por vígula, conforme exemplo: CPF, Nome, Telefone, Endereço, Número, Cidade, Estado",
                    "Cadastro", JOptionPane.INFORMATION_MESSAGE);
            dadosSeparados = new String[7];
            temp = dados.split(",");
            System.arraycopy(temp, 0, dadosSeparados, 0, temp.length);
        }

        return dadosSeparados;
    }

    private static boolean validarDados(String[] dadosSeparados) {
        for (String campo : dadosSeparados) {
            if (campo == null || "".equals(campo)) {
                return false;
            }
        }
        return true;
    }

    private static Cliente instanciaCliente(String[] dadosSeparados) {

        return new Cliente(dadosSeparados[0], dadosSeparados[1], dadosSeparados[2], dadosSeparados[3], dadosSeparados[4], dadosSeparados[5], dadosSeparados[6]);
    }


    private static Long validarCPF(String dados) {
        String cpf = dados.replaceAll("[^0-9]", ""); // remove caracteres não numéricos

        if (cpf.matches("[0-9]+")) {
            Long cpfLong = Long.parseLong(cpf);
            return cpfLong;
        } else {
            JOptionPane.showMessageDialog(null, "Digite sómente os 11 números");
            return null;
        }
    }


}

