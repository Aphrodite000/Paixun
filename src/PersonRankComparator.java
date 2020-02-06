import java.util.Comparator;
//什么类型的比较器,Person类的对象可以使用该比较器
//比较器的名字就是PersonRankComparator
public class PersonRankComparator implements Comparator<Person> {
    //这个比较器比较的是Person的rank值
    @Override
    //传给你两个引用，
    //o1<o2是负数，相等为0，大于为整数
    public int compare(Person o1, Person o2) {
        return o1.rank-o2.rank;
    }
}
