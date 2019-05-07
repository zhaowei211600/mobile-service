package com.third.mobile.service;

import com.third.mobile.bean.Requirement;

public interface IRequirementService {

    boolean saveRequire(Requirement requirement);

    void sendEmail(Requirement requirement);
}
