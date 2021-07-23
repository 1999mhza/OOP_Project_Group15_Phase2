package model.animal;

import javafx.scene.image.ImageView;

public class AnimalImageView extends ImageView {
    private Animal animal;

    public AnimalImageView(Animal animal) {
        this.animal = animal;
    }

    public Animal getAnimal() {
        return animal;
    }
}
