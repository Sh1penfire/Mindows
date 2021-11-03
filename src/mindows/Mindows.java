package mindows;

import arc.*;
import mindows.ui.*;
import mindows.scripts.*;
import mindustry.*;
import mindustry.game.EventType.*;
import mindustry.gen.Icon;
import mindustry.mod.*;

public class Mindows extends Mod{
    public Mindows(){
        Events.on(ContentInitEvent.class, h -> {
            MindowsTex.init();
        });

        Events.on(ClientLoadEvent.class, h -> {
            ScriptsHandler.load();
            new WindowFragment().build(Vars.ui.hudGroup);
            if(Vars.mobile){
                Vars.ui.showOkText("WARNING", "Mindows is untested on mobile and may break or show unexpected behavior, please report an issue if you do see one.\nThank you for installing Mindows!", () -> {});
            };
        });
    }

    @Override
    public void loadContent(){
    }
}
