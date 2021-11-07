package mindows.ui.windows;

import arc.math.Mathf;
import mindows.ui.tables.SmeeperTable;
import mindows.ui.tables.WindowTable;
import mindows.winesmeeper.Winesmeeper;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.ui.Styles;

public class SmeeperWindow extends WindowTable {
    public SmeeperTable smeeper;
    public int gameW = 10, gameH = 10;

    public SmeeperWindow(){
        super("WineSmeeper", Icon.hammer, t -> {});
    }

    @Override
    public void build() {
        top();
        topBar();

        smeeper = new SmeeperTable(new Winesmeeper());
        table(Styles.black5, t -> {
            t.table(tt -> {
                tt.top().left();

                tt.button(Icon.rotate, () -> {
                    smeeper.restart(gameW, gameH, Mathf.floor(gameW * gameH * 0.25f));
                }).size(40f, 100f).top().left().tooltip("restart");

                tt.table(Tex.button, ttt -> {
                    ttt.label(() -> "Width").top().left().growX();
                    ttt.slider(5, 25, 1, gameW, res -> {
                        gameW = (int) res;
                    }).grow();
                    ttt.labelWrap(() -> String.valueOf(gameW)).size(20f).top().left().get().parent = null;
                    ttt.row();
                    ttt.label(() -> "Height").top().left().growX();
                    ttt.slider(5, 25, 1, gameH, res -> {
                        gameH = (int) res;
                    }).grow();
                    ttt.labelWrap(() -> String.valueOf(gameH)).size(20f).top().left().get().parent = null;
                }).top().left().size(360f, 100f);

                tt.table(ttt -> {
                    ttt.labelWrap(() -> String.valueOf(smeeper.game.getTime())).top().left().get().parent = null;
                });
            }).top().left().growX();
            t.row();
            t.pane(smeeper).grow();
        }).grow();

        resizeButton();
    }
}
