package com.example.dynamic_questionnaire_system.service.ifs;

import com.example.dynamic_questionnaire_system.vo.AnsStatisticsReq;
import com.example.dynamic_questionnaire_system.vo.AnsStatisticsRes;

public interface AnsStatisticsService {
	
	//顯示答案統計結果
	public AnsStatisticsRes statistics(AnsStatisticsReq req);
	
	//顯示所有填寫人
	public AnsStatisticsRes readAllUsers(AnsStatisticsReq req);
	
	//顯示單一填寫人填寫資料
	public AnsStatisticsRes readUsersInfo(AnsStatisticsReq req);
}
