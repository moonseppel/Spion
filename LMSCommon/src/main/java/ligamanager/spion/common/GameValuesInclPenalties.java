package ligamanager.spion.common;

public class GameValuesInclPenalties<T> extends GameValues<T> {

    public T penalityShooting;

    public GameValuesInclPenalties() { }

    public GameValuesInclPenalties(T firstHalf, T secondHalf, T extraTime, T penalityShooting) {
        super(firstHalf, secondHalf, extraTime);
        this.penalityShooting = penalityShooting;
    }
}
