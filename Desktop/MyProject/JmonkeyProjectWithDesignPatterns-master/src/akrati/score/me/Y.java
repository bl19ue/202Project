package akrati.score.me;


public class Y extends Color{
    Balls balls;
    
    public Y(Balls balls){
        this.balls = balls;
    }
    
    public String getOrder(){
        if(balls == null)
            return "Yellow";
        return balls.getOrder() + ",Yellow";
    }
    
    public int point(){
        if(balls == null)
            return 4;
        return 4 + balls.point();
    }
    
}

