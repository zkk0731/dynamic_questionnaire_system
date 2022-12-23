package com.example.dynamic_questionnaire.constants;

public enum RtnCode {
	SUCCESS("200","Success"),
	TITLE_ALREADY_EXIST("403","問卷名稱已存在"),
	TITLE_NOT_EXIST("403","問卷名稱不存在"),
	QA_NOT_EXIST("200","問答不存在"),
	PARAMETER_REQUIRED("403","所需資料缺失"),
	QA_REQUIRED("403","問題或答案缺失"),
	PARAMETER_ERROR("403","資料格式錯誤"),
	ANS_PARAMETER_ERROR("403","答案格式錯誤"),
	NO_QUESTIONNAIRE("403","無問卷"),
	NO_DELETED_QUESTIONNAIRE("200","無刪除任何問卷"),
	NO_DELETED_QA("200","無刪除任何題目"),
	QUESTIONS_ALREADY_EXIST("403","問題已存在");
	
	private String code;
	
	private String message;

	private RtnCode(String code,String message) {
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
