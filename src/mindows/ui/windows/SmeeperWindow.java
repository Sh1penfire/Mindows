package mindows.ui.windows;

import arc.math.Mathf;
import arc.scene.ui.Label;
import arc.util.Align;
import mindows.ui.elements.StackSlider;
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
                    ttt.labelWrap("Width").left().growX();
                    ttt.add(new StackSlider(5, 25, 1, gameW, f -> {
                        gameW = (int)f;
                    })).top().left().growX();
                    ttt.row();

                    ttt.labelWrap("Height").left().growX();
                    ttt.add(new StackSlider(5, 25, 1, gameH, f -> {
                        gameH = (int)f;
                    })).top().left().growX();
                }).top().left().size(360f, 100f);

                tt.table(Tex.button, ttt -> {
                    ttt.top().left();
                    ttt.labelWrap("Time").top().left();
                    ttt.row();
                    Label label = ttt.labelWrap(() -> String.valueOf(smeeper.game.getTime()))
                        .top().left()
                        .growX()
                        .fontScale(2.5f)
                        .get();
                    label.parent = null;
                    label.setAlignment(Align.center);
                }).size(180f, 100f).top().left();
            }).top().left().growX();
            t.row();
            t.pane(smeeper).grow();
        }).grow();

        resizeButton();
    }
}
