package com.ifast.common.validation;

import java.util.List;

/**
 * 
 * @author Aron
 * @date 2017年8月4日
 */
public class ValidationResult {
	
	//校验结果是否有错
	private boolean hasErrors;
	
	//校验错误信息
//	private Map<String,String> errorMsg;
	private List<String> errorMsg;
	

	public boolean isHasErrors() {
		return hasErrors;
	}

	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}
	

	public List<String> getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(List<String> errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		String content = "" ;
		if(errorMsg.size()>0){
			content=errorMsg.get(0);
		}
//		for(int i = 0; i < errorMsg.size(); i++) {
//			if (i != errorMsg.size() - 1) {
//				content = content + errorMsg.get(i) + ",";
//			}
//			if (i == errorMsg.size() - 1) {
//				content = content + errorMsg.get(i);
//			}
//		}
		return content;
	}

}
