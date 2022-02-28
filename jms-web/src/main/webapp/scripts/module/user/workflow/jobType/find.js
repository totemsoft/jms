YAHOO.namespace("jms.user");
YAHOO.namespace("jms.user.workflow");

(function() {
//    var loader = new YAHOO.util.YUILoader({
//        onSuccess: function() {
	        //
            // jobTypes table
            if (YAHOO.jms.user.workflow.jobTypesDT) { YAHOO.jms.user.workflow.jobTypesDT.destroy(); YAHOO.jms.user.workflow.jobTypesDT = null; }
            var columnDefs = [
                {key: 'id', label: '', hidden: true, className: 'width0'},
                {key: 'name', label: 'Job Type', sortable: true}
            ];
            var ds = new YAHOO.util.LocalDataSource(YUD.get('jobTypes'), {
                responseType: YAHOO.util.DataSource.TYPE_HTMLTABLE,
                responseSchema: {fields: YAHOO.jms.getFields(columnDefs)}
            });
            var dt = new YAHOO.widget.DataTable('jobTypesDiv', columnDefs, ds, {});
            dt.subscribe('rowMouseoverEvent', dt.onEventHighlightRow);
            dt.subscribe('rowMouseoutEvent', dt.onEventUnhighlightRow);
            dt.subscribe('rowClickEvent', dt.onEventSelectRow);
            var reload_jobTypeActionWorkflowDT = function() {
                var row = YAHOO.jms.getSelectedRow(YAHOO.jms.user.workflow.jobTypesDT);
                var callback = {
                    cache: false,
                    success: function(oResponse) {
                        var jsonData = YAHOO.jms.parseJsonData(oResponse.responseText);
                        YAHOO.jms.dataRefresh(YAHOO.jms.user.workflow.jobTypeActionWorkflowDT, jsonData.actionWorkflowRows);
                    },
                    failure: function(oResponse) {
                    	YAHOO.jms.emptyCallback.failure(oResponse);
                    }
                };
                YAHOO.jms.view(row, 'user/workflowRegister.do?dispatch=viewByJobType', callback);
            };
            dt.subscribe('rowSelectEvent', reload_jobTypeActionWorkflowDT, dt, true);
            YAHOO.jms.user.workflow.jobTypesDT = dt;
            //
            // actionWorkflow table
            if (YAHOO.jms.user.workflow.jobTypeActionWorkflowDT) { YAHOO.jms.user.workflow.jobTypeActionWorkflowDT.destroy(); YAHOO.jms.user.workflow.jobTypeActionWorkflowDT = null; }
            var columnDefs = [
                {key: 'id', label: '', hidden: true, className: 'width0'},
                {key: 'actionCodeId', name: 'actionCodeId', label: '', hidden: true, className: 'width0'},
                {key: 'outcomeId', name: 'outcomeId', label: '', hidden: true, className: 'width0'},
                {key: 'action', label: 'Action', sortable: true},
                {key: 'outcome', label: 'Outcome', sortable: true},
                {key: 'nextAction', label: 'Next Action', sortable: true},
                {key: 'dueDays', label: 'Due Days', className: 'number', sortable: true},
                {key: 'active', name: "active", label: 'Active', className: 'center', sortable: true, formatter: YAHOO.jms.formatCheckbox, disabled: false, onchange: "YAHOO.jms.entity.actionWorkflow.onchangeActive", dataTable: "YAHOO.jms.user.workflow.jobTypeActionWorkflowDT"}
            ];
            var ds = new YAHOO.util.DataSource([], {
                responseType: YAHOO.util.XHRDataSource.TYPE_JSARRAY,
                responseSchema: {fields: YAHOO.jms.getFields(columnDefs)}
            });
            var dt = new YAHOO.widget.DataTable('jobTypeActionWorkflowDiv', columnDefs, ds, {});
            dt.subscribe('rowMouseoverEvent', dt.onEventHighlightRow);
            dt.subscribe('rowMouseoutEvent', dt.onEventUnhighlightRow);
            dt.subscribe('rowClickEvent', dt.onEventSelectRow);
            dt.subscribe('rowDblclickEvent', function() {
                var record = YAHOO.jms.getSelectedRecord(this);
                YAHOO.jms.edit(record, 'setup/editActionWorkflow.do?dispatch=edit', null, null, null, null,
                    {postSubmitCallback: {
                    	cache: false,
                    	success: function(oResponse) {
                    		reload_jobTypeActionWorkflowDT();
                    	},
                    	failure: function(oResponse) {
                    		YAHOO.jms.emptyCallback.failure(oResponse);
                    	}
                    }}
                );
            }, dt, true);
            YAHOO.jms.user.workflow.jobTypeActionWorkflowDT = dt;
//        }
//    });
//    loader.insert();
})();