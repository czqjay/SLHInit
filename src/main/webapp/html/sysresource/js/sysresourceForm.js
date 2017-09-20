/**
 * @author : shanjizhou
 *
 * @date 2013-06-20
 *
 * */
$(function(){
   sysResourceFormCRUD = {
        saveOrUpdate: function(){
        
            validateCallback($('#sysResourceFormAdd')[0]||$('#sysResourceFormUpdate')[0], function(data){
				DWZ.debug(data);
				if(!data.success){
					$.alert("保存失败<br>"+data.msg?data.msg:'');	
					return false;
				} 
                suntoolkit.destroyDialog();
				sysResourceListCRUD.reloadData();
            })
        },
       sysResourceFormValidateSubmit: function(){
            sysResourceFormSubmit();
        }
    }
});
