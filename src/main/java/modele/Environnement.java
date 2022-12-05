package modele;

import application.SimuVirus;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public class Environnement {
    //Une liste dynamique de Personne
    private int tour;
    private List<Personne> listePersonnesDansEnvt;
    // ses dimensions x et y
    private int x;
    private int y;

    // lien vers l'application de contrôle
    private SimuVirus virusControle;

    // lien vers lieu
    private Lieu lieu;

    //  permet de récupérer un lieu à partir de sa position
    private Hashtable<Point, Lieu> plan;

    // annuaire : table (Type; liste de lieux) qui permet de récupérer la liste des lieux associés à un type de lieu
    private Hashtable<TypeLieu, ArrayList<Lieu>> annuaire;


    public Environnement(int x, int y, SimuVirus virusControle) {
        this.setListePersonnesDansEnvt(new ArrayList<>());
        this.setVirusControle(virusControle);
        this.tour = 0;
        this.x = x;
        this.y = y;
        this.plan = new Hashtable<Point, Lieu>();
        this.annuaire = new Hashtable<TypeLieu, ArrayList<Lieu>>();
    }


    //bouger : demande à chaque personne dans l'environnement de bouger
    public void bouger(double widthstep, double heightstep, double decalage) {
        double x;
        double y;
        Random hasard = new Random();
        for (Personne p : listePersonnesDansEnvt) {
            //this.getVirusControle().updatePos(p);
            p.updatepos(hasard.nextInt((int) this.getX()), (hasard.nextInt((int) this.getY())));
            //p.getPersImg().updatepos(widthstep * (decalage + p.getPoint().getX()), +heightstep * (decalage + p.getPoint().getY()));
            this.getVirusControle().updatePos(p);
            //  System.out.println("Les personnes se deplacent");
        }
    }


    public void afficherActivite() {
        for (Personne p : listePersonnesDansEnvt) {
            System.out.println(p.getActivite());
        }
    }

    public void sactiver() {
        TypeLieu tdl = null;
        Random hasard = new Random();
        //int activite = 1;
        Activite activiteActuel = null;
        for (Personne p : this.listePersonnesDansEnvt) {
            if (p.getActivite().equals(Activite.Travail)) {
                tdl = TypeLieu.Magasin;
                activiteActuel = Activite.Course;
            } else if (p.getActivite().equals(Activite.Repos)) {
                tdl = TypeLieu.Entreprise;
                activiteActuel = Activite.Travail;
            } else if (p.getActivite().equals(Activite.Course)) {
                tdl = TypeLieu.Domicile;
                activiteActuel = Activite.Repos;
            }
            p.setActivite(activiteActuel);
            ArrayList<Lieu> lieux = getAnnuaire().get(tdl);
            Lieu l = lieux.get(hasard.nextInt(lieux.size()));
            p.updatepos((int) l.getPoint().getX(), (int) l.getPoint().getY());
            this.getVirusControle().updatePos(p);
        }
        System.out.println("Les pions se déplacent");
    }


    //avancerTemps : demande à chaque personne de calculer son temps de contact avec malade
    public void avancerTemps() {
        this.tour++;
        List<Personne> listContaminer = new ArrayList<Personne>();
        // System.out.println("Le temps avance, tour n°" + tour);
        // On crée un booleen contamine qui dicte si p est a proximite
        for (Personne pers1 : listePersonnesDansEnvt) {
            for (Personne pers2 : listePersonnesDansEnvt) {
                if ((pers1.getPoint().getX() == pers2.getPoint().getX() && (pers1.getPoint().getY()) == pers2.getPoint().getY() && pers1.isEtatMalade() && !pers2.isEtatMalade() && pers1.getId() != pers2.getId())) {
                    if (!listContaminer.contains(pers2)) {
                        pers2.setDuree(pers2.getDuree()+1);
                        listContaminer.add(pers2);
                        if (pers2.getDuree() == 1) {
                            pers2.getPersImg().setFill(Color.LIGHTGOLDENRODYELLOW);
                            //    System.out.println("Stade 1 : " + pers2);
                        } else if (pers2.getDuree() == 2) {
                            pers2.getPersImg().setFill(Color.LIGHTPINK);
                            //    System.out.println("Stade 2 : " + pers2);
                        } else if (pers2.getDuree() >= 3) {
                            // System.out.println("Stade 3 : " + pers2);
                            switchMalade(pers2);
                        }
                    }
                }
            }
        }
        updateFXML();
        System.out.println("tour n°" + tour);
    }

    private void updateFXML() {
        int infectee = 0;
        int sain = 0;
        int persMax = 0;
        ArrayList<Personne> listeInfectee = new ArrayList<>();
        ArrayList<Personne> listeSain = new ArrayList<>();
        for (Personne p : listePersonnesDansEnvt) {
            persMax++;
            if (p.isEtatMalade() && (!listeInfectee.contains(p))) {
                infectee++;
                listeInfectee.add(p);
            }
            if (!p.isEtatMalade() && (!listeSain.contains(p))) {
                sain++;
                listeSain.add(p);
            }
        }
        this.getVirusControle().controller.updateNbPers(persMax/2);
        this.getVirusControle().controller.updateNbSain(sain);
        this.getVirusControle().controller.updateNbInfectees(infectee);
        this.getVirusControle().controller.updateTour(tour);
    }


    //addPersonne(Personne p) : ajoute une personne dans la liste, fonction lancée par l'application
    public void addPersonne(Personne p) {
        this.getListePersonnesDansEnvt().add(p);
    }

    // switchMalade(int x, int y) : récupère la personne en colonne, ligne (x,y) et bascule son état malade, fonction lancée par l'application
    public void switchMalade(int x, int y) {
        for (Personne p : this.getListePersonnesDansEnvt()) {
            if (p.getPoint().getX() == x && p.getPoint().getY() == y) {
                p.setEtatMalade(true);
                // System.out.println("Malade à la positon (" + p.getPoint().getX() + "," + p.getPoint().getY() + ")");
                p.getPersImg().changeColorMalade();
                break;
            }
        }
    }

    public void switchMalade(Personne p) {
        p.setEtatMalade(true);
        p.getPersImg().changeColorMalade();
    }

    public void switchMaladeRandom() {
        Random hasard = new Random();
        Personne persRandom = listePersonnesDansEnvt.get(hasard.nextInt(listePersonnesDansEnvt.size()));
        while (persRandom.isEtatMalade()) {
            persRandom = listePersonnesDansEnvt.get(hasard.nextInt(listePersonnesDansEnvt.size()));
        }
        persRandom.setEtatMalade(true);
        //System.out.println("Malade à la positon (" + persRandom.getPoint().getX() + "," + persRandom.getPoint().getY() + ")");
        persRandom.getPersImg().changeColorMalade();
    }


    public void addLieu(Lieu l) {
        this.plan.put(l.getPoint(), l);
    }

    private void putTypeDeLieuAnnuaire(Lieu l) {
        annuaire.put(l.getTypeDeLieu(), new ArrayList<Lieu>());
    }

    public void putLieuAnnuaire(Lieu l) {
        List<Lieu> lieux = annuaire.get(l.getTypeDeLieu());
        if (lieux == null) putTypeDeLieuAnnuaire(l);
        annuaire.get(l.getTypeDeLieu()).add(l);
    }


    public int getTour() {
        return tour;
    }

    public void setTour(int tour) {
        this.tour = tour;
    }

    public List<Personne> getListePersonnesDansEnvt() {
        return listePersonnesDansEnvt;
    }

    public void setListePersonnesDansEnvt(List<Personne> listePersonnesDansEnvt) {
        this.listePersonnesDansEnvt = listePersonnesDansEnvt;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public SimuVirus getVirusControle() {
        return virusControle;
    }

    public void setVirusControle(SimuVirus virusControle) {
        this.virusControle = virusControle;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    public Hashtable<Point, Lieu> getPlan() {
        return plan;
    }

    public void setPlan(Hashtable<Point, Lieu> plan) {
        this.plan = plan;
    }

    public Hashtable<TypeLieu, ArrayList<Lieu>> getAnnuaire() {
        return annuaire;
    }

    public void setAnnuaire(Hashtable<TypeLieu, ArrayList<Lieu>> annuaire) {
        this.annuaire = annuaire;
    }
}


