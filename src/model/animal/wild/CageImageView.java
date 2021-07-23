package model.animal.wild;

import javafx.scene.image.ImageView;

public class CageImageView extends ImageView {
    private Wild wild;

    public CageImageView(Wild wild) {
        this.wild = wild;
    }

    public Wild getWild() {
        return wild;
    }
}
