package mindows.ui.tables;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.geom.Vec2;
import arc.scene.ui.layout.Table;
import arc.util.Log;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.gen.Groups;
import mindustry.ui.Styles;

public class ScanTable extends Table {
    public ScanTable(){
        super();
        setBackground(Styles.black8);
    }

    @Override
    public void draw() {
        super.draw();
        clipBegin();

        Vec2 pos = new Vec2(Core.camera.unproject(x, y));
        Vec2 dim = new Vec2(Core.camera.unproject(x + width, y + height));

        Draw.color(Color.white);
        Groups.unit.intersect(pos.x, pos.y, dim.x - pos.x, dim.y - pos.y, u -> {
            Tmp.v1.set(Core.camera.project(u.x, u.y));

            Draw.rect(u.icon(), Tmp.v1.x, Tmp.v1.y, u.icon().width * zoomScale(), u.icon().height * zoomScale(), u.rotation() - 90f);
            Lines.stroke(3f);
            Lines.square(Tmp.v1.x, Tmp.v1.y, (u.icon().width / 2f + 2f) * zoomScale());
        });

        clipEnd();
    }

    public float zoomScale(){
        return Vars.renderer.getScale() / 4f;
    }
}
