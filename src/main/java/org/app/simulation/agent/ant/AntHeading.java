package org.app.simulation.agent.ant;

import org.app.simulation.agent.Agent;
import org.app.simulation.agent.pheromone.Pheromone;
import org.app.simulation.agent.pheromone.PheromoneType;
import org.app.simulation.meneger.config.Config;

import java.util.Comparator;
import java.util.Vector;

public class AntHeading {

    Config settings;
    double turnangle = 0.001 * Math.PI;
    double currentangle;
    double pheromoneangle = 0;
    double pheromonehomeangle = 0;
    Ant owner;
    Vector<Pheromone> arleadyusedpheromones = new Vector<>();

    AntHeading(Config settings, Ant owner) {
        this.settings = settings;
        this.owner = owner;
        currentangle = Math.random() * (Math.PI * 2);
    }

    public double getHeadingAngle() {
        return currentangle;
    }

    public void setHeadingAngle(double angle) {
        currentangle = angle;
    }

    public void clear_known_pheromones() {
        arleadyusedpheromones.clear();
    }

    public void update() {
        changecurrentaanglefrompheromones();
        if (owner.getDirection() == Antdirection.FOOD) {
            turnangle = turnangle + pheromoneangle * settings.getAntProbabilityOfTakingPheromonesIntoAccount();
        }
        if (owner.getDirection() == Antdirection.HOME) {
            turnangle = turnangle + pheromonehomeangle * settings.getAntProbabilityOfTakingPheromonesIntoAccount();
        }


        currentangle += turnangle;

        turnangle += (Math.random() - 0.5) * settings.getAntTurnAngleChange();

        //reduce turn angle to angle turn max

        if (turnangle > settings.getAntTurnAngleMax()) {
            turnangle = settings.getAntTurnAngleMax();
        }
        if (turnangle < -settings.getAntTurnAngleMax()) {
            turnangle = -settings.getAntTurnAngleMax();
        }
    }

    void removePheromonesNotInFov(Vector<Pheromone> pheromones) {
        pheromones.removeIf(p -> {
            double angle = Math.atan2(p.getLocy() - owner.getLocy(), p.getLocx() - owner.getLocx());
            return Math.abs(angle - currentangle) > settings.getAntFov();
        });
    }

    double getmeanAnglefromPheromones(Vector<Pheromone> pheromones) {

        double meanAngle = 0;

        for (Pheromone p : pheromones) {
            meanAngle += Math.atan2(p.getLocy() - owner.getLocy(), p.getLocx() - owner.getLocx());
        }

        return meanAngle / pheromones.size();
    }

    //function that calculate what needs to be added to current angle to get to the target
    double calculateneededanglechange(Agent target) {
        double angle = Math.atan2(target.getLocy() - owner.getLocy(), target.getLocx() - owner.getLocx());
        double neededanglechange = angle - currentangle;

        if (neededanglechange > Math.PI) {
            neededanglechange -= Math.PI * 2;
        }
        if (neededanglechange < -Math.PI) {
            neededanglechange += Math.PI * 2;
        }

        return neededanglechange;
    }

    double calculateneededanglechangetoangle(double angle) {
        double neededanglechange = angle - currentangle;

        if (neededanglechange > Math.PI) {
            neededanglechange -= Math.PI * 2;
        }
        if (neededanglechange < -Math.PI) {
            neededanglechange += Math.PI * 2;
        }

        return neededanglechange;
    }

    void changecurrentaanglefrompheromones() {

        Vector<Pheromone> pheromones = settings.getMap().getSurroundingPheromones(owner, settings.getAntPheromoneSenseRange());

        //exit if there are no pheromones to process
        if (pheromones.size() == 0) {
            return;
        }

        switch (owner.getDirection()) {
            case FOOD -> {
                pheromones.removeIf(p -> p.getType() != PheromoneType.FOOD);


                if (pheromones.size() == 0) {
                    return;
                }

                //sorting pheromones by age
                pheromones.sort(Comparator.comparingInt(Pheromone::getCreationTick));
                pheromones.sort(Comparator.comparingInt(Pheromone::getGottotarget));

                if (pheromones.size() == 0) {
                    return;
                }


                pheromoneangle = calculateneededanglechange(pheromones.firstElement());
            }
            case HOME -> {
                pheromones.removeIf(p -> p.getType() != PheromoneType.HOME);


                if (pheromones.size() == 0) {
                    return;
                }

                //sorting pheromones by age
                pheromones.sort(Comparator.comparingInt(Pheromone::getCreationTick));
                pheromones.sort(Comparator.comparingInt(Pheromone::getGottotarget));

                if (pheromones.size() == 0) {
                    return;
                }


                pheromonehomeangle = calculateneededanglechange(pheromones.firstElement());
            }

            default -> {
            }
        }
    }


    double calulatemediananglefrompheromones(Vector<Pheromone> pheromones) {
        Vector<Double> angles = new Vector<>();

        for (Pheromone pheromone : pheromones) {
            angles.add(Math.atan2(pheromone.getLocy() - owner.getLocy(), pheromone.getLocx() - owner.getLocx()));
        }

        angles.sort(Comparator.comparingDouble(Double::doubleValue));


        return angles.get(angles.size() / 2);
    }

    double findAngleToAgent(Agent agent) {
        return owner.countAngleBeetwenPoints(agent.getLocx(), agent.getLocy(), owner.getLocx(), owner.getLocy());
    }

    void reverseDirection() {
        currentangle += Math.PI;
    }

    void bouncexwall() {
        currentangle = Math.PI - currentangle;
    }

    void bounceywall() {
        currentangle = 2 * Math.PI - currentangle;
    }

}
