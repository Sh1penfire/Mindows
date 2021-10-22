package mindows.ui.windows;

import arc.scene.ui.TextField;
import arc.scene.ui.layout.Table;
import mindows.ui.WindowTable;
import mindustry.Vars;
import mindustry.game.Schematic;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.ui.Styles;

public class SchematicWindow extends WindowTable {
    public TextField searchField;
    public String search = "";

    public SchematicWindow(){
        super("schematics", Icon.copy, t -> {});
    }

    @Override
    public void build() {
        top();
        topBar();

        // window contents
        table(Styles.black5, w -> {
            // search field
            w.table(Tex.button, t -> {
                searchField = t.field(search, res -> {
                    search = res;
                }).growX().pad(5f).get();
            }).growX().pad(10f).top().left();
            w.row();

            w.table(Styles.black5, tb -> {
                tb.top().left();
                tb.pane(Styles.defaultPane, pane -> {
                    // wh
                }).grow().scrollX(true);
            }).grow();
        }).grow();

        resizeButton();
    }

}
