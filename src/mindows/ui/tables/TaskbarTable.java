package mindows.ui.tables;

import arc.scene.ui.layout.Table;
import mindows.ui.MindowsTex;
import mindustry.ui.Styles;

public class TaskbarTable extends Table {
    public TaskbarTable(WindowTable... items){
        visible = true;
        table(MindowsTex.sidebar,t -> {
            t.top().center();
            for(WindowTable w : items){
                t.button(w.icon, Styles.emptyi, () -> {
                    w.visible(() -> true);
                }).disabled(b -> w.visible)
                    .size(40f)
                    .padLeft(5f);
                t.row();
            }
        }).right().center().width(40f);
    }
}
