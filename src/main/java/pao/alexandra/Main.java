package pao.alexandra;

import pao.alexandra.gestiunefisiere.controller.ControllerGestiuneFisiere;
import pao.alexandra.gestiunefisiere.model.fisier.Fisier;
import pao.alexandra.gestiunefisiere.model.fisier.TipFisier;
import pao.alexandra.gestiunefisiere.model.securitate.Permisiune;
import pao.alexandra.gestiunefisiere.model.utilizator.Utilizator;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        UUID idAdmin = UUID.nameUUIDFromBytes("0L".getBytes());
        Utilizator utilizator = new Utilizator("admin", "admin", 0);
        ControllerGestiuneFisiere aplicatie = new ControllerGestiuneFisiere(idAdmin, utilizator);

        Fisier fisierTest = new Fisier("fisierTest", TipFisier.TEXT);

        aplicatie.adaugaFolder(idAdmin, "test\\teste\\multeteste");
        aplicatie.afiseazaIerarhieFoldere(idAdmin);

        aplicatie.adaugaFisier(idAdmin, "test\\testnou", fisierTest);
        aplicatie.afiseazaIerarhieFoldere(idAdmin);

        aplicatie.stergeFolder(idAdmin, "test\\teste\\multeteste");
        aplicatie.afiseazaIerarhieFoldere(idAdmin);

        aplicatie.adaugaFolder(idAdmin, "test\\teste\\multeteste");
        aplicatie.afiseazaIerarhieFoldere(idAdmin);

        aplicatie.adaugaFisier(idAdmin, "test\\teste", fisierTest);
        aplicatie.afiseazaIerarhieFoldere(idAdmin);

        aplicatie.stergeFolder(idAdmin, "test\\teste\\multeteste");
        aplicatie.afiseazaIerarhieFoldere(idAdmin);

        aplicatie.stergeFisier(idAdmin, "test\\testnou", fisierTest);
        aplicatie.afiseazaIerarhieFoldere(idAdmin);

        aplicatie.adaugaFisier(idAdmin, "test\\testnou", fisierTest);
        aplicatie.afiseazaIerarhieFoldere(idAdmin);

        aplicatie.redenumesteFolder(idAdmin, "test\\testnou", "testnouredenumit");
        aplicatie.afiseazaIerarhieFoldere(idAdmin);

        aplicatie.redenumesteFisier(idAdmin, "test\\testnouredenumit", fisierTest.toString(), "fisierTestRedenumit");
        aplicatie.afiseazaIerarhieFoldere(idAdmin);

        Utilizator alexandra = new Utilizator("Alexandra", "Ali", 0);
        UUID idAlexandra = aplicatie.adaugaUtilizator(alexandra);
        UUID grupNou = aplicatie.adaugaGrupPermisiuneNou(idAlexandra, "permisiuneNoua", Permisiune.getDefault());
        aplicatie.adaugaGrupPermisiuneLaFolder(idAdmin, "", grupNou);
        aplicatie.afiseazaIerarhieFoldere(idAlexandra);
    }
}
