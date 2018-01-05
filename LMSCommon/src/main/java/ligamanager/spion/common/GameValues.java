package ligamanager.spion.common;

public class GameValues<T> {

    public T firstHalf;
    public T secondHalf;
    public T extraTime;

    public GameValues() {}

    public GameValues(T firstHalf, T secondHalf, T extraTime) {
        this.firstHalf = firstHalf;
        this.secondHalf = secondHalf;
        this.extraTime = extraTime;
    }
}
