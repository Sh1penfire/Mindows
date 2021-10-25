package mindows.ui.windows;

import arc.graphics.*;
import arc.struct.*;
import arc.util.*;
import mindows.ui.tables.*;
import mindustry.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.dialogs.*;
import mindustry.world.*;

public class SchematicWindow extends WindowTable{
    public SchematicWindow(){
        super("schematics", Icon.paste, t -> {});
    }

    @Override
    public void build(){
        top();
        topBar();

        SearchTable search = new SearchTable((table, query) -> {
            Building core = Vars.player.core();
            for(Schematic s : Vars.schematics.all()){
                if(s.name().toLowerCase().replace("\\W", "").contains(query.toLowerCase().replace("\\W", ""))){
                    table.button(t -> {
                        t.top().left();
                        t.add(new SchematicsDialog.SchematicImage(s).setScaling(Scaling.fit)).size(90f);
                        t.table(i -> {

                            i.top().left();
                            i.labelWrap(s.name() + " [gray]" + s.width + "x" + s.height + "[]").padLeft(5f).top().left();
                            i.row();
                            i.table(j -> {
                                boolean missingResearch = false;
                                boolean bannedBlocks = false;

                                j.top().left();
                                for(ItemStack stack : s.requirements()){
                                    missingResearch |= !stack.item.unlockedNowHost();
                                }

                                OrderedSet<Block> blocks = new OrderedSet<>();
                                s.tiles.each(tile -> blocks.add(tile.block));
                                for(Block block : blocks){
                                    missingResearch |= !block.unlockedNowHost();
                                    bannedBlocks |= Vars.state.rules.bannedBlocks.contains(block);
                                }

                                if(core == null || (!core.items.has(s.requirements()) && !Vars.state.rules.infiniteResources)){
                                    j.image(Icon.warningSmall).color(Pal.accent).padLeft(5f);
                                    j.labelWrap("[accent]Insufficient Resources").padLeft(5f);
                                    j.row();
                                }
                                if(missingResearch){
                                    j.image(Icon.treeSmall).color(Pal.accent).padLeft(5f);
                                    j.labelWrap("[accent]Missing Research").padLeft(5f);
                                    j.row();
                                }
                                if(bannedBlocks){
                                    j.image(Icon.cancelSmall).color(Color.scarlet).padLeft(5f);
                                    j.labelWrap("[scarlet]Contains Banned Blocks").padLeft(5f);
                                    j.row();
                                }
                            }).top().left();
                            i.row();

                            ItemSeq arr = s.requirements();
                            i.table(r -> {
                                int j = 0;
                                for(ItemStack stack : arr){
                                    r.image(stack.item.uiIcon).left().size(Vars.iconSmall);
                                    r.label(() -> {
                                        if(core == null || Vars.state.rules.infiniteResources || core.items.has(stack.item, stack.amount)) return "[lightgray]" + stack.amount + "";
                                        return (core.items.has(stack.item, stack.amount) ? "[lightgray]" : "[scarlet]") + Math.min(core.items.get(stack.item), stack.amount) + "[lightgray]/" + stack.amount;
                                    }).padLeft(2).left().padRight(4);

                                    if(++j % 4 == 0){
                                        r.row();
                                    }
                                }
                            }).top().left();
                        }).pad(5f).top().left();
                    }, () -> {
                        Vars.control.input.useSchematic(s);
                    }).growX();
                    table.row();
                }
            }
        });

        add(search).grow();

        resizeButton();
    }
}