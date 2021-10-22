package mindows.ui;

import arc.scene.Group;
import arc.util.Log;
import mindustry.gen.*;
import mindustry.ui.fragments.Fragment;

import static mindows.ui.WindowTables.*;

@SuppressWarnings("all")
public class WindowFragment extends Fragment {

    @Override
    public void build(Group parent) {

        parent.fill(t -> {
            t.name = "Windows";
            t.visible(() -> parent.visible);

            // windows (totally not a copyright violation)
            t.center().right();
            t.add(schematics).size(250f).visible(false);

            // sidebar
            t.table(s -> {
                s.button(Icon.copy, () -> {
                    schematics.visible(() -> true);
                }).disabled(b -> schematics.visible).size(40f);
            });
        });
    };
}
