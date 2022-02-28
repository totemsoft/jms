YAHOO.namespace('jms.table');

//// Returns a request string for consumption by the DataSource
//var generateRequest = function(startIndex, sortKey, dir, results) {
//    startIndex = startIndex || 0;
//    sortKey = sortKey || 'id';
//    //converts from DataTable format 'yui-dt-[dir]' to server value '[dir]'
//    dir = dir ? dir.substring(7) : 'asc'; 
//    results = results || 10;
//    return 'results=' + results + '&startIndex=' + startIndex + '&sort=' + sortKey + '&dir=' + dir;
//};

/**
 * This method is passed into the DataTable's 'generateRequest' configuration
 * setting overriding the default generateRequest function. This function puts
 * together a query string which is passed to the DataSource each time a new
 * set of data is requested. All of the custom sorting and filtering options
 * added in by this script are gathered up here and inserted into the
 * query string.
 * @param {Object} oState
 * @param {Object} oSelf
 * These parameters are explained in detail in DataTable's API
 * documentation. It's important to note that oState contains
 * a reference to the paginator and the pagination state and
 * the column sorting state as well.
 */
YAHOO.jms.table.requestBuilder = function (oState, oSelf) {
    oState = oState || {pagination: null, sortedBy: null};
    var columns = oSelf.getColumnSet();
    var column = columns.keys[1];
    var sort = oState.sortedBy ? oState.sortedBy.key : column.getKey();
    var dir = (oState.sortedBy && oState.sortedBy.dir === YAHOO.widget.DataTable.CLASS_DESC) ? 'desc' : 'asc';
    var startIndex = (oState.pagination && oState.pagination.recordOffset != 0) ? oState.pagination.recordOffset : 0;
    var pageSize = (oState.pagination && oState.pagination.rowsPerPage != 0) ? oState.pagination.rowsPerPage : 100;
    //TODO: set proper form (hardcode for now)
    var form = YAHOO.util.Dom.get(YAHOO.jms.form);
    if (form) {
        if (form.sort) form.sort.value = sort;
        if (form.dir) form.dir.value = dir;
        if (form.startIndex) form.startIndex.value = startIndex;
        if (form.pageSize) form.pageSize.value = pageSize;
        YAHOO.util.Connect.setForm(form);
    }
};

/**
 * This method is used to fire off a request for new data for the DataTable from the DataSource. 
 * The new state of the DataTable, after the request for new data, will be determined here.
 * @param {Boolean} resetRecordOffset
 */
YAHOO.jms.table.filter = function(dataTable, resetRecordOffset) {
    var oState = dataTable.getState();
    // We don't always want to reset the recordOffset.
    // if we want the Paginator to be set to the first page, pass in a value of true to this method.
    // otherwise, pass in false and the paginator will remain at the page it was set at before.
    if (resetRecordOffset && oState.pagination) {
        oState.pagination.recordOffset = 0;
    }
    //use onDataReturnSetRows because that method will clear out the old data in the DataTable, making way for the new data.
    var oCallback = {
        success: dataTable.onDataReturnSetRows,
        failure: dataTable.onDataReturnSetRows,
        argument: oState,
        scope: dataTable
    };
    // Generate a query string
    var request = YAHOO.jms.table.requestBuilder(oState, dataTable);
    // Fire off a request for new data.
    dataTable.getDataSource().sendRequest(request, oCallback);
};

/**
 * id  <string|HTMLElement>
 *     The id of the element, or the element itself that the table will be inserted into.
 *     Existing markup in this element, if valid, will be used to build the table.
 */
