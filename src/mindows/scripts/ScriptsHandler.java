package mindows.scripts;

import arc.files.Fi;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Time;
import mindustry.Vars;

import static mindustry.Vars.dataDirectory;

public class ScriptsHandler {
    public static Fi scriptDirectory, setupDirectory;
    public static Fi[] scriptFiles, setupFiles;
    public static Seq<Script> scripts = new Seq<>();

    public static void load() {
        scriptDirectory = dataDirectory.child("scripts/");
        setupDirectory = scriptDirectory.child("load/");

        scriptFiles = scriptDirectory.list(".js");
        setupFiles = setupDirectory.list(".js");

        if(scriptFiles.length == 0){
            Log.infoTag("Mindows", "No utility scripts found.");
        }else {
            Log.infoTag("Mindows", "Found " + scriptFiles.length + " utility scripts");

            for (Fi file : scriptFiles) {
                scripts.add(new Script(file));
            }
        }

        if(setupFiles.length == 0){
            Log.infoTag("Mindows", "No setup scripts found.");
        }else {
            Log.infoTag("Mindows", "Found " + setupFiles.length + " load scripts");
        }
    };

    // run setup scripts
    public static void clientLoad(){
        if(setupFiles.length == 0) return;

        float start = Time.time;

        for(Fi file : setupFiles){
            Log.infoTag("Mindows", "Running " + file.name());
            Vars.mods.getScripts().runConsole(file.readString());
        }

        Log.infoTag("Mindows", "Finished running setup scripts in " + (Time.time - start) + "ms");
    }
}
