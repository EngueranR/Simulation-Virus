package application;

import FXML.Controller;
import gui.PersonneImg;
import gui.Place;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import modele.Environnement;
import modele.Lieu;
import modele.Personne;
import modele.TypeLieu;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.Random;


/**
 * classe exemple pour un jeu de pion en java fx
 *
 * @author emmanueladam
 */
public class SimuVirus extends Application implements EventHandler<MouseEvent> {

    /**
     * hauteur du panneau
     */
    private double height;
    /**
     * largeur du panneau
     */
    private double width;
    /**
     * decalage pour centrer le dessin
     */
    private double decalage;

    /**
     * nb pixels utilise en largeur pour une "case de la grille"
     */
    private double widthStep;
    /**
     * nb pixels utilise en hauteur pour une "case de la grille"
     */
    private double heightStep;

    private PersonneImg personneSelectionnee;

    private Group troupe;

    private Environnement evt;

    private boolean go = false;
    private Timeline littleCycle;

    public Controller controller;

    private static int valeurController = 0;


    /**
     * lancement automatique de l'application graphique
     */
    public void start(Stage primaryStage) {
        decalage = 1.5;
        width = 500;
        height = 500;
        heightStep = height / 30;
        widthStep = width / 30;
        evt = new Environnement(30, 30, this);
        construirePlateauJeu(primaryStage);
        construireFXML(primaryStage);
    }

    /**
     * construction du theatre et de la scene
     */
    private void construirePlateauJeu(Stage primaryStage) {
        // definir la troupe des acteurs et des decors
        troupe = new Group();
        // definir la scene principale
        Scene scene = new Scene(troupe, 2 * widthStep + width, 2 * heightStep + height, Color.ANTIQUEWHITE);
        scene.setFill(Color.BLANCHEDALMOND);
        // definir le decor
        dessinEnvironnement();
        // ajouter les acteurs
        ajoutPions();
        evt.switchMaladeRandom();
        long tempo = 400;
        int[] step = {1};
        littleCycle = new Timeline(new KeyFrame(Duration.millis(tempo), event -> {
            if (step[0] > 4) {
                evt.sactiver();
                step[0] = 0;
            } else evt.avancerTemps();
            step[0]++;
        }));
        littleCycle.setCycleCount(Timeline.INDEFINITE);

        primaryStage.setTitle("Simulation propagation de virus");
        primaryStage.setScene(scene);
        // afficher le theatre
        primaryStage.show();
        agirSelonTouche(scene);
    }


