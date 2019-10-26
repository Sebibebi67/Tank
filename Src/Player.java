public class Player{

    private int x, y;
    private int move;
    private double alphaMove;

    public Player(int x, int y){
        this.x = x;
        this.y = y;
        this.move = 10;
        this.alphaMove = 0;
    }

    public void display(){}

    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public double getAlphaMove(){return this.alphaMove;}

    public void move(Boolean[] keys){
        if (keys[0]){
            this.x = (int) (this.x + Math.cos(this.alphaMove)*this.move);
            this.y = (int) (this.y + Math.sin(this.alphaMove)*this.move);
        }
        if (keys[1]){
            this.alphaMove = this.alphaMove + Math.PI/4;
        }
        if (keys[2]){
            this.x = (int) (this.x - Math.cos(this.alphaMove)*this.move);
            this.y = (int) (this.y - Math.sin(this.alphaMove)*this.move);
        }
        if (keys[3]){
            this.alphaMove = this.alphaMove - Math.PI/4;
        }
    }
}