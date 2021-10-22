package mindows.ui.windows;

import arc.scene.ui.TextField;
import mindows.ui.WindowTable;
import mindustry.Vars;
import mindustry.game.Schematic;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.ui.Styles;

import java.util.regex.Pattern;

public class SchematicWindow extends WindowTable {
    public TextField searchField;
    public String query = " ";
    public final Pattern ignoreSymbols = Pattern.compile("[`~!@#$%^&*()\\-_=+{}|;:'\",<.>/?]");

    public Runnable rebuildPane = () -> {};

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
                searchField = t.field(query, res -> {
                    query = res != null ? res : "";
                    rebuildPane.run();
                }).growX().pad(5f).get();
            }).growX().pad(10f).top().left();
            w.row();
            w.table(Styles.black5, tb -> {
                tb.top().left();
                tb.pane(Styles.defaultPane, t -> {
                    rebuildPane = () -> {
                        t.clearChildren();
                        t.top().left();

                        for(Schematic s : Vars.schematics.all()){
                            // idk what to do here
                        }
                    };

                    rebuildPane.run();
                }).grow().scrollX(true);
            }).grow();
        }).grow();

        resizeButton();
    }
}
