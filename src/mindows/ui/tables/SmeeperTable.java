package mindows.ui.tables;

import arc.scene.ui.layout.Table;
import mindows.winesmeeper.WineSmeeper;
import mindows.winesmeeper.WineTile;
import mindustry.ui.Styles;

public class SmeeperTable extends Table {
    public WineSmeeper smeeper;
    public int w, h, wines;
    public SmeeperTable(WineSmeeper smeeper){
        super(Styles.black5);

        this.smeeper = smeeper;
        restart(10, 10, 6);
    }

    public void build(){
        parent = null;

        clearChildren();
        int j = 0;
        for(int i = 0; i < smeeper.grid.length; i++){
            WineTile tile = smeeper.grid[i];
            button(b -> b.label(tile::getText), () -> {
                tile.reveal();
                build();
            }).top().left().size(40f).disabled(!tile.covered);
            j++;
            if(j == smeeper.w){
                row();
                j = 0;
            }
        }
    }

    public void restart(){
        restart(w, h, wines);
    }

    public void restart(int w, int h, int wines){
        this.w = w;
        this.h = h;
        this.wines = wines;
        smeeper.setup(w, h, wines);
        build();
    }
}
