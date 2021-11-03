package mindows.scripts;

import arc.files.Fi;
import mindustry.Vars;

import static mindustry.Vars.dataDirectory;

public class ScriptsHandler {
    public static Fi scriptDirectory = dataDirectory.child("scripts/");
    public static Fi[] scripts;
    public static void load() {
        scripts = scriptDirectory.list();
        for (Fi script : scripts) {
            Vars.mods.getScripts().runConsole(script.readString());
        }
    }
}
