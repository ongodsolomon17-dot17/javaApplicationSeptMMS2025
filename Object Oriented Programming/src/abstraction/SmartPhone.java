
package abstraction;

public class SmartPhone implements Callable, Camera, Movie, Music, Wifi, Game{

    @Override
    public void makeCall() {
        System.out.println("john is calling..... ");
    }

    @Override
    public void takePicture() {
                System.out.println("picture have been taken..... ");

    }

    @Override
    public void playMovie() {
                System.out.println("movie is playing..... ");

    }

    @Override
    public void playMusic() {
                System.out.println("music is playing..... ");

    }

    @Override
    public void connectToWifi() {
                System.out.println("Wifi connected..... ");

    }

    @Override
    public void playGame() {
                System.out.println("Solomon is Playing Game..... ");

    }
    
    
}
