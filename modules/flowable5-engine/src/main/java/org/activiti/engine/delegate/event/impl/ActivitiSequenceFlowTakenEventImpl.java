package org.activiti.engine.delegate.event.impl;

import org.flowable.engine.delegate.event.FlowableEngineEventType;
import org.flowable.engine.delegate.event.FlowableSequenceFlowTakenEvent;

/**
 * @author Joram Barrez
 */
public class ActivitiSequenceFlowTakenEventImpl extends ActivitiEventImpl implements FlowableSequenceFlowTakenEvent {
	
	protected String id;
	protected String sourceActivityId;
	protected String sourceActivityName;
	protected String sourceActivityType;
	protected String sourceActivityBehaviorClass;
	protected String targetActivityId;
	protected String targetActivityName;
	protected String targetActivityType;
	protected String targetActivityBehaviorClass;
	
	public ActivitiSequenceFlowTakenEventImpl(FlowableEngineEventType type) {
	  super(type);
  }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSourceActivityId() {
		return sourceActivityId;
	}

	public void setSourceActivityId(String sourceActivityId) {
		this.sourceActivityId = sourceActivityId;
	}

	public String getSourceActivityName() {
		return sourceActivityName;
	}

	public void setSourceActivityName(String sourceActivityName) {
		this.sourceActivityName = sourceActivityName;
	}

	public String getSourceActivityType() {
		return sourceActivityType;
	}

	public void setSourceActivityType(String sourceActivityType) {
		this.sourceActivityType = sourceActivityType;
	}

	public String getSourceActivityBehaviorClass() {
		return sourceActivityBehaviorClass;
	}

	public void setSourceActivityBehaviorClass(String sourceActivityBehaviorClass) {
		this.sourceActivityBehaviorClass = sourceActivityBehaviorClass;
	}

	public String getTargetActivityId() {
		return targetActivityId;
	}

	public void setTargetActivityId(String targetActivityId) {
		this.targetActivityId = targetActivityId;
	}

	public String getTargetActivityName() {
		return targetActivityName;
	}

	public void setTargetActivityName(String targetActivityName) {
		this.targetActivityName = targetActivityName;
	}

	public String getTargetActivityType() {
		return targetActivityType;
	}

	public void setTargetActivityType(String targetActivityType) {
		this.targetActivityType = targetActivityType;
	}

	public String getTargetActivityBehaviorClass() {
		return targetActivityBehaviorClass;
	}

	public void setTargetActivityBehaviorClass(String targetActivityBehaviorClass) {
		this.targetActivityBehaviorClass = targetActivityBehaviorClass;
	}
	
}
