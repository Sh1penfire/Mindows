package mindows.ui.windows;

import arc.scene.Element;
import arc.scene.ui.TextField;
import arc.scene.ui.layout.Table;
import arc.util.Log;
import arc.util.Scaling;
import mindows.ui.WindowTable;
import mindustry.Vars;
import mindustry.game.Schematic;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.SchematicsDialog;

public class SchematicWindow extends WindowTable {
    public TextField searchField;
    public String search = "";

    public Table resultsTable;

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
                    updateResults(search);
                }).growX().pad(5f).get();
            }).growX().pad(10f).top().left();
            w.row();

            // schematic selection table
            w.table(Styles.black5, tb -> {
                tb.top().left();
                tb.pane(Styles.defaultPane, pane -> {
                    resultsTable = pane;
                    updateResults("");
                }).grow().scrollX(true);
            }).grow();

        }).grow();

        resizeButton();
    }

    // FIXME bad hack
    public void updateResults(String query){
        if(resultsTable == null) return;
        resultsTable.parent = null; // if i don't do this, the parent table gets reset too.
        resultsTable.clearChildren();
        resultsTable.top().left();
        for(Schematic s : Vars.schematics.all()){
            if(s.name().toLowerCase().replace("\\W", "").contains(query.toLowerCase().replace("\\W", ""))){
                resultsTable.button(t -> {
                    t.top().left();
                    t.add(new SchematicsDialog.SchematicImage(s).setScaling(Scaling.fit)).size(90f);
                    t.table(i -> {
                        i.top().left();
                        i.labelWrap(s.name()).top().left();
                        i.row();
                        i.labelWrap(s.width + "x" + s.height).color(Pal.darkishGray).top().left();
                    }).pad(5f).top().left();
                }, () -> {
                    Vars.control.input.useSchematic(s);
                }).growX();
                resultsTable.row();
            };
        };
    }

}
