package com.gdin.dzzwsyb.swzzbdbxt.web.model;

import java.util.ArrayList;
import java.util.List;

public class MsgCoSponsorExample {
	protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public MsgCoSponsorExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public boolean isDistinct() {
		return distinct;
	}

	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	protected abstract static class GeneratedCriteria {
		protected List<Criterion> criteria;

		protected GeneratedCriteria() {
			super();
			criteria = new ArrayList<Criterion>();
		}

		public boolean isValid() {
			return criteria.size() > 0;
		}

		public List<Criterion> getAllCriteria() {
			return criteria;
		}

		public List<Criterion> getCriteria() {
			return criteria;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
		}

		protected void addCriterion(String condition, Object value, String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1, Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		public Criteria andIdIsNull() {
			addCriterion("id is null");
			return (Criteria) this;
		}

		public Criteria andIdIsNotNull() {
			addCriterion("id is not null");
			return (Criteria) this;
		}

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

		public Criteria andMsgIdIsNull() {
			addCriterion("msg_id is null");
			return (Criteria) this;
		}

		public Criteria andMsgIdIsNotNull() {
			addCriterion("msg_id is not null");
			return (Criteria) this;
		}

		public Criteria andMsgIdEqualTo(String value) {
			addCriterion("msg_id =", value, "msgId");
			return (Criteria) this;
		}

		public Criteria andMsgIdNotEqualTo(String value) {
			addCriterion("msg_id <>", value, "msgId");
			return (Criteria) this;
		}

		public Criteria andMsgIdGreaterThan(String value) {
			addCriterion("msg_id >", value, "msgId");
			return (Criteria) this;
		}

		public Criteria andMsgIdGreaterThanOrEqualTo(String value) {
			addCriterion("msg_id >=", value, "msgId");
			return (Criteria) this;
		}

		public Criteria andMsgIdLessThan(String value) {
			addCriterion("msg_id <", value, "msgId");
			return (Criteria) this;
		}

		public Criteria andMsgIdLessThanOrEqualTo(String value) {
			addCriterion("msg_id <=", value, "msgId");
			return (Criteria) this;
		}

		public Criteria andMsgIdLike(String value) {
			addCriterion("msg_id like", value, "msgId");
			return (Criteria) this;
		}

		public Criteria andMsgIdNotLike(String value) {
			addCriterion("msg_id not like", value, "msgId");
			return (Criteria) this;
		}

		public Criteria andMsgIdIn(List<String> values) {
			addCriterion("msg_id in", values, "msgId");
			return (Criteria) this;
		}

		public Criteria andMsgIdNotIn(List<String> values) {
			addCriterion("msg_id not in", values, "msgId");
			return (Criteria) this;
		}

		public Criteria andMsgIdBetween(String value1, String value2) {
			addCriterion("msg_id between", value1, value2, "msgId");
			return (Criteria) this;
		}

		public Criteria andMsgIdNotBetween(String value1, String value2) {
			addCriterion("msg_id not between", value1, value2, "msgId");
			return (Criteria) this;
		}

		public Criteria andRoleIdIsNull() {
			addCriterion("role_id is null");
			return (Criteria) this;
		}

		public Criteria andRoleIdIsNotNull() {
			addCriterion("role_id is not null");
			return (Criteria) this;
		}

		public Criteria andRoleIdEqualTo(Long value) {
			addCriterion("role_id =", value, "roleId");
			return (Criteria) this;
		}

		public Criteria andRoleIdNotEqualTo(Long value) {
			addCriterion("role_id <>", value, "roleId");
			return (Criteria) this;
		}

		public Criteria andRoleIdGreaterThan(Long value) {
			addCriterion("role_id >", value, "roleId");
			return (Criteria) this;
		}

		public Criteria andRoleIdGreaterThanOrEqualTo(Long value) {
			addCriterion("role_id >=", value, "roleId");
			return (Criteria) this;
		}

		public Criteria andRoleIdLessThan(Long value) {
			addCriterion("role_id <", value, "roleId");
			return (Criteria) this;
		}

		public Criteria andRoleIdLessThanOrEqualTo(Long value) {
			addCriterion("role_id <=", value, "roleId");
			return (Criteria) this;
		}

		public Criteria andRoleIdIn(List<Long> values) {
			addCriterion("role_id in", values, "roleId");
			return (Criteria) this;
		}

		public Criteria andRoleIdNotIn(List<Long> values) {
			addCriterion("role_id not in", values, "roleId");
			return (Criteria) this;
		}

		public Criteria andRoleIdBetween(Long value1, Long value2) {
			addCriterion("role_id between", value1, value2, "roleId");
			return (Criteria) this;
		}

		public Criteria andRoleIdNotBetween(Long value1, Long value2) {
			addCriterion("role_id not between", value1, value2, "roleId");
			return (Criteria) this;
		}

		public Criteria andIsSignedIsNull() {
			addCriterion("is_signed is null");
			return (Criteria) this;
		}

		public Criteria andIsSignedIsNotNull() {
			addCriterion("is_signed is not null");
			return (Criteria) this;
		}

		public Criteria andIsSignedEqualTo(Integer value) {
			addCriterion("is_signed =", value, "isSigned");
			return (Criteria) this;
		}

		public Criteria andIsSignedNotEqualTo(Integer value) {
			addCriterion("is_signed <>", value, "isSigned");
			return (Criteria) this;
		}

		public Criteria andIsSignedGreaterThan(Integer value) {
			addCriterion("is_signed >", value, "isSigned");
			return (Criteria) this;
		}

		public Criteria andIsSignedGreaterThanOrEqualTo(Integer value) {
			addCriterion("is_signed >=", value, "isSigned");
			return (Criteria) this;
		}

		public Criteria andIsSignedLessThan(Integer value) {
			addCriterion("is_signed <", value, "isSigned");
			return (Criteria) this;
		}

		public Criteria andIsSignedLessThanOrEqualTo(Integer value) {
			addCriterion("is_signed <=", value, "isSigned");
			return (Criteria) this;
		}

		public Criteria andIsSignedIn(List<Integer> values) {
			addCriterion("is_signed in", values, "isSigned");
			return (Criteria) this;
		}

		public Criteria andIsSignedNotIn(List<Integer> values) {
			addCriterion("is_signed not in", values, "isSigned");
			return (Criteria) this;
		}

		public Criteria andIsSignedBetween(Integer value1, Integer value2) {
			addCriterion("is_signed between", value1, value2, "isSigned");
			return (Criteria) this;
		}

		public Criteria andIsSignedNotBetween(Integer value1, Integer value2) {
			addCriterion("is_signed not between", value1, value2, "isSigned");
			return (Criteria) this;
		}

		public Criteria andIsAssignedIsNull() {
			addCriterion("is_assigned is null");
			return (Criteria) this;
		}

		public Criteria andIsAssignedIsNotNull() {
			addCriterion("is_assigned is not null");
			return (Criteria) this;
		}

		public Criteria andIsAssignedEqualTo(Integer value) {
			addCriterion("is_assigned =", value, "isAssigned");
			return (Criteria) this;
		}

		public Criteria andIsAssignedNotEqualTo(Integer value) {
			addCriterion("is_assigned <>", value, "isAssigned");
			return (Criteria) this;
		}

		public Criteria andIsAssignedGreaterThan(Integer value) {
			addCriterion("is_assigned >", value, "isAssigned");
			return (Criteria) this;
		}

		public Criteria andIsAssignedGreaterThanOrEqualTo(Integer value) {
			addCriterion("is_assigned >=", value, "isAssigned");
			return (Criteria) this;
		}

		public Criteria andIsAssignedLessThan(Integer value) {
			addCriterion("is_assigned <", value, "isAssigned");
			return (Criteria) this;
		}

		public Criteria andIsAssignedLessThanOrEqualTo(Integer value) {
			addCriterion("is_assigned <=", value, "isAssigned");
			return (Criteria) this;
		}

		public Criteria andIsAssignedIn(List<Integer> values) {
			addCriterion("is_assigned in", values, "isAssigned");
			return (Criteria) this;
		}

		public Criteria andIsAssignedNotIn(List<Integer> values) {
			addCriterion("is_assigned not in", values, "isAssigned");
			return (Criteria) this;
		}

		public Criteria andIsAssignedBetween(Integer value1, Integer value2) {
			addCriterion("is_assigned between", value1, value2, "isAssigned");
			return (Criteria) this;
		}

        public Criteria andIsAssignedNotBetween(Integer value1, Integer value2) {
            addCriterion("is_assigned not between", value1, value2, "isAssigned");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }
    }

	public static class Criteria extends GeneratedCriteria {

		protected Criteria() {
			super();
		}
	}

	public static class Criterion {
		private String condition;

		private Object value;

		private Object secondValue;

		private boolean noValue;

		private boolean singleValue;

		private boolean betweenValue;

		private boolean listValue;

		private String typeHandler;

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		protected Criterion(String condition) {
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		protected Criterion(String condition, Object value, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		protected Criterion(String condition, Object value) {
			this(condition, value, null);
		}

		protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		protected Criterion(String condition, Object value, Object secondValue) {
			this(condition, value, secondValue, null);
		}
	}
}