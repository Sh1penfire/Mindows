package mindows.ui.elements;

import arc.func.*;
import arc.math.Mathf;
import arc.scene.event.Touchable;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import mindustry.ui.*;

public class StackSlider extends Table {
    public StackSlider(float min, float max, float step, float def, Floatc listener){
        super();
        Slider slider = new Slider(min, max, step, false);
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

    private String number(float number){
        if(number % 1 == 0){
            return String.valueOf(Mathf.floor(number));
        }else{
            return String.valueOf(number);
        }
    }
}
