package com.example.dynamic_questionnaire_system.service.ifs;

import com.example.dynamic_questionnaire_system.vo.AnsStatisticsReq;
import com.example.dynamic_questionnaire_system.vo.AnsStatisticsRes;

public interface AnsStatisticsService {
	
	public AnsStatisticsRes statistics(AnsStatisticsReq req);
}
