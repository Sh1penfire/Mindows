package mindows.ui.tables;

import arc.func.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import mindustry.gen.*;
import mindustry.ui.*;

public class SearchTable extends Table{
    public TextField search;
    public Table all;
    public Cons2<Table, String> rebuild;

    public SearchTable(String messageText, Cons2<Table, String> rebuild){
        super(Styles.black5);

        search = new TextField();
        search.setMessageText(messageText);
        search.changed(this::rebuild);
        this.rebuild = rebuild;

        //search field
        table(Tex.button, s -> s.add(search).growX().pad(5)).growX().pad(10).top().left();
        row();
        table(Styles.black5, a -> {
            a.top().left();
            a.pane(Styles.defaultPane, pane -> {
                pane.top().left();
                all = pane;
                rebuild();
            }).grow().scrollX(true);
        }).grow();
    }

    public SearchTable(Cons2<Table, String> rebuild){
        this("@players.search", rebuild);
    }

    void rebuild(){
        all.parent = null; // if I don't do this, the parent table gets reset too. For some fucking reason. Anuke whyyyyyyyyyyyyyyyyyyyyyyyy.
        all.clearChildren();
        rebuild.get(all, search.getText());
    }
}