package mindows.ui.elements;

import arc.func.*;
import arc.math.Mathf;
import arc.scene.event.Touchable;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.util.Strings;
import mindustry.ui.*;

public class StackSlider extends Table {
    public Slider slider;
    public StackSlider(float min, float max, float step, float def, Floatc listener){
        super();
        slider = new Slider(min, max, step, false);
        slider.setValue(def);

        Label value = new Label(number(def), Styles.outlineLabel);

        Table contents = new Table();
        contents.add(value).padLeft(10f).left().growX().get().parent = null;
        contents.touchable = Touchable.disabled;

        slider.changed(() -> {
            listener.get(slider.getValue());
            value.setText(number(slider.getValue()));
        });

        slider.change();

        stack(slider, contents).top().left().growX();
    }

    public void setValue(float value){
        slider.setValue(value);
    }

    // fix floating point errors and remove decimals when unnecessary
    private String number(float number){
        if(number % 1 == 0){
            return String.valueOf(Mathf.floor(number));
        }else{
            return Strings.autoFixed(number, 7);
        }
    }
}
