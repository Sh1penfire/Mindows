package mindows.ui.windows;

import arc.util.*;
import mindows.ui.*;
import mindows.ui.tables.*;
import mindustry.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.dialogs.*;

public class SchematicWindow extends WindowTable {
    public SchematicWindow(){
        super("schematics", Icon.copy, t -> {});
    }

    @Override
    public void build(){
        top();
        topBar();

        SearchTable search = new SearchTable((table, query) -> {
            for(Schematic s : Vars.schematics.all()){
                if(s.name().toLowerCase().replace("\\W", "").contains(query.toLowerCase().replace("\\W", ""))){
                    table.button(t -> {
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
                    table.row();
                }
            }
        });

        add(search).grow();

        resizeButton();
    }
}