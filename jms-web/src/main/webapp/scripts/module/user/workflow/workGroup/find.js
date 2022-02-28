YAHOO.namespace("jms.user");
YAHOO.namespace("jms.user.workflow");

(function() {
//    var loader = new YAHOO.util.YUILoader({
//        onSuccess: function() {
	        //
            // workGroups table
            if (YAHOO.jms.user.workflow.workGroupsDT) { YAHOO.jms.user.workflow.workGroupsDT.destroy(); YAHOO.jms.user.workflow.workGroupsDT = null; }
            var columnDefs = [
                {key: 'id', label: '', hidden: true, className: 'width0'},
                {key: 'name', label: 'Work Group', sortable: true}
            ];
            var ds = new YAHOO.util.LocalDataSource(YUD.get('workGroups'), {
                responseType: YAHOO.util.DataSource.TYPE_HTMLTABLE,
                responseSchema: {fields: YAHOO.jms.getFields(columnDefs)}
            });
            var dt = new YAHOO.widget.DataTable('workGroupsDiv', columnDefs, ds, {});
            dt.subscribe('rowMouseoverEvent', dt.onEventHighlightRow);
            dt.subscribe('rowMouseoutEvent', dt.onEventUnhighlightRow);
            dt.subscribe('rowClickEvent', dt.onEventSelectRow);
            dt.subscribe('rowSelectEvent', function() {
                var row = YAHOO.jms.getSelectedRow(this);
                var callback = {
                    cache: false,
                    success: function(oResponse) {
                        var jsonData = YAHOO.jms.parseJsonData(oResponse.responseText);
                        YAHOO.jms.dataRefresh(YAHOO.jms.user.workflow.workGroupActionWorkflowDT, jsonData.actionWorkflowRows);
                        YAHOO.jms.dataRefresh(YAHOO.jms.user.workflow.workGroupSystemFunctionDT, jsonData.systemFunctionRows);
                    },
                    failure: function(oResponse) {
                    	YAHOO.jms.emptyCallback.failure(oResponse);
                    }
                };
                YAHOO.jms.view(row, 'user/workflowRegister.do?dispatch=viewByWorkGroup', callback);
            }, dt, true);
            YAHOO.jms.user.workflow.workGroupsDT = dt;
            //
            // actionWorkflow table
            if (YAHOO.jms.user.workflow.workGroupActionWorkflowDT) { YAHOO.jms.user.workflow.workGroupActionWorkflowDT.destroy(); YAHOO.jms.user.workflow.workGroupActionWorkflowDT = null; }
            var columnDefs = [
                {key: 'id', label: '', hidden: true, className: 'width0'},
                {key: 'outcome', label: 'Outcome', sortable: true},
                {key: 'action', label: 'Action', sortable: true},
                {key: 'nextAction', label: 'Next Action', sortable: true},
                {key: 'dueDays', label: 'Due Days', sortable: true}
            ];
            var ds = new YAHOO.util.DataSource([], {
                responseType: YAHOO.util.XHRDataSource.TYPE_JSARRAY,
                responseSchema: {fields: YAHOO.jms.getFields(columnDefs)}
            });
            var dt = new YAHOO.widget.DataTable('workGroupActionWorkflowDiv', columnDefs, ds, {});
            dt.subscribe('rowMouseoverEvent', dt.onEventHighlightRow);
            dt.subscribe('rowMouseoutEvent', dt.onEventUnhighlightRow);
            dt.subscribe('rowClickEvent', dt.onEventSelectRow);
            YAHOO.jms.user.workflow.workGroupActionWorkflowDT = dt;
            //
            // systemFunction table
            if (YAHOO.jms.user.workflow.workGroupSystemFunctionDT) { YAHOO.jms.user.workflow.workGroupSystemFunctionDT.destroy(); YAHOO.jms.user.workflow.workGroupSystemFunctionDT = null; }
            var columnDefs = [
                {key: 'id', label: '', hidden: true, className: 'width0'},
                {key: 'module', label: 'Module', sortable: true},
                {key: 'name', label: 'Name', sortable: true},
                {key: 'description', label: 'Description', sortable: true}
            ];
            var ds = new YAHOO.util.DataSource([], {
                responseType: YAHOO.util.XHRDataSource.TYPE_JSARRAY,
                responseSchema: {fields: YAHOO.jms.getFields(columnDefs)}
            });
            var dt = new YAHOO.widget.DataTable('workGroupSystemFunctionDiv', columnDefs, ds, {});
            dt.subscribe('rowMouseoverEvent', dt.onEventHighlightRow);
            dt.subscribe('rowMouseoutEvent', dt.onEventUnhighlightRow);
            dt.subscribe('rowClickEvent', dt.onEventSelectRow);
            YAHOO.jms.user.workflow.workGroupSystemFunctionDT = dt;
//        }
//    });
//    loader.insert();
})();