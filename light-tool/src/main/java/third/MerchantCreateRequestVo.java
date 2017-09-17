package third;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hui.sun on 2017/7/5.
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantCreateRequestVo implements Serializable {

    private static final long serialVersionUID = -795540171748753000L;

    /**
     * 店铺id
     */
    private Long pid;

    /**
     * 商户id
     */
    private Long wid;

    /**
     * 名称
     */
    String merchantName;

    /**
     * 地址：省
     */
    private Integer province;

    /**
     * 地址：市
     */
    private Integer city;

    /**
     * 地址:区
     */
    private Integer region;

    /**
     * 	行业(1级)
     */
    private Long parentIndustryId;

    /**
     * 	行业(2级)
     */
    private Long childIndustryId;

    /**
     * logo
     */
    String logo;

    /**
     * 描述
     */
    String desc;

    /**
     * 名称
     */
    String name;

    /**
     * 手机号
     */
    private String phone;

    private String qq;

    private String weixin;

    private String mail;

    /**
     * 创建店铺使用的解决方案
     */
    private Long solutionId;

    /**
     * 使用解决方案的时候使用的套餐id
     */
    private Long packId;

    /**
     * 解决方案有效时间，此字段和startTime&endTime必须有不为空的，否则会报错
     */
    private Integer validDays;

    /**
     * 解决方案生效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 解决方案失效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
