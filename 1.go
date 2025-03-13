package main

import "fmt"

// twoSum 函数接收一个整数数组和目标值，返回数组中和为目标值的两个数的下标
// 如果不存在这样的两个数，返回 nil
func twoSum(nums []int, target int) []int {
	// 创建一个map用于存储遍历过的数字及其下标
	m := make(map[int]int)
	for i := 0; i < len(nums); i++ {
		// 计算需要查找的另一个数
		another := target - nums[i]
		// 在map中查找是否存在另一个数
		if _, ok := m[another]; ok {
			// 如果找到，返回两个数的下标
			return []int{m[another], i}
		}
		// 将当前数字及其下标存入map
		m[nums[i]] = i
	}
	// 如果没有找到符合条件的两个数，返回nil
	return nil
}

func main() {
	// 测试用例
	nums := []int{2, 7, 11, 15}
	target := 18
	// 打印结果
	fmt.Println(twoSum(nums, target))
}
