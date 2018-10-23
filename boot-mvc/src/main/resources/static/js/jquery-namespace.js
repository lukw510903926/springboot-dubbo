$.namespace = function(fullNS){
	var nsArray = fullNS.split('.');
	var sEval = "";
	var sNS = "";
	for (var i = 0; i < nsArray.length; i++) {
		if (i != 0)
			sNS += ".";
		sNS += nsArray[i];
		sEval += "if (typeof(" + sNS + ") == 'undefined') " + sNS
				+ " = new Object();"
	}
	if (sEval != "")
		eval(sEval);
}