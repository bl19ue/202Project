
package akrati.score.me;


public class Nohit extends Balls{
    public Nohit(){
        order = "Did not hit";
    }
    
    public String getOrder()
    {
        return order;
    }
    
    public int point(){
        return 0;
    }
}
