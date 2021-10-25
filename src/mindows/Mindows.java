package mindows;

import arc.*;
import mindows.ui.MindowsTex;
import mindows.ui.WindowFragment;
import mindustry.Vars;
import mindustry.game.EventType.*;
import mindustry.mod.*;

public class Mindows extends Mod{
    public Mindows(){
        Events.on(ClientLoadEvent.class, h -> {
            MindowsTex.init();
            new WindowFragment().build(Vars.ui.hudGroup);
        });
    }

    @Override
    public void loadContent(){
    }
}