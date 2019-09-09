import java.util.Arrays;
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
    //实质是gap为1的插排
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
    //希尔排序
    //gap为间隔，分成gap组
    //间隔插牌,分组排序
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
        for(int i=0;i<array.length-1;i++) {
            //无序区间[0,array.length-i)
            //有序区间[array.length-i,array.length-1]
            int max=0;
            for(int j=1;j<array.length-i;j++){
                if(array[j]>array[max]){
                    max=j;
                }
            }
            //交换最大数和无序区间最后一个数
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
            swap(array, min, low);
            if (max == low) {
                max = min;
            }
            //把大的放在右边
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
        if(low+1>=high){
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
        while(iLeft<mid&&iRight<high){
            //总是把较小的放在extra
            //两个数组第一个元素挑最小的放在extra中，数组的左边坐标加一，在开始循环
            if(array[iLeft]<=array[iRight]){
                //两边相等取左边，保证稳定性
                extra[iExtra++]=array[iLeft++];
            }else {
                extra[iExtra++]=array[iRight++];
            }
        }
        //走到这，说明有一个数组已经走完了

        //说明左边的没走完
        while(iLeft<mid){
            extra[iExtra++]=array[iLeft++];
        }
        //说明右边的没走完
        while(iRight<high){
            extra[iExtra++]=array[iRight++];
        }
        //再将数据搬回去
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

}
