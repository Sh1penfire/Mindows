package mindows.winesmeeper;

import arc.func.Cons;
import arc.math.Mathf;
import arc.util.Time;

public class Winesmeeper {
    public int minWidth = 5, minHeight = 5;
    public int width, height, wines;
    public WineTile[][] grid;

    public float startTime, endTime;
    public boolean started, ended;

    public void init(int width, int height, int wines){
        this.width = Math.max(minWidth, width);
        this.height = Math.max(minHeight, height);
        this.wines = wines;

        started = false;
        ended = false;

        startTime = 0;
        endTime = 0;

        grid = new WineTile[this.height][this.width];

        for(int y = 0; y < this.height; y++){
            for(int x = 0; x < this.width; x++){
                grid[y][x] = new WineTile(x, y, this);
            }
        }

        generateWines();

        tilesEach(WineTile::setup);
    }

    public void generateWines(){
        for(int i = 0; i < wines; i++){
            grid[Mathf.random(0, height - 1)][Mathf.random(0, width - 1)].isWine = true;
        }
    }

    public void tilesEach(Cons<WineTile> cons){
        for(WineTile[] row : grid){
            for(WineTile tile : row){
                cons.get(tile);
            }
        }
    }

    public void start(){
        started = true;
        startTime = Time.time;
    }

    public void gameOver(){
        tilesEach(t -> t.covered = false);
        ended = true;
        endTime = Time.time;
    }

    public int getTime(){
        return Mathf.floor((started ? ended ? endTime - startTime : Time.time - startTime : 0f) / 60f);
    }
}
