package com.eladmin.service;

import com.eladmin.domain.VerificationCode;
import com.eladmin.domain.vo.EmailVo;

/**
 * @author tq
 * @date 2018-12-26
 */
public interface VerificationCodeService {

    /**
     * 发送邮件验证码
     * @param code
     */
    EmailVo sendEmail(VerificationCode code);

    /**
     * 验证
     * @param code
     */
    void validated(VerificationCode code);
}
