package mindows.ui.tables;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.geom.Vec2;
import arc.scene.ui.layout.Scl;
import arc.scene.ui.layout.Table;
import arc.struct.Bits;
import arc.util.Align;
import arc.util.Log;
import arc.util.Tmp;
import arc.util.pooling.Pools;
import mindustry.Vars;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.type.StatusEffect;
import mindustry.ui.Fonts;
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
            float h = u.type.fullIcon.height * zoomScale();
            float w = u.type.fullIcon.width * zoomScale();
            float rad = (u.type.fullIcon.width / 2f + 2f) * zoomScale();

            Draw.rect(u.icon(), Tmp.v1.x, Tmp.v1.y, w, h, u.rotation() - 90f);
            Lines.stroke(4f);
            Lines.square(Tmp.v1.x, Tmp.v1.y, rad);
            drawText(u.team.emoji + " " + Math.floor(u.health) + "/" + u.maxHealth, Tmp.v1.x - w/2, Tmp.v1.y + rad + 20f, Color.white);

            Bits applied = u.statusBits();
            StringBuilder effects = new StringBuilder();
            if(applied != null){
                for(StatusEffect effect : Vars.content.statusEffects()){
                    if(applied.get(effect.id)){
                        effects.append(effect.emoji());
                    }
                }
            }
            drawText(effects.toString(), Tmp.v1.x - w/2, Tmp.v1.y - rad, Color.white);
        });

        Draw.color();
        clipEnd();
    }

    public float zoomScale(){
        return Vars.renderer.getScale() / 4f;
    }

    public float drawText(String text, float x, float y, Color color){
        if(Vars.renderer.pixelator.enabled()) return 0;

        Font font = Fonts.def;
        GlyphLayout layout = Pools.obtain(GlyphLayout.class, GlyphLayout::new);
        boolean ints = font.usesIntegerPositions();
        font.setUseIntegerPositions(false);
        font.getData().setScale(1f);
        layout.setText(font, text);

        font.setColor(color);
        font.draw(text, x, y, Align.left);

        font.setUseIntegerPositions(ints);
        font.setColor(Color.white);
        font.getData().setScale(1f);
        Draw.reset();
        Pools.free(layout);

        return width;
    }
}
