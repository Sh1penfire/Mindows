package mindows.ui.windows;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import arc.util.Strings;
import arc.util.Time;
import mindows.ui.elements.StackSlider;
import mindows.ui.tables.WindowTable;
import mindustry.Vars;
import mindustry.content.StatusEffects;
import mindustry.game.Team;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;
import mindustry.ui.Styles;

public class CheatWindow extends WindowTable {
    public int statusDuration = 5;
    public boolean permanent = false;

    public CheatWindow(){
        super("Cheat", Icon.admin, t -> {});
        setMaxWindowWidth(550f);
    }

    @Override
    public void build() {
        top();
        topBar();

        table(tab -> {
            tab.pane(Styles.defaultPane, p -> {
                p.setBackground(Styles.black5);

                // Time control
                p.table(Styles.black5, t -> {
                    t.label(() -> "Time Scale").left().padBottom(5f);
                    t.row();
                    t.table(tt -> {
                        StackSlider slider = new StackSlider(0.1f, 4f, 0.1f, 1f, this::timeScale);
                        tt.button(Icon.rotate, () -> {
                            slider.setValue(1);
                            timeScale(1);
                        }).tooltip("reset").left();
                        tt.add(slider).left().growX().padLeft(10f);
                    });
                }).top().left().growX().height(70f).pad(5f);
                p.row();

                // Team menu
                p.table(Styles.black5, t -> {
                    t.top().left();
                    t.label(() -> "Teams").left();
                    t.row();
                    t.pane(Styles.horizontalPane, tt -> {
                        for(Team team : Team.baseTeams){
                            TextureRegion icon = Core.atlas.find("team-" + team.name);
                            tt.button(b -> {
                                b.image(icon.found() ? icon : Icon.units.getRegion()).color(team.color);
                            }, () -> {
                                Vars.player.team(team);
                            }).size(80f).top().left().tooltip("Change to " + team.name);
                        }
                    }).top().left().scrollX(true).scrollY(false);
                }).top().left().growX().height(160f).pad(5f);
                p.row();

                // Effects menu
                p.table(Styles.black5, t -> {
                    t.top().left();
                    t.label(() -> "Statuses").left();
                    t.row();

                    // duration field
                    t.table(Tex.button, tt -> {
                        tt.label(() -> "Duration").left().padLeft(10f);
                        tt.field(String.valueOf(statusDuration), result -> {
                            statusDuration = Strings.parseInt(result);
                        }).left().padLeft(5f).disabled(x -> permanent);
                        tt.label(() -> "s").left().color(Pal.darkishGray).padRight(10f);
                    }).height(80f).growX();
                    t.row();

                    // permanent and clear buttons
                    t.table(tt -> {
                        tt.button(b -> {
                                b.image(() -> !permanent ? Icon.cancelSmall.getRegion() : Icon.okSmall.getRegion()).left();
                                b.label(() -> "Permanent").left().padLeft(5f);
                            }, () -> permanent = !permanent)
                            .growX().pad(5f);

                        tt.button("Clear Effects", () -> Vars.player.unit().clearStatuses())
                            .growX().pad(5f);
                    }).growX();
                    t.row();

                    // effect selection
                    t.pane(Styles.defaultPane, tt -> {
                        int r = 0;
                        for(StatusEffect status : Vars.content.statusEffects()){
                            if(status == StatusEffects.none) continue;

                            tt.button(b -> b.image(status.fullIcon).color(status.color), () -> {
                                Vars.player.unit().apply(status, permanent ? Integer.MAX_VALUE : statusDuration * 60);
                            }).size(60f).tooltip(status.localizedName);

                            r++;
                            if(r % 8 == 0){
                                tt.row();
                            }
                        };
                    }).grow();

                }).top().left().growX().height(370f).pad(5f);
            }).top().left().grow();
        }).top().left().grow();

        resizeButton();
    }

    public void timeScale(float scale){
        Time.setDeltaProvider(() -> Core.graphics.getDeltaTime() * 60f * scale);
    }
}
