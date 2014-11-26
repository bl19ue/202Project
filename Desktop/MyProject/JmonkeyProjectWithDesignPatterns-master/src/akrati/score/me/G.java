package akrati.score.me;


public class G extends Color{
    Balls balls;
    
    public G(Balls balls){
        this.balls = balls;
    }
    
    public String getOrder(){
        if(balls == null)
            return "Green";
        return balls.getOrder() + ",Green";
    }
    
    public int point(){
        if(balls == null)
            return 3;
        return 3 + balls.point();
    }
    
}

