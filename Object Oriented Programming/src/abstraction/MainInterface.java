
package abstraction;


public class MainInterface {
    public static void main(String[] args){
    SmartPhone smartphone = new SmartPhone();
    Nokia3310 nokia3310 = new Nokia3310();
    
    
    System.out.println("Smart Phone interface");
    
    
    smartphone.makeCall();
    smartphone.playGame();
    smartphone.playMovie();
    smartphone.playMusic();
    smartphone.takePicture();
    smartphone.connectToWifi();
    
    
        System.out.println("Nokia3310 interface\n");
        
        nokia3310.makeCall();
        nokia3310.playGame();
        nokia3310.playMusic();
        nokia3310.takePicture();


        
    }       

}
