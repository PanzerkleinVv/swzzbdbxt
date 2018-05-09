package com.gdin.dzzwsyb.swzzbdbxt.core.util;

import java.util.Arrays;

public class SelectArray {

	private static String[] userState = new String[] { "停用", "启用" };
	private static String[] submissionType = new String[] { "", "办结", "延期", "中止", "阶段性办结" };
	private static String[] yesOrNo = new String[] { "否", "是" };
	private static String[] submissionStatus = new String[] { "草稿", "待审核", "已审核" };
	private static String[] msgBasis = new String[] { "立项依据1", "立项依据2", "立项依据3", "自定义" };
	private static String[] msgStatus = new String[] { "草稿", "在办", "逾期", "阶段性型办结", "办结", "中止" };
	private static String[] noticeType = new String[] { "动态更新", "逾期", "待审核", "待签收", "待指派", "督查草稿", "提请草稿" };
	private static String[] noticeTarget = new String[] { "msg", "submission", "sponsor", "co-sponsor" };
	private static String[] resdStatus = new String[] { "已读", "未读" };

	private SelectArray() {

	}

	public static String[] getUserState() {
		return Arrays.copyOf(userState, userState.length);
	}

	public static String[] getSubmissionType() {
		return Arrays.copyOf(submissionType, submissionType.length);
	}

	public static String[] getYesOrNo() {
		return Arrays.copyOf(yesOrNo, yesOrNo.length);
	}

	public static String[] getSubmissionStatus() {
		return Arrays.copyOf(submissionStatus, submissionStatus.length);
	}

	public static String[] getMsgBasis() {
		return Arrays.copyOf(msgBasis, msgBasis.length);
	}

	public static String[] getMsgStatus() {
		return Arrays.copyOf(msgStatus, msgStatus.length);
	}

	public static String[] getNoticeType() {
		return Arrays.copyOf(noticeType, noticeType.length);
	}

	public static String[] getNoticeTarget() {
		return Arrays.copyOf(noticeTarget, noticeTarget.length);
	}

	public static String[] getResdStatus() {
		return Arrays.copyOf(resdStatus, resdStatus.length);
	}

}
