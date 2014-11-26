package akrati.score.me;

 
public class W extends Color{
    Balls balls;
    
    public W(Balls balls){
        this.balls = balls;
    }
    
    public String getOrder(){
        if(balls == null)
            return "White";
        return balls.getOrder() + ",White";
    }
    
    public int point(){
        if(balls == null)
            return 5;
        return 5 + balls.point();
    }
    
}