    private void construireFXML(Stage primaryStage) {
        String sceneFile = "src/main/java/FXML/Dialogue.fxml";
        FXMLLoader fxmlLoader = null;
        AnchorPane page = null;
        try {
            URL url = new File(sceneFile).toURI().toURL();
            fxmlLoader = new FXMLLoader(url);
            page = fxmlLoader.load();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (page != null) {
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Controller");
            dialogStage.initModality(Modality.NONE);
            dialogStage.initOwner(primaryStage);
            Scene miniScene = new Scene(page);
            dialogStage.setScene(miniScene);
            this.controller = fxmlLoader.getController();
            this.controller.setSimuVirus(this);
            dialogStage.showAndWait();

        }
    }


    // agirSelonTouche : à adapter depuis jeu de la vie
    private void agirSelonTouche(Scene scene) {
        scene.setOnKeyTyped(e -> {
            switch (e.getCharacter()) {
                // case "b" -> evt.bouger();
                case "t" -> evt.avancerTemps();
                case "T" -> evt.avancerTemps();
                case "b" -> evt.bouger(this.getWidthStep(), this.getHeightStep(), this.getDecalage());
                case "B" -> evt.bouger(this.getWidthStep(), this.getHeightStep(), this.getDecalage());
                case "m" -> evt.switchMaladeRandom();
                case "M" -> evt.switchMaladeRandom();
                case "a" -> evt.sactiver();
                case "A" -> evt.sactiver();
                case "g" -> go();
                case "G" -> go();
            }

        });
    }


    public void lancer() {
        go = true;
        littleCycle.play();
    }

    public void quitter() {
        go = false;
        littleCycle.pause();
    }


    private void go() {
        go = !go;
        if (go) littleCycle.play();
        else littleCycle.pause();
    }

    /**
     * dessin de la zone de jeu
     */
    private void dessinEnvironnement() {
        //Les lignes
        for (int i = 0; i <= 29; i++) {
            Line line1 = new Line(decalage * widthStep, (i + decalage) * heightStep, (29 + decalage) * widthStep, (i + decalage) * heightStep);
            Line line2 = new Line((i + decalage) * widthStep, decalage * heightStep, (i + decalage) * widthStep, (29 + decalage) * heightStep);
            line1.setStrokeWidth(6);
            line1.setStroke(Color.GRAY);
            line1.setOpacity(0.9);
            line2.setStrokeWidth(6);
            line2.setStroke(Color.GRAY);
            line2.setOpacity(0.9);
            troupe.getChildren().add(line1);
            troupe.getChildren().add(line2);
        }
        //les places
        ajouterPlaces();
    }

    /**
     * ajouter les places cliquables (croisements)
     */
    private void ajouterPlaces() {
        int nbEntreprise = (int) ((2 * 15) / 3);
        int nbMagasin = (int) (15 / 3);
        int choix;
        Random hasard = new Random();
        for (int i = 0; i <= 29; i++) {
            double x = (i + decalage) * widthStep;
            for (int j = 0; j < 15; j++) {
                choix = hasard.nextInt(2);

                if (nbMagasin <= 0 && nbEntreprise > 0) {
                    choix = 1;
                }
                if (nbEntreprise <= 0 && nbMagasin > 0) {
                    choix = 0;
                }

                double y = (j + decalage) * heightStep;
                if (choix == 1) {
                    Lieu l = new Lieu(new Point(i, j), TypeLieu.Entreprise);
                    Place c = new Place(x, y, heightStep / 5, l);
                    evt.addLieu(l);
                    evt.putLieuAnnuaire(l);
                    c.setLieu(l);
                    c.setFill(Color.MIDNIGHTBLUE);
                    troupe.getChildren().add(c);
                    nbEntreprise--;
                }
                if (choix == 0) {
                    Lieu l = new Lieu(new Point(i, j), TypeLieu.Magasin);
                    Place c = new Place(x, y, heightStep / 5, l);
                    evt.addLieu(l);
                    evt.putLieuAnnuaire(l);
                    c.setFill(Color.LIGHTCORAL);
                    troupe.getChildren().add(c);
                    nbMagasin--;
                }
            }
            for (int j = 15; j <= 29; j++) {
                double y = (j + decalage) * heightStep;
                Lieu l = new Lieu(new Point(i, j), TypeLieu.Domicile);
                Place c = new Place(x, y, heightStep / 5, l);
                evt.addLieu(l);
                evt.putLieuAnnuaire(l);
                c.setFill(Color.MINTCREAM);
                troupe.getChildren().add(c);
            }
        }
    }


    /**
     * ajouter les pions des joueurs
     */
    private void ajoutPions() {
        for (int i = 0; i <= 29; i++) {
            double x = (i + decalage) * widthStep;
            for (int j = 15; j <= 29; j++) {
                double y = (j + decalage) * heightStep;
                PersonneImg p = new PersonneImg(x, y, heightStep / 3, Color.WHITE);
                Personne pers = new Personne(i, j, p, evt);
                evt.getListePersonnesDansEnvt().add(pers);
                p.setOnMouseClicked(this);
                troupe.getChildren().add(p);

                PersonneImg p2 = new PersonneImg(x, y, heightStep / 3, Color.WHITE);
                Personne pers2 = new Personne(i, j, p2, evt);
                evt.getListePersonnesDansEnvt().add(pers2);
                p2.setOnMouseClicked(this);
                troupe.getChildren().add(p2);
            }
        }
    }


    /**
     * gestion de la souris : selection de jeton, de place
     */


    public void updatePos(Personne p) {
        Timeline timeline = new Timeline();
        double endX = (p.getPoint().getX() + decalage) * widthStep;
        double endY = (p.getPoint().getY() + decalage) * heightStep;
        KeyFrame bougePersone = new KeyFrame(new Duration(300), new KeyValue(p.getPersImg().centerXProperty(), endX), new KeyValue(p.getPersImg().centerYProperty(), endY));
        timeline.getKeyFrames().add(bougePersone);
        timeline.play();
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        //si le clic est sur un jeton, alterne son etat selectionne ou non
        if (mouseEvent.getSource().getClass() == PersonneImg.class) {
            PersonneImg p = (PersonneImg) mouseEvent.getSource();
            p.select();
            personneSelectionnee = (p.isSelected() ? p : null);
        }
        //si le clic est sur un croisement, si un jeton a ete selectionne, il s'y deplace
        else if (mouseEvent.getSource().getClass() == Place.class) {
            Place p = (Place) mouseEvent.getSource();
            if (personneSelectionnee != null && personneSelectionnee.isSelected()) {
                int startX = (int) Math.round(personneSelectionnee.getCenterX() / widthStep - decalage);
                int startY = (int) Math.round(personneSelectionnee.getCenterY() / heightStep - decalage);
                int endX = (int) Math.round(p.getCenterX() / widthStep - decalage);
                int endY = (int) Math.round(p.getCenterY() / heightStep - decalage);
                System.err.println("startX, startY, endX, endY=" + startX + "," + startY + "," + endX + "," + endY);
                animPionVers(p);
                personneSelectionnee.select();
            }
        }
    }


    /**
     * lancement d'une animation de deplacement du jeton selectionne vers la place
     *
     * @param p la place destination du jeton selectionne
     */
    private void animPionVers(Place p) {
        Timeline timeline = new Timeline();
        double xdest = p.getCenterX();
        double ydest = p.getCenterY();
        KeyFrame bougeVoiture = new KeyFrame(new Duration(500), new KeyValue(personneSelectionnee.centerXProperty(), xdest), new KeyValue(personneSelectionnee.centerYProperty(), ydest));
        timeline.getKeyFrames().add(bougeVoiture);
        timeline.play();

    }

    public double getDecalage() {
        return decalage;
    }

    public void setDecalage(int decalage) {
        this.decalage = decalage;
    }

    public double getWidthStep() {
        return widthStep;
    }

    public void setWidthStep(double widthStep) {
        this.widthStep = widthStep;
    }

    public double getHeightStep() {
        return heightStep;
    }

    public void setHeightStep(double heightStep) {
        this.heightStep = heightStep;
    }

    public PersonneImg getPersonneSelectionnee() {
        return personneSelectionnee;
    }

    public void setPersonneSelectionnee(PersonneImg personneSelectionnee) {
        this.personneSelectionnee = personneSelectionnee;
    }

    public Group getTroupe() {
        return troupe;
    }

    public void setTroupe(Group troupe) {
        this.troupe = troupe;
    }

    public Environnement getEvt() {
        return evt;
    }

    public void setEvt(Environnement evt) {
        this.evt = evt;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setDecalage(double decalage) {
        this.decalage = decalage;
    }

    public boolean isGo() {
        return go;
    }

    public void setGo(boolean go) {
        this.go = go;
    }

    public Timeline getLittleCycle() {
        return littleCycle;
    }

    public void setLittleCycle(Timeline littleCycle) {
        this.littleCycle = littleCycle;
    }


    /**
     * lancement de la fenetre
     */
    public static void main(String[] args) {
        launch(args);
    }
}
