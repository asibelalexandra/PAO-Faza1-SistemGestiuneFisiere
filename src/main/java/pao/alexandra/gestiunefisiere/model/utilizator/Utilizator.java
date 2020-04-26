package pao.alexandra.gestiunefisiere.model.utilizator;

public class Utilizator {

    private String nume;
    private String prenume;
    private int varsta;

    public Utilizator(String nume, String prenume, int varsta) {
        this.nume = nume;
        this.prenume = prenume;
        this.varsta = varsta;
    }

    public Utilizator(Utilizator utilizator) {
        this.nume = utilizator.nume;
        this.prenume = utilizator.prenume;
        this.varsta = utilizator.varsta;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public int getVarsta() {
        return varsta;
    }
}
