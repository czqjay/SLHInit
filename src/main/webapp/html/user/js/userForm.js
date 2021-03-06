/**
 * @author : 梁汝健
 * @date : 2014-03-10
 * @Description：管理用户记录增、改、form提交时处理操作js
 * */
$(function(){
    userFormCRUD = {
        saveOrUpdate: function(){
            validateCallback($('#userFormAdd')[0]||$('#userFormUpdate')[0], function(data){
				DWZ.debug(data);
	
				if(!data.success){
					$.alert("保存失败<br>"+data.msg?data.msg:'');	
					return false;
				}else{
				 $.alert('保存成功!');	
				}
                suntoolkit.destroyDialog();
				userListCRUD.reloadData({accountName:$('#accountName').val(),userName:$('#userName').val(),phone:$('#phone').val(),beginDateStrat:$('#beginDateStrat').val(),beginDateEnd:$('#beginDateEnd').val(),datatype:$('#datatype').val()});
            })
        },
        userFormValidateSubmit: function(){
            userFormSubmit();
        }
    }
});
