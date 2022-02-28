(function() {
    var loader = new YAHOO.util.YUILoader({
        onSuccess: function() {
        	/**
        	 * 
        	 */
    	    addAttachment = function(uploadFileTableId) {
                var table = YUD.get(uploadFileTableId);
                var rows = table.rows;
                //less header and footer row
                var index = rows.length - 2;
                var newRow = document.createElement("tr");
                //insert before footer row (last row)
                var footerRow = rows[rows.length - 1];
                YUD.insertBefore(newRow, footerRow);
                newRow.setAttribute("id", "uploadFileRow" + index);
                newRow.setAttribute("class", index % 2 == 0 ? "odd" : "even");
                newRow.innerHTML = 
                	'<td>' +
                	    '<input type="file" id="uploadFile' + (index == 0 ? '' : index) + '" value="" size="70" name="attachmentFiles[' + index + ']"/>' +
                	'</td>' +
                	'<td>' +
                	    '<a title="Remove Attachment" href="javascript:removeAttachment(' + index + ');">' +
                	        '<span class="delete">&#160;</span>' +
                	    '</a>' +
                	'</td>';
            };
            /**
             * 
             */
            removeAttachment = function(index) {
            	var row = YUD.get("uploadFileRow" + index);
            	if (row != null) {
                    row.parentNode.removeChild(row);
            	}
            };
        }
    });
    loader.insert();
})();