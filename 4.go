package main

// findMedianSortedArrays 函数用于寻找两个有序数组的中位数
// nums1 和 nums2 是两个已排序的整数数组
// 返回这两个数组合并后的中位数
func findMedianSortedArrays(nums1 []int, nums2 []int) float64 {
	// 为了优化时间复杂度，我们总是让 nums1 的长度小于等于 nums2
	if len(nums1) > len(nums2) {
		return findMedianSortedArrays(nums2, nums1)
	}
	// 初始化二分查找的边界和中间位置
	// k 是合并后数组的中间位置
	low, high, k, nums1Mid, nums2Mid := 0, len(nums1), (len(nums1)+len(nums2)+1)>>1, 0, 0
	for low <= high {
		// 在 nums1 中二分查找分割线的位置
		// nums1:  ……………… nums1[nums1Mid-1] | nums1[nums1Mid] ……………………
		// nums2:  ……………… nums2[nums2Mid-1] | nums2[nums2Mid] ……………………
		nums1Mid = low + (high-low)>>1 // 分界限右侧是 mid，分界线左侧是 mid - 1
		nums2Mid = k - nums1Mid        // nums2 的分割线位置由 nums1 的位置决定

		// 需要确保分割线左边的数都小于等于右边的数
		if nums1Mid > 0 && nums1[nums1Mid-1] > nums2[nums2Mid] { // nums1 中的分界线划多了，要向左边移动
			high = nums1Mid - 1
		} else if nums1Mid != len(nums1) && nums1[nums1Mid] < nums2[nums2Mid-1] { // nums1 中的分界线划少了，要向右边移动
			low = nums1Mid + 1
		} else {
			// 找到合适的划分了，需要输出最终结果了
			// 分为奇数偶数 2 种情况
			break
		}
	}

	// 计算分割线左边的最大值
	midLeft, midRight := 0, 0
	if nums1Mid == 0 { // nums1 左边为空
		midLeft = nums2[nums2Mid-1]
	} else if nums2Mid == 0 { // nums2 左边为空
		midLeft = nums1[nums1Mid-1]
	} else { // 取左边的最大值
		midLeft = max(nums1[nums1Mid-1], nums2[nums2Mid-1])
	}

	// 如果合并后的数组长度为奇数，直接返回左边的最大值
	if (len(nums1)+len(nums2))&1 == 1 {
		return float64(midLeft)
	}

	// 计算分割线右边的最小值
	if nums1Mid == len(nums1) { // nums1 右边为空
		midRight = nums2[nums2Mid]
	} else if nums2Mid == len(nums2) { // nums2 右边为空
		midRight = nums1[nums1Mid]
	} else { // 取右边的最小值
		midRight = min(nums1[nums1Mid], nums2[nums2Mid])
	}

	// 如果长度为偶数，返回左边最大值和右边最小值的平均数
	return float64(midLeft+midRight) / 2
}
