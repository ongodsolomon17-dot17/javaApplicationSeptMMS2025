
package abstraction;

public class Nokia3310 implements Callable,Music,Game,Camera{

    @Override
    public void makeCall() {
        System.out.println("making Call");
    }

    @Override
    public void takePicture() {
                System.out.println("Taking picture");

    }

    @Override
    public void playMusic() {
                System.out.println("Playing music");

    }

    @Override
    public void playGame() {
                System.out.println("Playing game");

    }
    
}
