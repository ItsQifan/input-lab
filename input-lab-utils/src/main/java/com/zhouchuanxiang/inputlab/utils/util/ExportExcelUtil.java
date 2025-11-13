package com.zhouchuanxiang.inputlab.utils.util;

import cn.hutool.poi.excel.ExcelWriter;

import java.util.List;

/**
 * @Author zhouchuanxiang
 * @Description  导出Excel工具类
 * @Date 15:29 2025/8/11
 * @Param
 * @return
 **/
public class ExportExcelUtil {

    /**
     * 合并Excel中指定列的相同内容单元格
     *
     * @param writer       ExcelWriter对象
     * @param dataRows     数据行列表（包含标题行）
     * @param mergeColumns 需要合并的列索引（从0开始）
     * @param skipHeader   是否跳过标题行（不合并标题行）
     */
    public static void mergeSameCells(ExcelWriter writer, List<List<String>> dataRows,
                                      int[] mergeColumns, boolean skipHeader) {
        // 数据为空或只有一行，无需合并
        if (dataRows == null || dataRows.size() <= 1) {
            return;
        }

        // 确定起始行（跳过标题行则从1开始，否则从0开始）
        int startRow = skipHeader ? 1 : 0;
        if (startRow >= dataRows.size()) {
            return;
        }

        // 对每一列单独进行合并判断，互不影响
        for (int col : mergeColumns) {
            // 检查列索引是否有效
            if (col < 0) {
                continue;
            }

            // 记录当前列的合并起始行
            int mergeStart = startRow;

            // 遍历数据行，从起始行的下一行开始比较
            for (int i = startRow + 1; i < dataRows.size(); i++) {
                // 检查行是否包含当前列
                if (col >= dataRows.get(i).size() || col >= dataRows.get(i - 1).size()) {
                    // 列索引无效，结束当前列的合并
                    break;
                }

                Object currentValue = dataRows.get(i).get(col);
                Object prevValue = dataRows.get(i - 1).get(col);

                // 如果当前值与上一行值不同，或者是最后一行，需要处理合并
                if (!equals(currentValue, prevValue) || i == dataRows.size() - 1) {
                    // 计算合并的结束行
                    int mergeEnd = equals(currentValue, prevValue) ? i : i - 1;

                    // 如果需要合并的区间大于1行，则执行合并
                    if (mergeEnd > mergeStart) {
                        writer.merge(mergeStart, mergeEnd, col, col, null, false);
                    }

                    // 重置合并起始行为当前行
                    mergeStart = i;
                }
            }
        }
    }

    /**
     * 比较两个对象是否相等，处理null值情况
     */
    private static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

}
