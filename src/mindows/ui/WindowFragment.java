package mindows.ui;

import arc.func.Cons;
import arc.input.KeyCode;
import arc.math.geom.Vec2;
import arc.scene.Group;
import arc.scene.event.*;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.layout.Table;
import arc.util.*;
import mindustry.gen.*;
import mindustry.ui.Styles;
import mindustry.ui.fragments.Fragment;

@SuppressWarnings("all")
public class WindowFragment extends Fragment {
    public Table console, probe;
    @Override
    public void build(Group parent) {
        console = new Table(w -> {
            w.name = "Console Window";
            buildWindow(w, "Console", window -> {
                window.pane(Styles.defaultPane, c -> {
                    c.top().left();
                    c.labelWrap("placeholder").top().left();
                }).grow();
            });
        });
        probe = new Table(w -> {
            w.name = "Block Probe";
            buildWindow(w, "Probe", window -> {
                window.pane(Styles.defaultPane, c -> {
                    c.top().left();
                    c.labelWrap("placeholder").top().left();
                }).grow();
            });
        });

        parent.fill(t -> {
            t.name = "IseeHud";
            t.visible(() -> parent.visible);

            t.add(console).size(250f).visible(() -> false);
            t.add(probe).size(250f).visible(() -> false);
            t.center().right();
            t.table(m -> {
                m.button(Icon.terminal, 40f, () -> {
                    Sounds.windowHide.play();
                    console.visible(() -> true);
                }).disabled(b -> console.visible).size(60f);
                m.row();
                m.button(Icon.eye, 40f, () -> {
                    Sounds.windowHide.play();
                    probe.visible(() -> true);
                }).disabled(b -> probe.visible).size(60f);
            }).center().right();
        });
    }

    // behold, the messy world of UI code
    public void buildWindow(Table parent, String title, Cons<Table> content){
        buildWindow(parent, title, Icon.terminal, Icon.cancel,
            content,
            t -> {
                t.visible(() -> false);
            }
        );
    }

    public void buildWindow(Table parent, String title, TextureRegionDrawable icon, TextureRegionDrawable closeIcon, Cons<Table> content, Cons<Table> onClose) {
        parent.top();
        addDragBar(parent, title, icon, closeIcon, onClose);
        parent.row();
        parent.table(Styles.black5, pt -> {
            pt.top().left();
            content.get(pt); // put window contents in a panel if possible
        }).grow();
        addResizeButton(parent);
    }

    public void addDragBar(Table parent, String title, TextureRegionDrawable icon, TextureRegionDrawable closeIcon, Cons<Table> onClose) {
        parent.table(t -> {
            t.table(Tex.buttonEdge1, b -> {
                b.top().left();
                b.image(icon).size(20f).padLeft(15).top().left();
                b.pane(Styles.nonePane, p -> {
                    p.top().left();
                    p.labelWrap(title).padLeft(20).top().left().get().setAlignment(Align.topLeft);
                }).top().left().height(40f).growX().get().setScrollingDisabled(true, true);
            }).maxHeight(40f).grow();
            t.table(Tex.buttonEdge3, b -> {
                b.button(closeIcon, Styles.emptyi, () -> onClose.get(parent));
            }).maxHeight(40f).width(80f).growY();

            t.touchable = Touchable.enabled;
            t.addListener(new InputListener(){
                float lastX, lastY;
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, KeyCode button) {
                    Vec2 v = t.localToStageCoordinates(Tmp.v1.set(x, y));
                    lastX = v.x;
                    lastY = v.y;
                    t.toFront();
                    return true;
                }

                @Override
                public void touchDragged(InputEvent event, float x, float y, int pointer) {
                    Vec2 v = t.localToStageCoordinates(Tmp.v1.set(x, y));

                    parent.moveBy(v.x - lastX, v.y - lastY);
                    lastX = v.x;
                    lastY = v.y;
                }
            });
        }).top().height(40f).growX();
    }

    public void addResizeButton(Table parent) {
        parent.row();
        parent.table(Styles.black5, t -> {
            t.table().growX();
            t.table(Icon.resizeSmall, r -> {
                r.bottom().left();
                r.touchable = Touchable.enabled;
                r.addListener(new InputListener(){
                    float lastX, lastY;
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, KeyCode button) {
                        Vec2 v = r.localToStageCoordinates(Tmp.v1.set(x, y));
                        lastX = v.x;
                        lastY = v.y;
                        return true;
                    }

                    @Override
                    public void touchDragged(InputEvent event, float x, float y, int pointer) {
                        Vec2 v = r.localToStageCoordinates(Tmp.v1.set(x, y));
                        float w = v.x - lastX;
                        float h = v.y - lastY;
                        if(parent.getWidth() + w < 160f) w = 0;
                        if(parent.getHeight() - h  < 160f) h = 0;
                        parent.sizeBy(w, -h);
                        parent.moveBy(0, h);
                        lastX = v.x;
                        lastY = v.y;
                    }
                });
            }).size(20f).left();
        }).height(20f).growX();
    }
}
