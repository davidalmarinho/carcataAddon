package com.davidalmarinho.java2dgame.game_objects.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hex extends Component {
    public final int ID;
    private Sprite nonSelectedSpr;
    private Sprite selectedSpr;
    public boolean selected;

    public Hex(int id, Sprite nonSelectedSpr, Sprite selectedSpr, boolean selected) {
        this.ID = id;
        this.nonSelectedSpr = nonSelectedSpr;
        this.selectedSpr = selectedSpr;
        this.selected = selected;
    }

    public Hex(int id, Sprite nonSelectedSpr, Sprite selectedSpr) {
        this(id, nonSelectedSpr, selectedSpr, false);
    }

    @Override
    public Component copy() {
        return new Hex(ID, this.nonSelectedSpr, this.selectedSpr, selected);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (selected) {
            drawQuick(batch, selectedSpr);
        } else {
            drawQuick(batch, nonSelectedSpr);
        }
    }
}
