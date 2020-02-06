import java.util.Comparator;

public class Card implements Comparable<Card>{
    //1到k
    int rank;
    //花色：红桃......
    int suit;

    @Override
    public int compareTo(Card o) {
        return rank-o.rank;
    }
}