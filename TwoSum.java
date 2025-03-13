import java.util.HashMap;
import java.util.Arrays;

/**
 * 两数之和问题的Java实现
 */
public class TwoSum {
    
    /**
     * twoSum 方法接收一个整数数组和目标值，返回数组中和为目标值的两个数的下标
     * 如果不存在这样的两个数，返回空数组
     */
    public static int[] twoSum(int[] nums, int target) {
        // 创建一个HashMap用于存储遍历过的数字及其下标
        HashMap<Integer, Integer> map = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            // 计算需要查找的另一个数
            int another = target - nums[i];
            
            // 在HashMap中查找是否存在另一个数
            if (map.containsKey(another)) {
                // 如果找到，返回两个数的下标
                return new int[] {map.get(another), i};
            }
            
            // 将当前数字及其下标存入HashMap
            map.put(nums[i], i);
        }
        
        // 如果没有找到符合条件的两个数，返回空数组
        return new int[0];
    }
    
    public static void main(String[] args) {
        // 测试用例
        int[] nums = {2, 7, 11, 15};
        int target = 18;
        
        // 打印结果
        System.out.println(Arrays.toString(twoSum(nums, target)));
    }
} 