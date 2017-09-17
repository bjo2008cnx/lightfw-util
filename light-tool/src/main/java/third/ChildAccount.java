package third;

import lombok.Data;

/**
 * 对应account_center的rbac_account表
 *
 * @author Michael.Wang
 * @date 2017/9/17
 */
@Data
public class ChildAccount {
    private Long id;
    private String account, userName, password, telphone, email, merchantId, belongSys, STATUS, createtime, updateTime, createUser, updateUser;
}