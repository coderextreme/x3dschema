var fs = require('fs');
var Ajv = require('ajv');
var selectObjectFromJson = require('./selectObjectFromJson');

var XMLSchemaJsonSchema = JSON.parse(fs.readFileSync('./schemas/XMLSchema.jsonschema').toString());
// var JsonixJsonSchema = JSON.parse(fs.readFileSync('./schemas/Jsonix.jsonschema').toString());
var schemas = {};
var ajv = new Ajv();
	/*
ajv.addFormat("uri", /^(?:[a-z][a-z0-9+\-.]*:)?(?:\/?\/(?:(?:[a-z0-9\-._~!$&'()*+,;=:]|%[0-9a-f]{2})*@)?(?:\[(?:(?:(?:(?:[0-9a-f]{1,4}:){6}|::(?:[0-9a-f]{1,4}:){5}|(?:[0-9a-f]{1,4})?::(?:[0-9a-f]{1,4}:){4}|(?:(?:[0-9a-f]{1,4}:){0,1}[0-9a-f]{1,4})?::(?:[0-9a-f]{1,4}:){3}|(?:(?:[0-9a-f]{1,4}:){0,2}[0-9a-f]{1,4})?::(?:[0-9a-f]{1,4}:){2}|(?:(?:[0-9a-f]{1,4}:){0,3}[0-9a-f]{1,4})?::[0-9a-f]{1,4}:|(?:(?:[0-9a-f]{1,4}:){0,4}[0-9a-f]{1,4})?::)(?:[0-9a-f]{1,4}:[0-9a-f]{1,4}|(?:(?:25[0-5]|2[0-4]\d|[01]?\d\d?)\.){3}(?:25[0-5]|2[0-4]\d|[01]?\d\d?))|(?:(?:[0-9a-f]{1,4}:){0,5}[0-9a-f]{1,4})?::[0-9a-f]{1,4}|(?:(?:[0-9a-f]{1,4}:){0,6}[0-9a-f]{1,4})?::)|[Vv][0-9a-f]+\.[a-z0-9\-._~!$&'()*+,;=:]+)\]|(?:(?:25[0-5]|2[0-4]\d|[01]?\d\d?)\.){3}(?:25[0-5]|2[0-4]\d|[01]?\d\d?)|(?:[a-z0-9\-._~!$&'()*+,;=]|%[0-9a-f]{2})*)(?::\d*)?(?:\/(?:[a-z0-9\-._~!$&'()*+,;=:@]|%[0-9a-f]{2})*)*|\/(?:(?:[a-z0-9\-._~!$&'()*+,;=:@]|%[0-9a-f]{2})+(?:\/(?:[a-z0-9\-._~!$&'()*+,;=:@]|%[0-9a-f]{2})*)*)?|(?:[a-z0-9\-._~!$&'()*+,;=:@]|%[0-9a-f]{2})+(?:\/(?:[a-z0-9\-._~!$&'()*+,;=:@]|%[0-9a-f]{2})*)*)?(?:\?(?:[a-z0-9\-._~!$&'()*+,;=:@\/?]|%[0-9a-f]{2})*)?(?:\#(?:[a-z0-9\-._~!$&'()*+,;=:@\/?]|%[0-9a-f]{2})*)?$/i);
try {
    var metaschema = fs.readFileSync('2020-12-JSONSchema.json');
    var metaschemajson = JSON.parse(metaschema.toString());
    ajv.addMetaSchema(metaschemajson);
} catch (e) {
}
    */
var ajvValidate = {};

var overallversions = ["3.0", "3.1", "3.2", "3.3", "4.0", "4.0H"];
for (var sversion in overallversions) {
	schemas[overallversions[sversion]] = JSON.parse(fs.readFileSync('x3d-'+overallversions[sversion]+'-JSONSchema.json').toString());
	ajvValidate[overallversions[sversion]] = ajv.compile(schemas[overallversions[sversion]]);
}

function testValid(versions, valids, v, file, ajvValidate, hello) {
	// console.log(valids[0], valids[1], v);
	// change v == ? and set ? to 0 for my schema reports and set ? to 1 for roy's schema reports
	if (valids[0] !== valids[1] && v == 1) {
	    var errs = ajvValidate[versions[v]].errors;
	    for (var e in errs) {
		if (e == 0) {
		    console.log(e, 'Ajv '+versions[v]+' Validation failed on', file);
		}
		var error = "";
		error += "\r\n keyword: " + errs[e].keyword + "\r\n";
		var dataPath = errs[e].dataPath.replace(/^\./, "").replace(/[\.\[\]']+/g, " > ").replace(/ >[ \t]*$/, "");

		error += " dataPath: " + dataPath+ "\r\n";
		var selectedObject = selectObjectFromJson(hello, dataPath);
		error += " value: " + JSON.stringify(selectedObject,
			function(k, v) {
			    var v2 = JSON.parse(JSON.stringify(v));
			    if (typeof v2 === 'object') {
				    for (var o in v2) {
					if (typeof v2[o] === 'object') {
						    v2[o] = "|omitted|";
					}
				    }
			    }
			    return v2;
			}) + "\r\n";
		error += " message: " + errs[e].message + "\r\n";
		error += " params: " + JSON.stringify(errs[e].params) + "\r\n";
		error += " file: " + file + "\r\n";
		error += " version: " + versions[v] + "\r\n";
		console.log(error);
	    }
	}
}

function validateFile(file) {
	try {
		var hello = JSON.parse(fs.readFileSync(file).toString());
		var version = hello["X3D"]["@version"];

		var versions = [ version, "4.0H" ];
		var valids = ["NOT YOU", "NOT ME"]
		for (var v in versions) {
    			var valid = ajvValidate[versions[v]](hello);
			valids[v] = valid;
		}
		for (var v in versions) {
			testValid(versions, valids, v, file, ajvValidate, hello);
		}
	} catch (e) {
		console.log('Failed JSON parse', file, e);
	}
}

process.argv.shift();
process.argv.shift();

for (i in process.argv) {
	var file = process.argv[i];
	validateFile(file);
}
process.exit();
