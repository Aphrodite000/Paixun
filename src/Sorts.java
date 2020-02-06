import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Sorts {

    //插入排序
    //n个数循环n-1
    public static void insertSort1(int[] array){
        //一共循环n-1
        for(int i=0;i<array.length-1;i++){
            //有序区间   [0,i]
            //无序区间   [i+1,array.length-1]
            //待插入的数据是array[i+1]
            //插入过程从有序区间开始查找
            int key=array[i+1];
            //取无序的第一个数插入为key
            int j;
            for(j=i;j>=0;j--){
                //从后往前找，从i到0；
                if(key>=array[j]){
                    //j+1放k
                    break;
                }
            }
            //搬移，把k放在j+1上,把j+1空出来，从最后一个元素开始往后搬
            //遇到相同的还是后插，保持稳定性
            for(int k=i;k>j;k--){
                array[k+1]=array[k];
            }
            array[j+1]=key;
        }
    }
    //实质是gap为1的插排,边比较边移
    public static void insertSort2(int[] array){
        for(int i=0;i<array.length-1;i++){
            //有序区间   [0,i]
            //无序区间   [i+1,array.length-1]
            //待插入的数据是array[i+1]
            //插入过程从有序区间开始查找
            int key=array[i+1];
            //取无序的第一个数插入为key
            int j;
            //一边找一边插入，从后往前遍历，为了最好情况的出现，比最后一个大直接尾插
            //完全有序时间复杂度是O（n），平均是O（n）方，数组越接近有序，效率越高
            for(j=i;j>=0&&key<array[j];j--){
                //j就是i，指的是最后一个有序数下标，比一个搬一个
                array[j+1]=array[j];
            }
            //牌大直接尾插
            array[j+1]=key;
        }
    }
    public static void insertSort3(int[] array){
        for(int i=0;i<array.length-1;i++){
            int key=array[i+1];
            int j;
            for(j=i;j>=0&&key<array[j];j--){
                array[j+1]=array[j];
            }
            array[j+1]=key;
        }
    }
    //希尔排序 分组排序 分为两步  第一步是求gap，第二步是同组之间插入排序
    //gap为间隔(也就是增量)，所有距离为gap的元素为一组，同一组元素之间进行插入排序
    //直到gap=1结束排序
    private static void insertSortWithGap(int[] array,int gap) {
        //分成两组组内比相当于，两个数不动，所以直接减组数
        //array.length-1，相当于分成一组，是因为n个数，一个数不比，只比n-1次
        for(int i=0;i<array.length-gap;i++){
            int key=array[i+gap];
            int j;
            for(j=i;j>=0&&key<array[j];j-=gap){
                array[j+gap]=array[j];
            }
            array[j+gap]=key;
        }
    }
    public static void shellSort(int[] array){
        int gap=array.length;
        while (true){
            gap=gap/3-1;
            insertSortWithGap(array,gap);
            if(gap==1){
                break;
            }
        }
    }
    //直接选择排序，数据不敏感，时间复杂度O(n^2),不稳定
    //从无序区间选择最大的放到有序区间
    public  static void selectSort(int[] array){
        //要进行array.length-1次循环，所以范围是这样
        for(int i=0;i<array.length-1;i++) {
            //无序区间[0,array.length-i)
            //有序区间[array.length-i,array.length-1]
            //每次循环假设array[0]为最大的
            int max=0;
            //第几轮就排好几末尾几个元素
            for(int j=1;j<array.length-i;j++){
                if(array[j]>array[max]){
                    max=j;
                }
            }
            //交换最大数和无序区间最后一个数
            swap(array,max,array.length-i-1);
        }
    }
    public static void selectSort1(int[] array){
        for(int i=0;i<array.length-1;i++){
            int max=0;
            for(int j=1;j<array.length-i;j++){
                if(array[j]>array[max]){
                    max=j;
                }
            }
            swap(array,max,array.length-i-1);
        }
    }
    //直接选择排序2   遍历无序区间同时取最大值和最小值，最小值low在左边，最大值high在右边
    public  static void selsectSort2(int[] array) {
        int low=0;
        int high=array.length-1;
        //无序区间[low，high]，有序区间[left，low),(high,right]
        //无序区间的长度<=1结束;
        while(low<high){
            //设第一个数是最大数也是最小数
            int min=low;
            int max=low;
            for(int i=low+1;i<=high;i++){
                //遍历无序区间，找最大和最小
                if(array[i]>array[max]){
                    max=i;
                }
                if(array[i]<array[min]){
                    min=i;
                }
            }
            //把小的放在左边
            //low是无序区间的最左端
            swap(array, min, low);
            if (max == low) {
                max = min;
            }
            //把大的放在右边
            //high是无序区间的最大值
            swap(array, max, high);
            low++;
            high--;
        }
    }
    //堆排序，建大堆，交换堆顶元素，然后向下调整，在交换
    //排升序用大堆
    //排降序用小堆
    //有序区间[0,i]
    public static void heapSort(int[] array){
        //先用数组生成堆
        createHeap(array,array.length);
        for(int i=0;i<array.length-1;i++){
            //无序区间 [0,array.length-i)
            //0号下标就是堆顶元素，是最大数
            swap(array,0,array.length-i-1);
            //无序区间[0,array.length-i-1)
            //无序区间长度array.length-i-1;
            //向下调整
            heapify(array,array.length-1-i,0);
        }
    }
    //创建堆，二叉堆
    private static void createHeap(int[] array, int length) {
        for (int i = (length - 2) / 2; i >= 0; i--) {
            heapify(array, length, i);
        }
    }

    private static void heapify(int[] array, int length, int index) {
        int left = 2 * index + 1;
        while (left < length) {
            int max = left;
            if (left + 1 < length && array[left + 1] > array[left]) {
                max = left + 1;
            }

            if (array[index] >= array[max]) {
                break;
            }

            swap(array, index, max);
            index = max;
            left = 2 * index + 1;
        }
    }


    //生成顺序数组
    private static int[] buildSortArray(int n){
        int[] array=new int[n];
        for(int i=0;i<n;i++){
            array[i]=i;
        }
        return array;
    }
    //生成倒序数组
    private static int[] buildReversedSortedArray(int n){
        int[] array=new int[n];
        for(int i=0;i<n;i++){
            array[i]=n-i;
        }
        return array;
    }
    //生成随机数组
    private static int[] buildRandomSortedArray(int n){
        Random random=new Random(20190828);
        int[] array=new int[n];
        for(int i=0;i<n;i++){
            array[i]=random.nextInt(n);
        }
        return array;
    }
    private  static int[] buildEqualsSortedArray(int n){
        int[] array=new int[n];
        for(int i=0;i<n;i++){
            array[i]=n;
        }
        return array;
    }
    public static void main(String[] args) {
        int[] array;
        array=buildSortArray(10);
        System.out.println(Arrays.toString(array));
        //计时
        Long begin=System.nanoTime();
        insertSort2(array);
        Long end=System.nanoTime();
        System.out.println(end-begin);
        System.out.println(Arrays.toString(array));
    }

    //快排
    //挖坑
    public static int partition(int[] array,int left,int right){
        int pivot=right;
        int less=left;
        int great=right;
        //右边为基准值
        while(less<great){
            //先让左边走，小于基准值继续，大于基准值，把数字填到基准值的坑
            while(less<great&&array[less]<pivot){
                less++;
            }
            array[great]=array[less];
            //左边走完右边走，大于基准值继续，小于，把数填到上一个大数array[less]的坑里
            while(less<great&&array[great]>pivot){
                great--;
            }
            array[less]=array[great];
        }
        array[less]=pivot;
        return less;
    }
    public static int partition2(int[] array,int left,int right){
        int pivot=right;
        int less=left;
        int great=right;
        //右边为基准值
        while(less<great){
            while(less<great&&array[less]<pivot){
                less++;
            }
            while(less<great&&array[great]>pivot){
                great--;
            }
            swap(array,less,great);
        }
        array[less]=pivot;
        return less;
    }
    //红蓝两箭头同时从左边走，红色遍历全部，蓝只放小的
    public static int partition3(int[] array,int left,int right){
        int pivot=right;
        int less=left;//蓝  左闭右开
        int i=left;//红
        for(i=left;i<=right;i++){
           if(array[i]<pivot){
               swap(array,left,i);
               less++;
           }
        }
        swap(array,left,right);
        return less;
    }
    public static void swap(int[] array,int left,int right){
        int i=array[left];
        array[left]=array[right];
        array[right]=i;
    }
    //基准值有相等的很多个而且要放中间
    private static int[] partition4(int[] array, int left, int right) {
        int pivot = array[right];
        int less = left;
        int great = right;
        int i = left;
        //比基准值小的，[left,less)
        //比基准值大的，[great,right]
        //相等[less,i]
        //未知[i,great]

        while (i < great) {
            if (array[i] == pivot) {
                i++;
            } else if (array[i] < pivot) {
                swap(array, i, less);
                i++;
                less++;
            } else {
                while (i < great && array[great] > pivot) {
                    great--;
                }

                swap(array, i, great);
            }
        }

        return new int[] {less, great - 1};
    }

    //空间复杂度考虑有没有带N的动态空间，递归的调用栈，跟二叉树高度有关log9n)到n,
    //基准值取右边，如果数据是有序的，就是最坏情况，实际情况最坏情况出现概率大
    //最边上，随机取，多数取中（三数取中），避免一遍没有数，避免最快情况

    //归并排序：
    public static void mergeSort(int[] array){
        mergeSortInternal(array,0,array.length);
    }
    //排序
    public static void mergeSortInternal(int[] array,int low,int high){
        //low和high是左右
        if(low+1>high){
            //或者if(length<=1)
            //说明区间内部已经有序
            return;
        }
        int mid=(low+high)/2;
        //[low,mid)
        //[mid,high)  左闭右开
        mergeSortInternal(array,low,mid);
        mergeSortInternal(array,mid,high);
        //合并
        merge(array,low,mid,high);
    }
    //已经有序，直接合并
    public static void merge(int[] array,int low,int mid,int high){
        int length=high-low;
        //要一个额外的空间来做合并
        int[] extra=new int[length];
        int iLeft=low;
        int iRight=mid;
        //新空间尾下标
        int iExtra=0;
        //区间有数，下标正常才判断大小
        //不能取等号，因为是区间是左闭右开
        while(iLeft<mid&&iRight<high){
            //总是把较小的放在extra
            //两个数组第一个元素挑最小的放在extra中，数组的左边坐标加一，在开始循环
            //各取两个数组的首元素开始比较
            if(array[iLeft]<=array[iRight]){
                //两边相等取左边，保证稳定性
                extra[iExtra++]=array[iLeft++];
            }else {
                extra[iExtra++]=array[iRight++];
            }
            //以上循环数组在慢慢缩小
        }
        //走到这，说明有一个数组已经走完了

        //说明左边的没走完
        while(iLeft<mid){
            //剩余的已经有序，因为他们内部有序，上次合并时排序了，所以可以直接搬运剩余没比较的
            extra[iExtra++]=array[iLeft++];
        }
        //说明右边的没走完
        while(iRight<high){
            extra[iExtra++]=array[iRight++];
        }
        //再将数据搬回去
        //两个数组已经合并，搬回去，i从0到length-1,就是length个数
        for(int i=0;i<length;i++){
            array[low+i]=extra[i];
        }
    }
    public static void mergeSortNoR(int[] array){
        //循环了多少次，先是一个数与另一个数进行归并，然后是两个两个，四个四个，8个8个
        //11个数循环4次，因为小于16，小于2的4次方
        //i代表黑色线上的元素个数
        for(int i=1;i<array.length;i=i*2){
            //j代表蓝色的下标，i*2一次，代表合并的数组扩大一次
            for(int j=0;j<array.length;j=j+2*i){
                //遍历同一个行上的不同组（这个组指的是将要合并成一组）
                int low =j;
                int mid=j+i;
                int high=mid+i;
                if(mid>=array.length){
                    //如果最后单个剩一个组了，下次在进行合并
                    continue;
                }
                if(high>array.length){
                    //比如应该是8个和8个，现实是8个和3个合并
                    high=array.length;
                }

                merge(array,low,mid,high);
            }
        }
    }
*/
    //第一个测试，Person类测试
    //将simpleDemo换成main可以直接测试
    public static void simpleDemo(String[] args) {
        Person p1=new Person();
        p1.age=18;
        Person p2=new Person();
        p2.age=28;
        //定义一个结果
        int comparator=p1.compareTo(p2);
        if(comparator<0){
            System.out.println("p1指向的对象比较小");
        }else if(comparator==0){
            System.out.println("p1和p2是相等的");
        }else{
            System.out.println("p1指向的对象比较大");
        }
    }

    public static void main(String[] args) {
        Person[] people=new Person[20];
        //刚创建数组，全是null，先全创建对象
        Random random=new Random(20190920);
        for(int i=0;i<people.length;i++){
            people[i]=new Person();
            people[i].age=random.nextInt(100);
            people[i].rank=random.nextInt(50);
        }
        System.out.println(Arrays.toString(people));
        mergeSort(people);
        System.out.println(Arrays.toString(people));
        mergeSortWithComparator(people,new PersonRankComparator());
        System.out.println(Arrays.toString(people));
    }

//Person归并排序：

    //Person[] array：数组的每个元素是Person类的
    public static void mergeSort(Person[] array) {
        mergeSort1(array, 0, array.length);
    }

    // [low, high)
    private static void mergeSort1(Person[] array, int low, int high) {
        //low和high是左右
        int length = high - low;
        if (length <= 1) {
            //说明区间已经有序
            return;
        }
        //划分左右区间
        int mid = (high + low) / 2;
        //左闭右开
        // [low, mid)
        mergeSort1(array, low, mid);
        // [mid, high)
        mergeSort1(array, mid, high);

        // 合并两个有序区间
        merge(array, low, mid, high);
    }
    //已经有序，直接合并
    private static void merge(Person[] array, int low, int mid, int high) {
        int length = high - low;
        //要一个额外的空间来做合并
        Person[] extra = new Person[length];

        int iLeft = low;
        int iRight = mid;
        //新空间尾下标
        int iExtra = 0;
        //区间有数，下标正常才判断大小
        //不能取等号，因为是区间是左闭右开
        while (iLeft < mid && iRight < high) {
            //总是把较小的放在extra
            //两个数组第一个元素挑最小的放在extra中，数组的左边坐标加一，在开始循环
            //各取两个数组的首元素开始比较

            //Person[] array：数组的每个元素是Person类的
            int r = array[iLeft].compareTo(array[iRight]);
            if (r <= 0) {
                //两边相等取左边，保证稳定性
                extra[iExtra++] = array[iLeft++];
            } else {
                extra[iExtra++] = array[iRight++];
            }
            //数组在慢慢缩小
        }
        //走到这一步说明肯定有一个数组走完了

        //左边数组没有走完，把剩余的直接搬过去，因为上次排序中已经有序了，所以直接搬
        while (iLeft < mid) {
            extra[iExtra++] = array[iLeft++];
        }
        //右边数组没有走完，把剩余的直接搬过去
        while (iRight < high) {
            extra[iExtra++] = array[iRight++];
        }
        //再从新数组中把元素搬回原数组
        //两个数组已经合并，搬回去，i从0到length-1,就是length个数
        for (int i = 0; i < length; i++) {
            array[i + low] = extra[i];
        }
    }



    //用比较器的归并排序


    //除了要传给一个数组外，还要传给一个比较器
    public static void mergeSortWithComparator(Person[] array, Comparator<Person> comparator){
        mergeSort1WithComparator(array,0,array.length,comparator);
    }
    // [low, high)
    private static void mergeSort1WithComparator(Person[] array, int low, int high,Comparator<Person> comparator) {
        //low和high是左右
        int length = high - low;
        if (length <= 1) {
            //说明区间已经有序
            return;
        }
        //划分左右区间
        int mid = (high + low) / 2;
        //左闭右开
        // [low, mid)
        mergeSort1WithComparator(array, low, mid,comparator);
        // [mid, high)
        mergeSort1WithComparator(array, mid, high,comparator);

        // 合并两个有序区间
        mergeWithComparator(array, low, mid, high,comparator);
        //已经有序，直接合并
    }
    //已经有序，直接合并
    private static void mergeWithComparator(Person[] array, int low, int mid, int high,Comparator<Person> comparator) {
        int length = high - low;
        //要一个额外的空间来做合并
        Person[] extra = new Person[length];

        int iLeft = low;
        int iRight = mid;
        //新空间尾下标
        int iExtra = 0;
        //区间有数，下标正常才判断大小
        //不能取等号，因为是区间是左闭右开
        while (iLeft < mid && iRight < high) {
            //总是把较小的放在extra
            //两个数组第一个元素挑最小的放在extra中，数组的左边坐标加一，在开始循环
            //各取两个数组的首元素开始比较

            //Person[] array：数组的每个元素是Person类的
            //int r = array[iLeft].compareTo(array[iRight]);

            //传比较器主要是为了这一步，比较时直接调用比较器，传入两个对象进行比较，
            // 上一个方法是，用一个对象去调用该对象的一个方法，给这个方法传入另一个对象再去比较

            int r=comparator.compare(array[iLeft],array[iRight]);
            if (r <= 0) {
                //两边相等取左边，保证稳定性
                extra[iExtra++] = array[iLeft++];
            } else {
                extra[iExtra++] = array[iRight++];
            }
            //数组在慢慢缩小
        }
        //走到这一步说明肯定有一个数组走完了

        //左边数组没有走完，把剩余的直接搬过去，因为上次排序中已经有序了，所以直接搬
        while (iLeft < mid) {
            extra[iExtra++] = array[iLeft++];
        }
        //右边数组没有走完，把剩余的直接搬过去
        while (iRight < high) {
            extra[iExtra++] = array[iRight++];
        }
        //再从新数组中把元素搬回原数组
        //两个数组已经合并，搬回去，i从0到length-1,就是length个数
        for (int i = 0; i < length; i++) {
            array[i + low] = extra[i];
        }
    }


    //冒泡排序
    public static void bubbleSort(Person[] array,Comparator<Person> comparator){
        for(int i=0;i<array.length-1;i++){
            //从前往后遍历拍，大的放最后
            //i是已经拍好序的数,i=0是排好0个数时
            for(int j=0;j<array.length-i-1;j++){
                int r=comparator.compare(array[j],array[j+1]);
                //int r=array[j].compareTo(array[j+1]);
                if(r>0){
                    swap(array,j,j+1);
                }
            }
        }
    }

    private static void swap(Person[] array, int j, int i) {
        Person t=array[j];
        array[j]=array[i];
        array[i]=t;
    }
    //传一个对象数组people，和一个比较器
    public static void testBubbleSort(Person[] people) {
        bubbleSort(people, new PersonRankComparator());
    }
}
