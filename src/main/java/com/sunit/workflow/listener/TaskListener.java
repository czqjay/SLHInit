package com.sunit.workflow.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.IdentityLinkType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;



/**
 * 
 * 
 * 类名称：TaskListener
 * 类描述：  全局事件监听器  监听类型见  ActivitiEventType
 * 创建人：joye
 * 创建时间：Jul 16, 2015 2:10:35 PM
 * 修改人：joye
 * 修改时间：Jul 16, 2015 2:10:35 PM
 * 修改备注：
 * @version 
 *
 */
@Component
@Scope("prototype")
public class TaskListener implements ActivitiEventListener{  
 

	public boolean isFailOnException() {
		// TODO Auto-generated method stub
		return false; 
	}
	public void onEvent(ActivitiEvent event) {
		if(event.getType()==ActivitiEventType.TASK_COMPLETED){  
			TaskEntity  task= (TaskEntity) ((ActivitiEntityEventImpl)event).getEntity(); 
			
			if(task.getExecution().getVariable("auditRemark")!=null){
				String message = task.getExecution().getVariable("auditRemark").toString();
				event.getEngineServices().getTaskService().addComment(task.getId(),task.getProcessInstanceId(), "String", message);
			}
		}
		
		
		
		
		if(event.getType()==ActivitiEventType.CUSTOM_IDENTITYLINK_BEFORE){ 
			System.out.println("TaskCompleteListener.onEvent()"); 
			
			IdentityLinkEntity  link= (IdentityLinkEntity) ((ActivitiEntityEventImpl)event).getEntity();
			if(link.getTask()==null) return;
			
			if (link.getTask().getExecution().getVariable("customRole")!=null){
				link.setUserId(null);
				link.setGroupId(link.getTask().getExecution().getVariable("customRole").toString());
				
				link.setType(IdentityLinkType.CANDIDATE);
				
			}
			if (link.getTask().getExecution().getVariable("customUser")!=null){
				link.setGroupId(null);
				link.setUserId(link.getTask().getExecution().getVariable("customUser").toString());
				
				link.setType(IdentityLinkType.CANDIDATE);
				
			} 
			
			if (link.getTask().getExecution().getVariable("customAssignee")!=null){
				link.setGroupId(null);
				link.setUserId(link.getTask().getExecution().getVariable("customAssignee").toString());  
				
				link.setType(IdentityLinkType.ASSIGNEE);
				
			}
				
				
			
			
		}
		
		
	}
}
