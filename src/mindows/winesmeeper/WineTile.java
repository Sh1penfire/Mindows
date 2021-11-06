package mindows.winesmeeper;

import arc.math.geom.Geometry;

public class WineTile {
    public int neighbors, x, y;
    public boolean covered = true;
    public boolean isWine;
    public WineSmeeper smeeper;

    public WineTile(WineSmeeper smeeper){
        this.smeeper = smeeper;
    }

    public void setup(){
        if(isWine) return;
        for(int i = 0; i < 8; i++){
            WineTile tile = smeeper.gridAt(x + Geometry.d8[i].x, y + Geometry.d8[i].y);
            if(tile != null && tile.isWine){
                neighbors++;
            }
        }
    }

    public void reveal(){
        this.covered = false;
        if(isWine){
            for(WineTile tile : smeeper.grid) tile.covered = false;
            return;
        }
        if(neighbors == 0) floodFillReveal();
    }

    public void floodFillReveal(){
        for(int i = 0; i < 8; i++){
            WineTile tile = smeeper.gridAt(x + Geometry.d8[i].x, y + Geometry.d8[i].y);
            if(tile != null && !tile.isWine && tile.covered){
                tile.reveal();
            }
        }
    }

    public String getText(){
        return covered ? "?" : isWine ? "X" : neighbors == 0 ? "" : String.valueOf(neighbors);
    }
}
