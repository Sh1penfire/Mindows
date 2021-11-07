package mindows.ui.windows;

import arc.Events;
import arc.scene.ui.TextField;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Align;
import mindows.scripts.Script;
import mindows.scripts.ScriptsHandler;
import mindows.ui.tables.ConsoleTable;
import mindows.ui.tables.WindowTable;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.ui.Styles;

public class ScriptWindow extends WindowTable {
    public int messagesShown = 60;
    public Seq<String> messages = new Seq<>();
    public ConsoleTable console;
    public Table table;

    public String command = "";

    public ScriptWindow(){
        super("Script Console", Icon.terminal, t -> {});
    }

    {
        Events.on(EventType.WorldLoadEvent.class, h -> {
            messages.clear();
            command = "";
            if(Vars.net.client()){
                addMessage("[accent]SERVER DETECTED[]");
                if(Vars.player.admin){
                    addMessage("[accent]scripts will be run in server console.[]");
                    addMessage("The developers cannot be held responsible for any consequences.");
                }else{
                    addMessage("[scarlet]scripts disabled.[]");
                }
            }
        });
    }

    @Override
    public void build() {
        top();
        topBar();

        table(Styles.black5, tab -> {
            tab.table(t -> {
                t.top().left();

                t.labelWrap("Output").top().left();

                t.row();
                t.table(Tex.button, tt -> {
                    tt.top().left();

                    // TODO horizontal scrolling (scrollX doesn't work fsr)
                    // FIXME pane not updating when console table is updated
                    tt.pane(Styles.defaultPane, p -> {
                        p.add(console).top().left().grow();
                    }).top().left().grow();
                }).top().left().grow();
                t.row();
                t.table(Tex.button, tt -> {
                    tt.button(Icon.eraserSmall, () -> {
                        messages.clear();
                        console.update(messages);
                    }).size(40f).top().left().tooltip("Clear console");

                    tt.button(Icon.terminalSmall, () -> {
                        addMessage(Vars.mods.getScripts().runConsole(command));
                    }).size(40f).top().left().tooltip("Run script");

                    tt.field(command, cmd -> {
                        command = cmd;
                    }).top().left().padLeft(5f).growX();

                }).top().left().growX();
            }).top().left().grow();

            tab.table(t -> {
                t.top().left();

                t.labelWrap("Scripts").top().left();

                t.row();

                // WIP
                t.table(Tex.button, tt -> {
                    tt.top().left();
                    tt.pane(Styles.defaultPane, p -> {
                        p.top().left();
                        for(Script script : ScriptsHandler.scripts){
                            p.button(b -> {
                                b.labelWrap(script.name);
                                b.row();
                                b.labelWrap(script.description).color(Pal.darkishGray);
                            }, () -> {
                                addMessage(script.run());
                            }).growX();
                        }
                    }).top().left().grow();
                }).top().left().grow();
            }).top().left().grow();
        }).top().left().grow();
        // lmao

        resizeButton();
    }

    public void addMessage(String message){
        String[] split = message.split("\\n");
        if(messages.size + split.length > messagesShown){
            messages.removeRange(0, split.length - 1);
        }

        messages.addAll(split);
        console.update(messages);
    }
}
