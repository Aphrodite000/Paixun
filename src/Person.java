public class Person implements Comparable<Person>{
    //两个Person进行比较
    public String name;
    public int age;
    public int sex;
    //得分
    public int rank;

    /**
     *
     * @param o
     * @return  如果<0，表示this指向的Person对象小于o指向的对象
            * 如果==0 表示两个对象是相等的
            * 如果>0表示this指向的Person是大的那个
     */
    @Override
    //覆写接口中的方法
    public int compareTo(Person o) {
        if(age<o.age){
            return -1;
        }else if(age==o.age){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public String toString() {
        return String.format("Person{age=%d,rank=%d}",age,rank);
    }

}

