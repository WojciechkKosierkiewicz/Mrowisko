package org.app.menager;


import javafx.fxml.FXML;
import javafx.scene.layout.Pane;


import org.app.agent.anthill.Anthill;
import org.app.agent.food.Food;
import org.app.map.Map;
import org.app.menager.config.Config;
import org.app.menager.simresults.SimResults;

import java.util.List;
import java.util.UUID;
import java.util.Vector;

import org.app.agent.ant.Ant;

public class Menager {

    @FXML
    Pane world;
    private List<Anthill> anthills;
    private List<Food> foods;
    private int Tick = 0;
    private Config settings;

    public Menager(Config settings, Pane world) {
        this.settings = settings;
        this.world = world;
        this.map = new Map(settings.getMapSizeX(),
                settings.getMapSizeY(),
                //ustawia najniższy optymalny sektor
                settings.getMapSizeX() > settings.getMapSizeY() ? settings.getMapSizeX() / settings.getSenseRange() : settings.getMapSizeY() / settings.getSenseRange(),
                settings,
                world);
        this.results = new SimResults();
        this.anthills = new Vector<Anthill>();
        this.foods = new Vector<Food>();
    }

    private SimResults results;
    private org.app.map.Map map;
    private int CurrentTick = 0;

    @FXML
    public void initialize() {
        System.out.println("Menager init");
    }

    public void addAnthill(double posx, double posy) {
        anthills.add(new Anthill(settings, world, map));
        anthills.get(anthills.size() - 1).setPos(posx, posy);

    }

    public void addAnthill() {
        anthills.add(new Anthill(settings, world, map));
        anthills.get(anthills.size() - 1).setrandompos();
    }

    public Vector<UUID> getAnthillIDs() {
        Vector<UUID> anthillIDs = new Vector<UUID>();
        for (Anthill anthill : anthills) {
            anthillIDs.add(anthill.getId());
        }
        return anthillIDs;
    }

    public Map getMap() {
        return map;
    }

    public void addAnts(int amount, UUID anthillID) {
        for (Anthill anthill : anthills) {
            if (anthill.getId() == anthillID) {
                for (int i = 0; i < amount; i++) {
                    anthill.addAnt();
                }
                return;
            }
        }
    }

    public void draw() {
        for (Anthill anthill : anthills) {
            anthill.draw();
        }
    }

    public void PrzeprowadzTickSymulacji() {
        for (Anthill anthill : anthills) {
            anthill.update();
        }
        for (Anthill anthill : anthills) {
            anthill.draw();
        }
        Tick++;
        map.Tick();
        map.removePheromonesolderthan(300);
        removeAntsOutside(2);
    }

    public void RandomAntPosition() {
        for (Anthill anthill : anthills) {
            for (Ant ant : anthill.getAnts()) {
                ant.setRandomPosition();
            }
        }
    }

    public int getTick() {
        return Tick;
    }


    public void killeveryone() {
        anthills.clear();
        map.clearPhermonoes();
    }

    void ZwrocAktulneWyniki() {
        // TODO: 15.05.2023  
    }

    void removeAntsOutside(double treshold) {
        for (Anthill anthill : anthills) {
            anthill.removeAntsOutsideMap(treshold);
        }
    }

    public void printAmounts() {
        System.out.println("==================================");
        System.out.println("Anthills: " + anthills.size());
        System.out.println("Ants: " + getAmountofants());
        System.out.println("Pheromones: " + getAmountofphermoones());
        System.out.println("==================================");
    }

    public int getAmountofants() {
        int amount = 0;
        for (Anthill anthill : anthills) {
            amount += anthill.getAnts().size();
        }
        return amount;
    }

    public int getAmountofphermoones() {
        return map.getamountofpheromones();
    }

    void ZapiszWyniki() {
        // TODO: 15.05.2023   
    }
}