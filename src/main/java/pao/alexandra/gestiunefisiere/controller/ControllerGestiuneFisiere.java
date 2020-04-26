package pao.alexandra.gestiunefisiere.controller;

import pao.alexandra.gestiunefisiere.model.fisier.Fisier;
import pao.alexandra.gestiunefisiere.model.fisier.Folder;
import pao.alexandra.gestiunefisiere.model.securitate.Permisiune;
import pao.alexandra.gestiunefisiere.model.utilizator.Utilizator;
import pao.alexandra.gestiunefisiere.serviciu.ServiciuGestiuneFisiere;
import pao.alexandra.gestiunefisiere.serviciu.ServiciuGrupUtilizatori;
import pao.alexandra.gestiunefisiere.serviciu.ServiciuPermisiuni;
import pao.alexandra.gestiunefisiere.serviciu.ServiciuUtilizatori;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

public class ControllerGestiuneFisiere {

    private ServiciuGestiuneFisiere serviciuGestiuneFisiere;
    private ServiciuPermisiuni serviciuPermisiuni;
    private ServiciuUtilizatori serviciuUtilizatori;
    private ServiciuGrupUtilizatori serviciuGrupUtilizatori;

    private String delimitatorSistemOperare = Pattern.quote(File.separator);

    public ControllerGestiuneFisiere(UUID adminId, Utilizator admin) {
        serviciuPermisiuni = new ServiciuPermisiuni();
        serviciuUtilizatori = new ServiciuUtilizatori();
        serviciuGrupUtilizatori = new ServiciuGrupUtilizatori();

        adaugaUtilizator(adminId, admin);
        serviciuPermisiuni.adaugaGrupPermisiuneNoua(UUID.nameUUIDFromBytes("0".getBytes()), "admin", adminId, Permisiune.administrator());
        serviciuPermisiuni.adaugaGrupPermisiuneNoua(UUID.nameUUIDFromBytes("1".getBytes()), "simplu", adminId, new HashSet<>(Collections.singletonList(Permisiune.CITIRE)));

        Folder root = new Folder(null, "root", serviciuPermisiuni.getPermisiuni().keySet());
        serviciuGestiuneFisiere = new ServiciuGestiuneFisiere(root);
    }

    public UUID adaugaFolder(UUID utilizator, String cale) {
        UUID folderCurent = UUID.nameUUIDFromBytes("0L".getBytes());
        for (String numeFolderCurent : caleDinString(cale)) {
            folderCurent = serviciuGestiuneFisiere.adaugaFolder(genereazaId(), folderCurent, numeFolderCurent);
        }
        Optional<Folder> folder = serviciuGestiuneFisiere.cautaFolder(folderCurent);
        if (folder.isPresent() && serviciuPermisiuni.verificaPermisiune(utilizator, folder.get(), Permisiune.SCRIERE)) {
            return folderCurent;
        } else {
            return null;
        }
    }

    public UUID adaugaFisier(UUID utilizator, String cale, Fisier fisier) {
        UUID folderCurent = adaugaFolder(utilizator, cale);
        Optional<Folder> folder = serviciuGestiuneFisiere.cautaFolder(folderCurent);
        if (folder.isPresent() && serviciuPermisiuni.verificaPermisiune(utilizator, folder.get(), Permisiune.SCRIERE)) {
            return serviciuGestiuneFisiere.adaugaFisier(genereazaId(), folderCurent, fisier);
        } else {
            return null;
        }
    }

    public void stergeFolder(UUID utilizator, String cale) {
        UUID folderCurent = UUID.nameUUIDFromBytes("0L".getBytes());
        boolean existaFolder = true;
        for (String numeFolderCurent : caleDinString(cale)) {
            Optional<Map.Entry<UUID, Folder>> folderGasit = serviciuGestiuneFisiere.cautaFolderDupaNume(numeFolderCurent);
            if (folderGasit.isPresent() && folderGasit.get().getValue().getParinteId().equals(folderCurent)) {
                folderCurent = folderGasit.get().getKey();
            } else {
                existaFolder = false;
                return;
            }
        }
        if (existaFolder) {
            Optional<Folder> folder = serviciuGestiuneFisiere.cautaFolder(folderCurent);
            if (folder.isPresent() && serviciuPermisiuni.verificaPermisiune(utilizator, folder.get(), Permisiune.STERGERE)) {
                serviciuGestiuneFisiere.stergeFolder(folderCurent);
            }
        }
    }

    public void stergeFisier(UUID utilizator, String cale, Fisier fisier) {
        UUID folderCurent = UUID.nameUUIDFromBytes("0L".getBytes());
        boolean existaFolder = true;
        for (String numeFolderCurent : caleDinString(cale)) {
            Optional<Map.Entry<UUID, Folder>> folderGasit = serviciuGestiuneFisiere.cautaFolderDupaNume(numeFolderCurent);
            if (folderGasit.isPresent() && folderGasit.get().getValue().getParinteId().equals(folderCurent)) {
                folderCurent = folderGasit.get().getKey();
            } else {
                existaFolder = false;
                return;
            }
        }
        if (existaFolder) {
            Optional<Folder> folder = serviciuGestiuneFisiere.cautaFolder(folderCurent);
            if (folder.isPresent() && serviciuPermisiuni.verificaPermisiune(utilizator, folder.get(), Permisiune.STERGERE)) {
                serviciuGestiuneFisiere.stergeFisier(folderCurent, fisier);
            }
        }
    }

