package org.app.Gui;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import org.app.simulation.meneger.Meneger;
import javafx.scene.paint.Color;
import org.app.simulation.meneger.config.Config;

/**
 * Odpowiada za obsługę interakcji użytkownika,
 * aktualizację ustawień i wywołanie odpowiednich akcji w aplikacji.
 */
public class GuiController {
    @FXML
    Pane world;

    Config settings;
    Meneger sim;

    Boolean isRunning = false;
    private TimeKeeper clock;

    XYChart.Series<String, Integer> populacjaxy = new XYChart.Series<>();
    XYChart.Series<String, Integer> pheromonyxy = new XYChart.Series<>();

    @FXML
    Slider AntSizeSlide = new Slider(0, 10, 5);

    @FXML
    Slider AntLifeTimeSlide = new Slider(0, 10000, 1500);

    @FXML
    Slider AntRangeSlide = new Slider(0, 100, 10);

    @FXML
    Slider AntStepLengthSlide = new Slider(0, 10, 1);

    @FXML
    Slider AntTurnAngleMaxSlide = new Slider(0, 1, 0.01);

    @FXML
    Slider AnthillCircleRadiusSlide = new Slider(0, 10, 10);

    @FXML
    Slider PheromoneCircleRadiusSlide = new Slider(0, 10, 2);

    /// tu tu
    @FXML
    Slider AntsEvaporationTimeSlide = new Slider(0, 5000, 1000);

    @FXML
    Slider AntPheromoneIntervalSlide = new Slider(0, 100, 20);

    @FXML
    Slider AntHungerLimitSlide = new Slider(0, 100, 100);

    @FXML
    Slider AntConsumptionSlide = new Slider(0, 1, 0.5);

    @FXML
    Slider AntFoodCapacitySlide = new Slider(0, 100, 100);

    @FXML
    Slider AntTurnAngleChangeSlide = new Slider(0, 1, 0.001);

    @FXML
    Slider AnthillAntLimitSlide = new Slider(0, 100, 100);

    @FXML
    Slider PheromoneEvaporationTimeSlide = new Slider(0, 2000, 1000);

    @FXML
    TextField ticktext;
    @FXML
    private AreaChart<String, Integer> populacjaant;
    @FXML
    private AreaChart<String, Integer> iloscpheromonow;

    @FXML
    CheckBox doPheromonesEvaporate = new CheckBox();

    @FXML
    CheckBox isPheromoneVisable = new CheckBox();

    @FXML
    CheckBox doAntsEvaporate = new CheckBox();

    @FXML
    CheckBox isAntsVisable = new CheckBox();

    @FXML
    CheckBox AntGetHungry = new CheckBox();

    @FXML
    CheckBox AntDiesOfAge = new CheckBox();

    /**
     * Odpowiedzialna za aktualizację ustawień symulacji na podstawie wartości pobranych z suwaków lub innych elementów interfejsu użytkownika.
     */
    @FXML
    void UpdateSettings() {
        settings.setDisplayAntSize(AntSizeSlide.getValue());
        settings.setDisplayAnthillSize(AnthillCircleRadiusSlide.getValue());
        settings.setDisplayPheromoneSize(PheromoneCircleRadiusSlide.getValue());

        settings.setAntLifetime((int) AntLifeTimeSlide.getValue());
        settings.setAntPheromoneSenseRange((int) AntRangeSlide.getValue());
        settings.setAntStepLength((int) AntStepLengthSlide.getValue());
        settings.setAntTurnAngleMax(AntTurnAngleMaxSlide.getValue());
        settings.setAntLeavePheromoneInterval((int) AntPheromoneIntervalSlide.getValue());
        settings.setAntMaximumHungerLevel((int) AntHungerLimitSlide.getValue());
        settings.setAntConsumption(AntConsumptionSlide.getValue());
        settings.setAntFoodCapacity((int) AntFoodCapacitySlide.getValue());
        settings.setAntTurnAngleChange(AntTurnAngleChangeSlide.getValue());
        settings.setAnthillAntsLimit((int) AnthillAntLimitSlide.getValue());
        settings.setPheromoneEvaporationTime((int) PheromoneEvaporationTimeSlide.getValue());


        sim.updatealldisplayConfigs();
    }

    /**
     * Inicjalizuje kontroler GUI,
     * ustawia tło świata,
     * tworzy obiekty konfiguracji i menedżera symulacji oraz dodaje mrowisko.
     * Dodaje również początkową ilość jedzenia i inicjalizuje wykresy.
     */
    @FXML
    public void initialize() {
        clock = new TimeKeeper();
        world.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        settings = new Config(world);
        sim = new Meneger(settings, world);
        settings.setMap(sim.getMap());
        sim.addAnthill();
        sim.PrzeprowadzTickSymulacji();

        sim.AddRandomFood();

        if (populacjaant.getData().size() == 0 && iloscpheromonow.getData().size() == 0) {
            populacjaant.getData().add(populacjaxy);
            iloscpheromonow.getData().add(pheromonyxy);
        }
    }

    /**
     * Wykonuje pojedynczy krok symulacji.
     */
    @FXML
    public void step() {
        sim.PrzeprowadzTickSymulacji();
        if (sim.getTick() % 100 == 0) {
            populacjaxy.getData().add(new XYChart.Data<>(Integer.toString(sim.getTick()), sim.getAmountofants()));
            pheromonyxy.getData().add(new XYChart.Data<>(Integer.toString(sim.getTick()), sim.getAmountofphermoones()));
        }
        ticktext.setText(Integer.toString(sim.getTick()));
    }

    /**
     * Uruchamia animację symulacji.
     */

    @FXML
    public void start() {
        clock.start();
    }

    /**
     * Zatrzymuje animację symulacji.
     */
    @FXML
    public void stop() {
        clock.stop();
    }

    /**
     * Resetuje symulację,
     * zatrzymuje animację,
     * zabija wszystkie mrówki i czyści wykresy.
     */
    @FXML
    public void reset() {
        clock.stop();
        sim.killeveryone();
        clearGraphs();
        initialize();
    }

    /**
     * Czyści dane wykresów.
     */
    public void clearGraphs() {
        populacjaxy.getData().clear();
        pheromonyxy.getData().clear();
    }


    private class TimeKeeper extends AnimationTimer {
        private long last = 0;
        long FRAMES_PER_SEC = 10L;
        long INTERVAL = 1000000L / FRAMES_PER_SEC;

        @Override
        public void handle(long now) {
            if (now - last > INTERVAL) {
                step();
                last = now;
            }
        }

    }
}