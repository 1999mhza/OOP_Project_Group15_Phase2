package model.good;

import javafx.scene.image.ImageView;

public class GoodImageView extends ImageView {
    private Good good;

    public GoodImageView(Good good) {
        this.good = good;
    }

    public Good getGood() {
        return good;
    }
}
