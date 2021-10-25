package mindows.ui;

import arc.Core;
import arc.scene.style.Drawable;

public class MindowsTex{
    public static Drawable sidebar;

    public static void init(){
        sidebar = Core.atlas.drawable("mindows-sidebar");
    }
}