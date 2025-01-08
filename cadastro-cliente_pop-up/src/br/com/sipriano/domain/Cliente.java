package br.com.sipriano.domain;

import java.util.Objects;

public class Cliente {
    private String nome;
    private Long cpf;
    private Long tel;
    private String end;
    private Integer numero;
    private String cidade;
    private String estado;

    public Cliente() {

    }


    public Cliente(String cpf, String nome, String tel, String end, String num, String cidade, String estado) {
        this.cpf = Long.valueOf(cpf.trim());
        if (nome == null) {
            this.nome = null;
        } else {
            this.nome = nome.strip();
        }
        if (tel == null) {
            this.tel = null;
        } else {
            this.tel = Long.valueOf(tel.strip());
        }
        if (end == null) {
            this.end = null;
        } else {
            this.end = end.strip();
        }
        if (num == null) {
            numero = null;
        } else {
            this.numero = Integer.valueOf(num.strip());
        }
        if (cidade == null) {
            this.cidade = null;
        } else {
            this.cidade = cidade.strip();
        }
        if (estado == null) {
            this.estado = null;
        } else {
            this.estado = estado.strip();
        }


    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public Long getTel() {
        return tel;
    }

    public void setTel(Long tel) {
        this.tel = tel;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cpf, cliente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return "domain.Cliente{" +
                "nome='" + nome + '\'' +
                ", cpf=" + cpf +
                '}';
    }
}