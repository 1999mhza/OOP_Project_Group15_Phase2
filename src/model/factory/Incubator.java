package model.factory;

import model.Game;
import model.animal.domestic.Domestic;
import model.animal.domestic.DomesticList;

public class Incubator extends Factory {
    public Incubator() {
        super(350, 6, "Egg", "Chicken");
    }

    public void update() {
        if (produceRemainingTime > 0) {
            produceRemainingTime--;
            if (produceRemainingTime == 0) {
                DomesticList domesticList = DomesticList.getDomestic(output);
                try {
                    for (int i = 0; i < number; i++) {
                        Domestic domestic = (Domestic) Class.forName(domesticList.getPackageName()).newInstance();
                        Game.getInstance().getDomesticAnimals().add(domestic);
                    }
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
                }
            }
        }
    }
}
