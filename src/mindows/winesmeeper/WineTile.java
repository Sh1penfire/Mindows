package mindows.winesmeeper;

import arc.math.geom.*;

public class WineTile {
    public int number, x, y;
    public Winesmeeper game;
    public boolean isWine = false, covered = true;

    public WineTile(int x, int y, Winesmeeper game){
        this.game = game;
        this.x = x;
        this.y = y;
    }

    public void reveal(){
        covered = false;
        if(!game.started) game.start();
        if(isWine){
            game.gameOver();
            return;
        }
        if(number == 0) floodFillReveal();
    }

    public void floodFillReveal(){
        if(game != null){
            for(Point2 point : Geometry.d8){
                int ox = x + point.x;
                int oy = y + point.y;
                if(ox < game.width && 0 <= ox && oy < game.height && 0 <= oy){
                    WineTile tile = game.grid[oy][ox];
                    if(!tile.isWine && tile.covered) tile.reveal();
                }
            }
        }
    }

    public void setup(){
        if(isWine)return;
        if(game != null){
            for(Point2 point : Geometry.d8){
                int ox = x + point.x;
                int oy = y + point.y;
                if(ox < game.width && 0 <= ox && oy < game.height && 0 <= oy){
                    if(game.grid[oy][ox].isWine) number++;
                }
            }
        }
    }

    public String getNumber() {
        return covered ? "" : isWine ? "[scarlet]X[]" : number == 0 ? "" : String.valueOf(number);
    }
}
