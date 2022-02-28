(function() {
	YAHOO.jms.initAutoCompletes(YUS.query('input[type=text][id^=jobId.]'), '?dispatch=findJobNo&query=');
})();