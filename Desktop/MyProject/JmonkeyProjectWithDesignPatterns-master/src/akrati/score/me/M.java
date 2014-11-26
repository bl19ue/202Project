package akrati.score.me;

public class M extends Color{
    Balls balls;
    
    public M(Balls balls){
        this.balls = balls;
    }
    
    public String getOrder(){
        if(balls == null)
            return "Magenta";
        return balls.getOrder() + ",Magenta";
    }
    
    public int point(){
        if(balls == null)
            return 1;
        return 1 + balls.point();
    }
    
}
