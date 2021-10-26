package mindows.ui;

import arc.*;
import arc.scene.style.*;

public class MindowsTex{
    public static Drawable sidebar;

    public static void init(){
        sidebar = Core.atlas.drawable("mindows-sidebar");
    }
}