package mindows.ui.tables;

import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import mindustry.ui.Styles;

public class ConsoleTable extends Table {
    public ConsoleTable(Seq<String> messages){
        super(Styles.black5);
        top().left();
        update(messages);
        visible = true;
    }

    public void update(Seq<String> messages){
        parent = null;
        clearChildren();

        if(messages == null) return;
        for(String message : messages){
            labelWrap("[gray]>[] " + message).top().left();
            row();
        }
    }
}
