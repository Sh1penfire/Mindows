package mindows.winesmeeper;

import arc.math.Mathf;

public class WineSmeeper {
    public WineTile[] grid;
    public int w, h;

    public WineSmeeper(int w, int h, int wines){
        setup(w, h, wines);
    }

    public void setup(int w, int h, int wines){
        this.w = w;
        this.h = h;
        grid = new WineTile[w * h];

        int x = 0, y = 0;
        for(int i = 0; i < grid.length; i++){
            grid[i] = new WineTile(this);
            grid[i].x = x;
            grid[i].y = y;

            if(x > w - 1){
                x = 0;
                y++;
            }
            x++;
        }

        for(int i = 0; i < wines; i++){
            grid[Mathf.floor(Mathf.random(0, grid.length - 1))].isWine = true;
        }

        for(WineTile tile : grid){
            tile.setup();
        }
    }

    public WineTile gridAt(int x, int y){
        return (x < 0 || x >= w || y < 0 || y >= h) ? null : grid[y * w + x];
    }
}
