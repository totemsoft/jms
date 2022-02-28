YAHOO.namespace('jms.incidentMpf');

// incidentMpf scope variables
YAHOO.jms.incidentMpf = {
    // generic table - one per module
    dataTable: [],
    // generic toolbar - one per module
    toolbar: [],
    // tab0 :: new
    columnDefsNew: [
        {key:'id',className:'width0',hidden:true},
        {key:'fireCallNo',label:'Fire Call<br/>No',className:'center middle',sortable:false,resizeable:false},
        {key:'injuryNo',label:'Injury<br/>No',className:'center middle',sortable:false,resizeable:false},
        {key:'region',label:'Region',className:'center middle',sortable:false,resizeable:false},
        {key:'incidentDate',label:'Incident<br/>Date',className:'center middle',sortable:false,resizeable:false},
        {key:'name',label:'Name',className:'center middle',sortable:false,resizeable:false},
        {key:'dateOfBirth',label:'Date Of<br/>Birth',className:'center middle',sortable:false,resizeable:false},
        {key:'address',label:'Address',className:'center middle',sortable:false,resizeable:false},
        {key:'injury',label:'Injury',className:'center middle',sortable:false,resizeable:false},
        {key:'multipleInjury',label:'Multiple<br/>Injury',className:'center middle',sortable:false,resizeable:false},
        {key:'alcoholLevel',label:'Alcohol<br/>Level',className:'center middle',sortable:false,resizeable:false},
        {key:'drugs',label:'Drugs',className:'center middle',sortable:false,resizeable:false},
        {key:'treatedAt',label:'Treated<br/>At',className:'center middle',sortable:false,resizeable:false},
        {key:'selected',label:'<input type="checkbox" id="select.all" title="Select All" />',className:'center middle',formatter:'checkbox'}
    ],
    createDataTableNew: function(oElem, idx) {
        YAHOO.util.Event.onContentReady(oElem, function() {
            if (YAHOO.jms.incidentMpf.dataTable[idx]) {
                return;
            }
            var form = YAHOO.util.Dom.get(YAHOO.jms.form + idx);
            var dataTable = YAHOO.jms.createDynamicDataTable(oElem, 'incidentMpf.do?dispatch=findInjury', YAHOO.jms.incidentMpf.columnDefsNew, form);
            dataTable.subscribe('rowMouseoverEvent', dataTable.onEventHighlightRow);
            dataTable.subscribe('rowMouseoutEvent', dataTable.onEventUnhighlightRow);
            dataTable.subscribe('rowClickEvent', dataTable.onEventSelectRow);
            dataTable.subscribe('rowDblclickEvent', function() {
                var row = this.getRecordSet().getRecord(this.getSelectedRows()[idx])._oData;
                YAHOO.jms.incidentMpf.edit(row.id);
            });
            // selectAll event handler
            var selectAll = YAHOO.util.Dom.get('select.all');
            YAHOO.util.Event.addListener(selectAll, 'click', function(e) {
                var selectedNodes = YAHOO.util.Selector.query('div[id=' + oElem + '] input[id^=selected.][type=checkbox]:not([disabled])');
                for (var i = 0; i < selectedNodes.length; i++) {
                    var selectedNode = selectedNodes[i];
                    selectedNode.checked = this.checked;
                }
            });
            YAHOO.jms.incidentMpf.dataTable[idx] = dataTable;
        });
    },
    createToolbarNew: function(oElem, idx) {
        YAHOO.util.Event.onContentReady(oElem, function() {
            if (YAHOO.jms.incidentMpf.toolbar[idx]) {
                return;
            }
            var toolbar = new YAHOO.widget.Toolbar(oElem, {buttons: []});
            toolbar.addButton({id: 'tb_filter' + idx, type: 'push', label: 'Search', value: 'filter'});
            toolbar.addSeparator();
            toolbar.addButton({id: 'tb_export' + idx, type: 'push', label: 'Export', value: 'export'});
            toolbar.addSeparator();
            toolbar.addButton({id: 'tb_import' + idx, type: 'push', label: 'Import', value: 'import'});
            toolbar.addSeparator();
            toolbar.on('buttonClick', function(e) {
                if (e.button.id == 'tb_filter' + idx) {
                    YAHOO.jms.filterDataTable(YAHOO.jms.incidentMpf.dataTable[idx]);
                } else if (e.button.id == 'tb_import' + idx) {
                    var root = YAHOO.util.Dom.get('dataDiv' + idx);
                    var nodes = YAHOO.util.Selector.query('input[type=checkbox][id^=selected.]:checked', root);
                    if (confirm('Do you want to import ' + nodes.length + ' records?')) {
                        var postdata = {ids:[]};
                        for (var i = 0; i < nodes.length; i++) {
                            postdata.ids.push(nodes[i].value); // composite key "id1,id2"
                        }
                        var callback = {
                            cache:false,
                            success: function(oResponse) {
                                YAHOO.jms.alert('success', oResponse);
                                var data = YAHOO.jms.parseJsonData(oResponse.responseText);
                                
                            },
                            failure: function(oResponse) {
                                YAHOO.jms.emptyCallback.failure(oResponse);
                            }
                        };
                        YAHOO.jms.sendPostRequest(null, 'incidentMpf.do?dispatch=importInjury', callback, postdata);
                    }
                } else if (e.button.id == 'tb_export' + idx) {
                    var form = YAHOO.util.Dom.get(YAHOO.jms.form + idx);
                    YAHOO.jms.sendDownloadRequest('document/export.do?dispatch=action&exportName=report&', form);
                }
            });
            YAHOO.jms.incidentMpf.toolbar[idx] = toolbar;
        });
    },
    initNew: function() {
        this.createToolbarNew('toolbarDiv0', 0);
        this.createDataTableNew('dataDiv0', 0);
    },
    // tab1 :: feedback
    initFeedback: function() {
        //this.createToolbarFeedback('toolbarDiv1', 1);
        //this.createDataTableFeedback('dataDiv1', 1);
    },
    // tab2 :: review
    createDataTableReview: function(oElem, idx) {
        YAHOO.util.Event.onContentReady(oElem, function() {
            if (YAHOO.jms.incidentMpf.dataTable[idx]) {
                return;
            }
            var form = YAHOO.util.Dom.get(YAHOO.jms.form + idx);
            var dataTable = YAHOO.jms.createDynamicDataTable(oElem, 'incidentMpf.do?dispatch=findInjuryView', YAHOO.jms.incidentMpf.columnDefsNew, form);
            dataTable.subscribe('rowMouseoverEvent', dataTable.onEventHighlightRow);
            dataTable.subscribe('rowMouseoutEvent', dataTable.onEventUnhighlightRow);
            dataTable.subscribe('rowClickEvent', dataTable.onEventSelectRow);
            dataTable.subscribe('rowDblclickEvent', function() {
                var row = this.getRecordSet().getRecord(this.getSelectedRows()[0])._oData;
                YAHOO.jms.incidentMpf.edit(row.id);
            });
            // selectAll event handler
            var selectAll = YAHOO.util.Dom.get('select.all');
            YAHOO.util.Event.addListener(selectAll, 'click', function(e) {
                var selectedNodes = YAHOO.util.Selector.query('div[id=' + oElem + '] input[id^=selected.][type=checkbox]:not([disabled])');
                for (var i = 0; i < selectedNodes.length; i++) {
                    var selectedNode = selectedNodes[i];
                    selectedNode.checked = this.checked;
                }
            });
            YAHOO.jms.incidentMpf.dataTable[idx] = dataTable;
        });
    },
    createToolbarReview: function(oElem, idx) {
        YAHOO.util.Event.onContentReady(oElem, function() {
            if (YAHOO.jms.incidentMpf.toolbar[idx]) {
                return;
            }
            var toolbar = new YAHOO.widget.Toolbar(oElem, {buttons: []});
            toolbar.addButton({id: 'tb_filter' + idx, type: 'push', label: 'Search', value: 'filter'});
            toolbar.addSeparator();
            toolbar.addButton({id: 'tb_export' + idx, type: 'push', label: 'Export', value: 'export'});
            toolbar.addSeparator();
            toolbar.on('buttonClick', function(e) {
                if (e.button.id == 'tb_filter' + idx) {
                    YAHOO.jms.filterDataTable(YAHOO.jms.incidentMpf.dataTable[idx]);
                } else if (e.button.id == 'tb_export' + idx) {
                    var form = YAHOO.util.Dom.get(YAHOO.jms.form + idx);
                    YAHOO.jms.sendDownloadRequest('document/export.do?dispatch=action&exportName=report&', form);
                }
            });
            YAHOO.jms.incidentMpf.toolbar[idx] = toolbar;
        });
    },
    initReview: function() {
        this.createToolbarReview('toolbarDiv2', 2);
        this.createDataTableReview('dataDiv2', 2);
    }
};

(function() {
    var loader = new YAHOO.util.YUILoader({
        onSuccess: function() {
            YAHOO.jms.createTabView('tabViewDiv', {
                onActiveIndexChange: function(e) {
                    switch (e.newValue) {
                        case 0: YAHOO.jms.incidentMpf.initNew(); break;
                        case 1: YAHOO.jms.incidentMpf.initFeedback(); break;
                        case 2: YAHOO.jms.incidentMpf.initReview(); break;
                    }
                }
            });
            YAHOO.jms.onAvailableTab('tab0', 0,
                {dataSrc: 'incidentMpf.do?dispatch=new'});
            YAHOO.jms.onAvailableTab('tab1', 1,
                {dataSrc: 'incidentMpf.do?dispatch=feedback'});
            YAHOO.jms.onAvailableTab('tab2', 2,
                {dataSrc: 'incidentMpf.do?dispatch=review'});
        }
    });

    //Have loader insert only the JS files.
    //loader.insert({}, 'js');
    loader.insert();
})();