package mindows.ui.tables;

import arc.scene.Element;
import arc.scene.ui.layout.Table;
import mindows.winesmeeper.WineTile;
import mindows.winesmeeper.Winesmeeper;
import mindustry.ui.Styles;

public class SmeeperTable extends Table {
    public int w, h, wines;
    public Winesmeeper game;

    public SmeeperTable(Winesmeeper game){
        super(Styles.black5);

        this.game = game;
        this.game.init(10, 10, 10);
        build();
    }

    public void build(){
        parent = null;
        clearChildren();

        for(WineTile[] row : game.grid){
            for(WineTile tile : row){
                button(b -> b.label(tile::getNumber), () -> {
                    tile.reveal();
                    build();
                }).top().left().size(40f).disabled(!tile.covered);
            }
            row();
        }
    }

    public void restart(){
        restart(10, 10, 10);
    }

    public void restart(int w, int h, int wines){
        this.w = w;
        this.h = h;
        this.wines = wines;
        game.init(w, h, wines);
        build();
    }
}
