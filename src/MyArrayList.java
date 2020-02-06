import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

//定义一个类，类的属性是泛型的不确定的，在编译期间才知道的
//泛型类的名称不包括尖括号里的
// public class MyArrayList<E> implements List<E>/<Integer>
//MyArrayList的E是形参，List的E是形参
//定义时形参，使用一个类，使用一个方法时填形参
public class MyArrayList<E> {
    //数组元素类型是E型的
    private  E[]  array;
    private  int  size;
    @SuppressWarnings("unchecked")
    //没有检查类的警告，有可能引发运行时类型转换错误
    public MyArrayList(){
        //array=new E[100];  错的  泛型无法直接定义一个泛型数组，他是发生在编译期间才知道类型
        //注意：泛型无法定义泛型数组
        //首先定义成Object类型，但是没法赋值，所以在做一个强制类型转换
        //压制警告注解（把警告压制一下）
        array = (E[])new Object[100];
        //实力化一个数组强制转换成E[]型
        size = 0;
    }
    public void remove(){
        array[--size]=null;
        //不能直接只写size--,这种写法会引发内存泄漏
        //原本语义上应该死去的对象
        //因为写法原因，没有被GC判定为死掉
        //  java中出现的内存泄漏，内存泄漏：本来应该当成垃圾的对象，因为代码写错了，导致指向它的 引用没有变为空，
        //GC就不回收它
        //size--，认为把对象删掉了，但是还是有引用指向它，不会触发垃圾回收机制
        //对于数组类型或者引用类型的顺序表，一定要记着对他置空
    }
    public static void print(MyArrayList<Object> list){

    }
    public static void main1(String[] args){
        MyArrayList<Object> objectList=null;
        MyArrayList<Integer> integerList=null;

        print(objectList);
        //print(integerList);
    }
    public static void printList(List<?> list){
        for (Object o : list) {
            System.out.println(o);
        }
    }
    private static <E> void swap(E[] array, int i, int j) {
        E t = array[i];
        array[i] = array[j];
        array[j] = t;
    }

    public static void main(String[] args) {
        String[] strings = new String[10];
        swap(strings, 0, 3);
        MyArrayList.<String>swap(strings, 0, 10);
    }
}

