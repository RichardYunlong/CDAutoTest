package Base;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.util.ArrayList;

/**
 * map工具类
 */
public class GMap {
	
    /**
     *  构造函数
     */
	private GMap(){
		GClazz.thisAToolClass();
	}
	
    /**
     * 使用 Map按value进行排序
     *
     * @param oriMap 待排序的map
     *
     * @return 排序后的map
     */
    public static Map<String, String> sortMapByValue(Map<String, String> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<String, String> sortedMap = new LinkedHashMap<>();
        List<Map.Entry<String, String>> entryList = new ArrayList<>(
                oriMap.entrySet());
        entryList.sort(new MapValueComparator());

        Iterator<Map.Entry<String, String>> iter = entryList.iterator();
        Map.Entry<String, String> tmpEntry;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }
}

/**
 * 按 Map 的 value 进行字典序比较的比较器
 *
 * <p>用于把 {@code Map<String, String>} 按其值排序；值为 {@code null} 时会抛出
 * {@link NullPointerException}。</p>
 */
class MapValueComparator implements Comparator<Map.Entry<String, String>> {

    /**
     * 比较两个 Map 条目的值
     *
     * <p>仅比较 {@code value}，不比较 {@code key}，因此值相同的条目顺序不确定。</p>
     *
     * @param me1 第一个条目
     * @param me2 第二个条目
     * @return 负整数、0 或正整数，分别表示 {@code me1} 的值小于、等于或大于 {@code me2} 的值
     */
    @Override
    public int compare(Entry<String, String> me1, Entry<String, String> me2) {

        return me1.getValue().compareTo(me2.getValue());
    }
}
