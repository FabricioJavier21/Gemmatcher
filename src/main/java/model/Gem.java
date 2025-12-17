package model;

import javafx.scene.image.Image;

public class Gem {
    private GemType type;

    public Gem(GemType type) {
        this.type = type;
    }

    public GemType getType() {
        return type;
    }

    public void setType(GemType type) {
        this.type = type;
    }

    public Image getImage() {
        String path = "/gems/" + type.name().toLowerCase() + ".png";
        return new Image(getClass().getResourceAsStream(path));
    }
}
