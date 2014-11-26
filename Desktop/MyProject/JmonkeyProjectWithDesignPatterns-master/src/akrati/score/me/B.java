package akrati.score.me;


public class B extends Color{
    Balls balls;
    
    public B(Balls balls){
        this.balls = balls;
    }
    
    public String getOrder(){
        if(balls == null)
            return "Blue";
        return balls.getOrder() + ",Blue";
    }
    
    public int point(){
        if(balls == null)
            return 2;
        return 2 + balls.point();
    }
    
}
