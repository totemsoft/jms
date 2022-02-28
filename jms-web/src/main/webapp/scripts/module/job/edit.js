YAHOO.namespace("jms.job");

(function() {
//    var loader = new YAHOO.util.YUILoader({
//        onSuccess: function() {
        	var pagetoolsMenuBar = new YAHOO.widget.MenuBar("pagetools", {
                autosubmenudisplay: false,
                lazyload: true
            });
        	pagetoolsMenuBar.render();

            YUE.addListener("editDiary", "click", function(e) {
                YUE.stopEvent(e);
                YAHOO.jms.sendGetRequest("job/editDiary.do?dispatch=editDiary&todoAction=false", null, "New Diary");
            });
            YUE.addListener("editCall", "click", function(e) {
                YUE.stopEvent(e);
                YAHOO.jms.sendGetRequest("job/editCall.do?dispatch=editCall&todoAction=false", null, "New Call");
            });
            YUE.addListener("editLetter", "click", function(e) {
                YUE.stopEvent(e);
                YAHOO.jms.sendGetRequest("job/editLetter.do?dispatch=editLetter&todoAction=false", null, "New Letter");
            });
            YUE.addListener("editEmail", "click", function(e) {
                YUE.stopEvent(e);
                YAHOO.jms.sendGetRequest("job/editEmail.do?dispatch=editEmail&todoAction=false", "scripts/module/file/editEmail.js", "New Email");
            });
            YUE.addListener("editSMS", "click", function(e) {
                YUE.stopEvent(e);
                YAHOO.jms.sendGetRequest("job/editSMS.do?dispatch=editSMS&todoAction=false", null, "New SMS");
            });
            YUE.addListener("editRFI", "click", function(e) {
                YUE.stopEvent(e);
                YAHOO.jms.sendGetRequest("job/editRFI.do?dispatch=editRFI&todoAction=false", null, "New RFI");
            });
            YUE.addListener("editJob", "click", function(e) {
                YUE.stopEvent(e);
                YAHOO.jms.sendGetRequest("job/closeJob.do?dispatch=closeJob&todoAction=false", null, "Close Job");
            });
            //
            if (YAHOO.jms.job.allActionsButton) {
            	YAHOO.jms.job.allActionsButton.destroy();
            	YAHOO.jms.job.allActionsButton = null;
            }
            var allActionsButton = new YAHOO.widget.Button({ 
                type: "checkbox", 
                label: "Show All Actions", 
                id: "allActionsId", 
                container: "allActionsDiv", 
                checked: false});                
            allActionsButton.subscribe("checkedChange", function(oEvent) {
            	var hiddenClassName = "hidden";
            	var checked = oEvent.newValue;
                var nodes = YUS.query('div[id=bd] tr[id^=job.]');
            	for (var i = 0; i < nodes.length; i++) {
            		var node = nodes[i];
                    if (checked) {
                    	if (YUD.hasClass(node, hiddenClassName)) {
                            YUD.removeClass(node, hiddenClassName);
                        }
                    } else {
                    	if (!YUD.hasClass(node, hiddenClassName)) {
                            YUD.addClass(node, hiddenClassName);
                        }
                    }
            	}
            });
            YAHOO.jms.job.allActionsButton = allActionsButton;

            YAHOO.jms.toggleContainers(YUD.get("detailDiv"));

            YUE.addListener("jobDetailsEdit", "click", function(e) {
                YUE.stopEvent(e);
            	var jobId = YUD.get("jobId").value;
            	var requestScript = {"f": "YAHOO.jms.initAutoCompleteFilters", "p": [{"valuesUrl": "findFilter.do?dispatch=userName", "forceSelection": true}]};
                YAHOO.jms.sendGetRequest("job/editJobDetails.do?dispatch=edit&id=" + jobId, requestScript, "Job Details");
            });
            YUE.addListener("jobDocEdit", "click", function(e) {
                YUE.stopEvent(e);
            	var jobId = YUD.get("jobId").value;
                YAHOO.jms.sendGetRequest("job/editJobDoc.do?dispatch=edit&id=" + jobId, null, "Job Document");
            });
            //
            if (YUD.get("jobActionsTree")) {
                var jobActionsTree = new YAHOO.widget.TreeView("jobActionsTree");
                jobActionsTree.render();
                jobActionsTree.subscribe("dblClickEvent", jobActionsTree.onEventEditNode);
                //jobActionsTree.subscribe("clickEvent", jobActionsTree.onEventToggleHighlight);
                //jobActionsTree.setNodesProperty("propagateHighlightUp", true);
                //jobActionsTree.setNodesProperty("propagateHighlightDown", true);
            }
            YUE.addListener("jobActionsToDoEdit", "click", function(e) {
                YUE.stopEvent(e);
            	var jobId = YUD.get("jobId").value;
                YAHOO.jms.sendGetRequest("job/addJobAction.do?dispatch=edit&todoAction=true&id=" + jobId, null, "Add Job Actions To Do");
            });
//        }
//    });
//    loader.insert();
})();