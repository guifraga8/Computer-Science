package trabalho_grau_b;

public class Pessoa {
    private long CPF;
    private String RG;
    private String nomeCompleto;
    private String dataNascimento;
    private String cidade;

    public Pessoa(long CPF, String RG, String nomeCompleto, String dataNascimento, String cidade) {
        this.CPF = CPF;
        this.RG = RG;
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.cidade = cidade;
    }

    public long getCPF() {
        return this.CPF;
    }
    public void setCPF(long CPF) {
        this.CPF = CPF;
    }
    public String getRG() {
        return this.RG;
    }
    public void setRG(String RG) {
        this.RG = RG;
    }
    public String getNomeCompleto() {
        return this.nomeCompleto;
    }
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }
    public String getDataNascimento() {
        return this.dataNascimento;
    }
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public String getCidade() {
        return this.cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return this.nomeCompleto + ". RG: " + this.RG + ". CPF: " + this.CPF + ". Nasceu em " + this.dataNascimento + ". Residente de " + this.cidade + ".\n";
    }
}