YAHOO.jms.table.create = function(id, data) {

    // The standard link and email formatters use the same value both for the display text and the underlying href attribute for the link
    YAHOO.widget.DataTable.formatLink = function(elLiner, oRecord, oColumn, oData) {
        //if (!oData) return;
        if (oData && oData.indexOf('javascript:') == 0) {
            var idx = oData.indexOf(';');
            elLiner.innerHTML = '<a href="' + oData.substr(0, idx + 1) + '">' + oData.substr(idx + 1) + '</a>';
        } else {
            elLiner.innerHTML = '<a href="' + oData + '" target="_blank">' + oData + '</a>';
        }
    };
    // custom class formatter
    YAHOO.widget.DataTable.formatClass = function(elLiner, oRecord, oColumn, oData) {
        if (oData == true) {
            elLiner.innerHTML = '<em class="' + oColumn.key + '"/>';
        }
    };

    //
    var hasLink = false;
    var hasEditable = false;
    // Update the Column Definitions (pass as string in find.jsp)
    var columns = data.columns;
    for (var c = 0; c < columns.length; c++) {
        var column = columns[c];
        if (column.formatter == 'YAHOO.widget.DataTable.formatLink') {
            column.formatter = YAHOO.widget.DataTable.formatLink;
        	hasLink = true;
        } else  if (column.formatter == 'YAHOO.widget.DataTable.formatEmail') {
            column.formatter = YAHOO.widget.DataTable.formatEmail;
        	hasLink = true;
        } else if (column.formatter == 'YAHOO.widget.DataTable.formatCheckbox') {
            column.formatter = YAHOO.widget.DataTable.formatCheckbox;
        } else  if (column.formatter == 'YAHOO.widget.DataTable.formatRadio') {
            column.formatter = YAHOO.widget.DataTable.formatRadio;
        } else  if (column.formatter == 'YAHOO.widget.DataTable.formatNumber') {
            column.formatter = YAHOO.widget.DataTable.formatNumber;
        } else  if (column.formatter == 'YAHOO.widget.DataTable.formatClass') {
            column.formatter = YAHOO.widget.DataTable.formatClass;
        }
        if (column.editor) {
            if (column.editor == 'YAHOO.widget.DropdownCellEditor') {
                column.editor = new YAHOO.widget.DropdownCellEditor({dropdownOptions:column.dropdownOptions,disableBtns:false});
            } else if (column.editor == 'YAHOO.widget.TextboxCellEditor') {
                column.editor = new YAHOO.widget.TextboxCellEditor({disableBtns:false});
            } else if (column.editor == 'YAHOO.widget.TextareaCellEditor') {
                column.editor = new YAHOO.widget.TextareaCellEditor({disableBtns:false});
            }
            column.editor.key = column.key;
            hasEditable = true;
        }
    }

    // Create the DataSource
    var dataSource = new YAHOO.util.XHRDataSource(data.actions.find, {
        //connXhrMode: 'cancelStaleRequests',
        connMethodPost: true //POST
    });
    dataSource.responseType = YAHOO.util.XHRDataSource.TYPE_JSON;
    dataSource.responseSchema = {
        resultsList: 'records',
        fields: data.fields,
        metaFields: {
            totalRecords: 'paginator.totalRecords',
            paginationRecordOffset: 'paginator.startIndex',
            paginationRowsPerPage: 'paginator.pageSize',
            sortKey: 'sortedBy.key',
            sortDir: 'sortedBy.dir'
        }
    };

    // Create the Paginator (optional)
    var paginator = null;
    if (data.paginator) {
        paginator = new YAHOO.widget.Paginator({
            containers: 'paginatorDiv',
            //template: '{PageLinks} Show {RowsPerPageDropdown} per page',
            template: '{PreviousPageLink} {CurrentPageReport} {NextPageLink} {RowsPerPageDropdown}',
            pageReportTemplate: 'Showing items {startIndex} - {endIndex} of {totalRecords}',
            rowsPerPageOptions: [10,25,50,100,200,500]
        });
    }

    // Define a custom row formatter function
    var rowFormatter = function(elTr, oRecord) {
        var formatRowClass = oRecord.getData('formatRowClass');
        if (formatRowClass) {
            YAHOO.util.Dom.addClass(elTr, formatRowClass);
        }
        return true;
    };

    // DataTable configuration
    var configs = {
        //When dynamicData is enabled, sorting or paginating will trigger a DataSource request for new data to reflect the state. By default, the request is formatted with the following syntax:
        //sort={SortColumnKey}&dir={SortColumnDir}&startIndex={PaginationStartIndex}&results={PaginationRowsPerPage}
        dynamicData: true,
        //sortedBy : {key:data.columns[0].key, dir:YAHOO.widget.DataTable.CLASS_ASC}, // Sets UI initial sort arrow 
        sortedBy : {key:data.sortedBy.key, dir:data.sortedBy.dir}, // Sets UI initial sort arrow 
        initialLoad: false,
        //initialRequest: '1=1',
        scrollable: false,
        draggableColumns: true,
        //height: dtH + 'px', width: w + 'px',
        paginator: paginator,
        // This configuration item is what builds the query string passed to the DataSource.
        generateRequest: YAHOO.jms.table.requestBuilder,
        // Enable the row formatter
        formatRow: rowFormatter
    };

    // Create table
    var dataTable = new YAHOO.widget.DataTable(id, columns, dataSource, configs);
    //var dataTable = new YAHOO.widget.ScrollingDataTable(id, columns, dataSource, configs);
    // Show loading message while page is being rendered
    dataTable.set('MSG_EMPTY', 'This selection contains no data');
    dataTable.showTableMessage(dataTable.get('MSG_LOADING'), YAHOO.widget.DataTable.CLASS_LOADING);

/*
    // Define an event handler that scoops up the totalRecords which we sent as part of the JSON data.
    // This is then used to tell the paginator the total records.
    // This happens after each time the DataTable is updated with new data.
    dataTable.handleDataReturnPayload = function(oRequest, oResponse, oPayload) {
        var meta = oResponse.meta;
        // The payload object usually represents DataTable's state values, including:
        oPayload.totalRecords = meta.totalRecords;
        // oPayload.pagination.rowsPerPage = [number of rows per page]
        // oPayload.pagination.recordOffset = [index of first record of current page]
        // oPayload.sortedBy.key =  [key of currently sorted column]
        // oPayload.sortedBy.dir = [direction of currently sorted column]
        return oPayload;
    }
*/
    // Update payload data on the fly for tight integration with latest values from server 
    dataTable.doBeforeLoadData = function(oRequest, oResponse, oPayload) {
        var meta = oResponse.meta;
        var totalRecords = meta.totalRecords;// || oPayload.totalRecords;
        var pageSize = meta.paginationRowsPerPage || 10;
        var startIndex = meta.paginationRecordOffset || 0;
        var sort = meta.sortKey || 'id';
        var dir = meta.sortDir ? meta.sortDir : 'asc';
        //update payload
        oPayload.totalRecords = totalRecords;
        oPayload.pagination = {
            rowsPerPage: pageSize,
            recordOffset: startIndex
        };
        oPayload.sortedBy = {
            key: sort,
            dir: 'yui-dt-' + dir // converts from server value '[dir]' to DataTable format 'yui-dt-[dir]'
        };
        return true;
    };

    // Subscribe to events for row (global)
    dataTable.subscribe('rowMouseoverEvent', dataTable.onEventHighlightRow);
    dataTable.subscribe('rowMouseoutEvent', dataTable.onEventUnhighlightRow);
    dataTable.subscribe('rowClickEvent', dataTable.onEventSelectRow);
    // Subscribe to events for cell/row selection
    if (hasEditable) {
        dataTable.subscribe('cellMouseoverEvent', function(oArgs) {
            var elCell = oArgs.target;
            if (YAHOO.util.Dom.hasClass(elCell, 'yui-dt-editable')) {
                this.highlightCell(elCell);
            }
        });
        dataTable.subscribe('cellMouseoutEvent', dataTable.onEventUnhighlightCell);
        dataTable.subscribe('cellClickEvent', dataTable.onEventShowCellEditor);
    } else if (hasLink) {
        dataTable.subscribe('cellMouseoverEvent', dataTable.onEventHighlightCell);
        dataTable.subscribe('cellMouseoutEvent', dataTable.onEventUnhighlightCell);
        dataTable.subscribe('cellClickEvent', dataTable.onEventSelectCell);
        //NB: define cell specific navigation in page javascript
        //dataTable.subscribe('cellSelectEvent', function(oArgs) {
        //    var key = oArgs.key;
        //    if (key != 'link' && key != 'email') {
        //        var row = oArgs.record._oData;
        //    	YAHOO.jms.navigate(data.actions.view + row.id, '');
        //    }
        //});
    } else {
        dataTable.subscribe('rowSelectEvent', function() { //rowDblclickEvent
            if (!data.actions.expand) {
                return;
            }
            var row = this.getRecordSet().getRecord(this.getSelectedRows()[0])._oData;
            YAHOO.jms.navigate(data.actions.view + row.id, '');
        });
    }

    // Initial load
    YAHOO.jms.table.filter(dataTable, true);

    return dataTable;
};