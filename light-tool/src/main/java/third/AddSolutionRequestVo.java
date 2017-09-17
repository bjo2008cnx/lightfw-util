package third;

import lombok.*;

import java.io.Serializable;

/**
 * Created by hui.sun on 2017/7/19.
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddSolutionRequestVo implements Serializable {
    private static final long serialVersionUID = -4944141362401627534L;

    /**
     * 名称
     */
    private String name;
    /**
     * 说明
     */
    private String description;

    /**
     * 类型，微电商、新零售、智慧餐厅、智慧外卖、本地生活等
     */
    private Integer type;

    /**
     * 是否免费，true，免费,不填，默认true
     */
    private Boolean free;

    /**
     * 展示图片
     */
    private String image;

    /**
     * 插件id
     */
    private Long pluginId;

    /**
     * 解决方案展示url
     */
    private String solutionDescUrl;

    /**
     * 店铺展示url
     */
    private String storeLinkUrl;

    /**
     * 编辑url
     */
    private String editUrl;

    /**
     * 状态，false,开放使用中，true|null：开发中
     */
    private Boolean isDisable;


}
