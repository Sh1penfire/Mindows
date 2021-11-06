package mindows.ui.windows;

import mindows.ui.tables.SmeeperTable;
import mindows.ui.tables.WindowTable;
import mindows.winesmeeper.WineSmeeper;
import mindustry.gen.Icon;

public class WineSmeeperWindow extends WindowTable {
    public SmeeperTable game;
    public WineSmeeperWindow(){
        super("Winesmeeper", Icon.hammer, t -> {});
    }

    @Override
    public void build() {
        top();
        topBar();

        game = new SmeeperTable(new WineSmeeper(10, 10, 10));
        table(t -> {
            t.table(tt -> {
                t.button("restart", () -> game.restart());
            }).growX();
            t.row();
            t.pane(game).grow();
        }).grow();

        resizeButton();
    }
}