    public void redenumesteFolder(UUID utilizator, String cale, String numeNou) {
        UUID folderCurent = UUID.nameUUIDFromBytes("0L".getBytes());
        boolean existaFolder = true;
        for (String numeFolderCurent : caleDinString(cale)) {
            Optional<Map.Entry<UUID, Folder>> folderGasit = serviciuGestiuneFisiere.cautaFolderDupaNume(numeFolderCurent);
            if (folderGasit.isPresent() && folderGasit.get().getValue().getParinteId().equals(folderCurent)) {
                folderCurent = folderGasit.get().getKey();
            } else {
                existaFolder = false;
                return;
            }
        }
        if (existaFolder) {
            Optional<Folder> folder = serviciuGestiuneFisiere.cautaFolder(folderCurent);
            if (folder.isPresent() && serviciuPermisiuni.verificaPermisiune(utilizator, folder.get(), Permisiune.EDITARE)) {
                serviciuGestiuneFisiere.redenumesteFolder(folderCurent, numeNou);
            }
        }
    }

    public void redenumesteFisier(UUID utilizator, String cale, String fisierExistent, String numeNou) {
        UUID folderCurent = UUID.nameUUIDFromBytes("0L".getBytes());
        boolean existaFolder = true;
        for (String numeFolderCurent : caleDinString(cale)) {
            Optional<Map.Entry<UUID, Folder>> folderGasit = serviciuGestiuneFisiere.cautaFolderDupaNume(numeFolderCurent);
            if (folderGasit.isPresent() && folderGasit.get().getValue().getParinteId().equals(folderCurent)) {
                folderCurent = folderGasit.get().getKey();
            } else {
                existaFolder = false;
                return;
            }
        }
        if (existaFolder) {
            UUID finalFolderCurent = folderCurent;
            serviciuGestiuneFisiere.cautaFisierDupaNume(folderCurent, fisierExistent).ifPresent(fisier -> {
                Optional<Folder> folder = serviciuGestiuneFisiere.cautaFolder(finalFolderCurent);
                if (folder.isPresent() && serviciuPermisiuni.verificaPermisiune(utilizator, folder.get(), Permisiune.EDITARE)) {
                    serviciuGestiuneFisiere.redenumesteFisier(fisier.getKey(), numeNou);
                }
            });
        }
    }

    public UUID adaugaUtilizator(Utilizator utilizator) {
        return serviciuUtilizatori.adaugaUtilizator(genereazaId(), utilizator);
    }

    public UUID adaugaUtilizator(UUID id, Utilizator utilizator) {
        return serviciuUtilizatori.adaugaUtilizator(id, utilizator);
    }

    public UUID adaugaGrupPermisiuneNou(UUID utilizator, String numeGrupPermisiune, Set<Permisiune> permisiuni) {
        return serviciuPermisiuni.adaugaGrupPermisiuneNoua(genereazaId(), numeGrupPermisiune, utilizator, permisiuni);
    }

    public UUID adaugaUtilizatorLaGrupPermisiune(UUID utilizator, UUID idUtilizatorNou, UUID grupPermisiune) {
        return serviciuPermisiuni.adaugaUtilizatorLaGrupPermisiune(grupPermisiune, utilizator, idUtilizatorNou);
    }

    public void adaugaGrupPermisiuneLaFolder(UUID utilizator, String cale, UUID idGrupPermisiune) {
        UUID folderCurent = UUID.nameUUIDFromBytes("0L".getBytes());
        boolean existaFolder = true;
        for (String numeFolderCurent : caleDinString(cale)) {
            Optional<Map.Entry<UUID, Folder>> folderGasit = serviciuGestiuneFisiere.cautaFolderDupaNume(numeFolderCurent);
            if (folderGasit.isPresent() && folderGasit.get().getValue().getParinteId().equals(folderCurent)) {
                folderCurent = folderGasit.get().getKey();
            } else {
                existaFolder = false;
                return;
            }
        }
        if (existaFolder) {
            Optional<Folder> folder = serviciuGestiuneFisiere.cautaFolder(folderCurent);
            if (folder.isPresent() && serviciuPermisiuni.verificaPermisiune(utilizator, folder.get(), Permisiune.EDITARE)) {
                serviciuGestiuneFisiere.adaugaPermisiune(folderCurent, idGrupPermisiune);
            }
        }
    }

    public void afiseazaIerarhieFoldere(UUID utilizator) {
        Optional<Folder> folder = serviciuGestiuneFisiere.cautaFolder(serviciuGestiuneFisiere.getRoot());
        if (folder.isPresent() && serviciuPermisiuni.verificaPermisiune(utilizator, folder.get(), Permisiune.SCRIERE)) {
            System.out.println(serviciuGestiuneFisiere.toString());
        }
    }

    private UUID genereazaId() {
        return UUID.randomUUID();
    }

    private List<String> caleDinString(String cale) {
        return Arrays.asList(cale.split(delimitatorSistemOperare));
    }
}
