package org.app.menager.config;


import javafx.scene.layout.Pane;
import org.app.map.Map;

public class Config {

    //zmienne "globalne" dla całego projektu <- Wszyscy agenci ich używają
    Pane world;
    org.app.map.Map map;
    int CurrentTick = 0;


    //ustawienia ogólne symulacji
    int OutsideMapTreshold = 10;

    //zmienne mapy
    int MapSizeX = 600;
    int MapSizeY = 600;


    //zmienne mrówek
    int AntSenseRange = 10;
    double AntLifetime = 1500;
    double AntTurnAngle = 0.001;
    double AntTurnAngleMax = 0.01;
    double AntConsumption = 0.5;
    int AntFoodCapacity = 100;
    double AntHungerLimit = 100;
    double AntStepLen = 1;
    double antRange = 10;
    int AntPheromoneInterval = 10;
    boolean AntGetHungry = true;
    boolean AntDiesOfAge = true;

    //ustawienia anthills

    int AnthillAntLimit = 100;
    double AnthillAntSpawnChance = 0.1;


    //ustawienia pheromonów
    boolean doPheromonesEvaporate = true;
    int PheromoneEvaporationTime = 1000;
    boolean isPheromoneVisible = true;

    //ustawienia wyswietlania
    double AntCircleRadius = 5;
    double AntHillCircleRadius = 10;
    double pheromoneCircleRadius = 2;


    public Config(Pane world) {
        this.world = world;
    }

    public int getPheromoneEvaporationTime() {
        return PheromoneEvaporationTime;
    }

    public int getAntPheromoneInterval() {
        return AntPheromoneInterval;
    }

    public double getAntHillCircleRadius() {
        return AntHillCircleRadius;
    }

    public int getOutsideMapTreshold() {
        return OutsideMapTreshold;
    }


    public int getCurrentTick() {
        return CurrentTick;
    }

    public Map getMap() {
        return map;
    }

    public double getPheromoneCircleRadius() {
        return pheromoneCircleRadius;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public boolean isPheromoneVisible() {
        return isPheromoneVisible;
    }

    public Pane getWorld() {
        return world;
    }

    public double getAnthillAntSpawnChance() {
        return AnthillAntSpawnChance;
    }

    public double getAntTurnAngleMax() {
        return AntTurnAngleMax;
    }

    public double getAntTurnAngle() {
        return AntTurnAngle;
    }

    public double getAntCircleRadius() {
        return AntCircleRadius;
    }


    public int getSenseRange() {
        return AntSenseRange;
    }

    public int getMapSizeX() {
        return MapSizeX;
    }

    public int getMapSizeY() {
        return MapSizeY;
    }

    public boolean isDoPheromonesEvaporate() {
        return doPheromonesEvaporate;
    }

    public void setDoPheromonesEvaporate(boolean doPheromonesEvaporate) {
        this.doPheromonesEvaporate = doPheromonesEvaporate;
    }

    public double getAntStepLen() {
        return AntStepLen;
    }

    public void setAntCircleRadius(double antCircleRadius) {
        AntCircleRadius = antCircleRadius;
    }

    public double getAntRange() {
        return antRange;
    }

    public void setAntRange(double antRange) {
        this.antRange = antRange;
    }

    public int getAnthillAntLimit() {
        return AnthillAntLimit;
    }

    public void setAnthillAntLimit(int anthillAntLimit) {
        AnthillAntLimit = anthillAntLimit;
    }

    public void setAntStepLen(double antStepLen) {
        AntStepLen = antStepLen;
    }

    public double getAntLifetime() {
        return AntLifetime;
    }

    public void setAntTurnAngleMax(double antTurnAngleMax) {
        AntTurnAngleMax = antTurnAngleMax;
    }

    public void setAntHillCircleRadius(double antHillCircleRadius) {
        AntHillCircleRadius = antHillCircleRadius;
    }

    public void setPheromoneCircleRadius(double pheromoneCircleRadius) {
        this.pheromoneCircleRadius = pheromoneCircleRadius;
    }

    public void setAntLifetime(double antLifetime) {
        AntLifetime = antLifetime;
    }

    public double getAntHungerLimit() {
        return AntHungerLimit;
    }

    public void setAntHungerLimit(int antHungerLimit) {
        AntHungerLimit = antHungerLimit;
    }

    public double getAntConsumption() {
        return AntConsumption;
    }

    public void setAntConsumption(double antConsumption) {
        AntConsumption = antConsumption;
    }

    public int getAntFoodCapacity() {
        return AntFoodCapacity;
    }

    public void setAntFoodCapacity(int antFoodCapacity) {
        AntFoodCapacity = antFoodCapacity;
    }

    public boolean isAntGetHungry() {
        return AntGetHungry;
    }

    public boolean isAntDiesOfAge() {
        return AntDiesOfAge;
    }

}