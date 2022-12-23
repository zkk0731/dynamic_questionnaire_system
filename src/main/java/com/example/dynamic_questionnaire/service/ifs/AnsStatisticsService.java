package com.example.dynamic_questionnaire.service.ifs;

import com.example.dynamic_questionnaire.vo.AnsStatisticsReq;
import com.example.dynamic_questionnaire.vo.AnsStatisticsRes;

public interface AnsStatisticsService {
	
	public AnsStatisticsRes statistics(AnsStatisticsReq req);
}
