import java.util.Arrays;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class ParallelQuickSort
{
int[] list;
int threshold;
int left;
int right;
  public ParallelQuickSort(int[] a, int threshold)
  {
this.list = a; 
this.threshold = threshold;
this.left =0;
this.right = list.length -1;
  }
  public static void startMainTask(int[] list, int threshold) 
  {
    RecursiveAction mainTask = new SortTask(list,threshold);
    ForkJoinPool pool = new ForkJoinPool();
    pool.invoke(mainTask);
  }

  private static class SortTask extends RecursiveAction 
  {
 
    private int[] list;
    private int threshold;
    private int left;
    private int right;
    
    SortTask(int[] list, int threshold) 
    {
      this.list = list;
      this.threshold = threshold;
      this.right = list.length-1;
      this.left = left;
    }
    SortTask(int[]list, int left, int right)
    {
    this.list = list;
    this.left = left;
    this.right = right;
    }
    

    protected void compute() {
      if (list.length < threshold)
      {
        Arrays.sort(list);
      }
      else {
    if(left<right){
     int pivot = quickSort(list,left,right);
     invokeAll(new SortTask(list,left,pivot), new SortTask(list,pivot +1,right));
        
    } 
      }
    }



    private int quickSort( int[] array,int low, int high) {
        
        int i = low -1;
        int j = high +1;
        int pivot = array[low];

    while(true)
   {
    do{
    i++;
    }
    while(array[i] < pivot);
    do {
    j--;
    }
       while(array[j] > pivot);
    if(i>=j)
    return j;
    swap(array,i,j);
            
        }
    }
        private void swap(int[] array, int low, int high)
        {
        int temp = array[low];
        array[low] = array[high];
        array[high] = temp;
        }
    }
  }
