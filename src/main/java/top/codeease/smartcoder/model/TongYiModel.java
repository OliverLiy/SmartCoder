package top.codeease.smartcoder.model;

import lombok.Data;

import java.util.List;

/**
 * @author by: ly
 * @ClassName: TongYiModel
 * @Description: 通义大模型入参
 * @Date: 2024/5/20 下午9:49
 */
@Data
public class TongYiModel {
    private String model;
    private InputModel input;
}
