var fs = require('fs');
const Ajv2020 = require("ajv/dist/2020");
const ajv = new Ajv2020({ strict: false });
const addFormats = require("ajv-formats");
var selectObjectFromJson = require('./selectObjectFromJson');

addFormats(ajv);

var schema = JSON.parse(fs.readFileSync('x3d-4.0-JSONSchema.json').toString());
// var schema = JSON.parse(fs.readFileSync('X3dXml4.0SchemaConvertedToJson2020-12Schema.json').toString());
function validateFile(file) {
	try {
	    var json = JSON.parse(fs.readFileSync(file).toString());
	    var version = "4.0";
	    try {
		var valid = ajv.validate(schema, json);
		if (ajv.errors !== null) {
		        var errs = ajv.errors;
			for (var e in errs) {
				if (e == 0) {
				    console.log(e, 'Ajv '+version+' Validation failed on', file);
				}
				var error = "";
				error += "\r\n keyword: " + errs[e].keyword + "\r\n";
				var instancePath = errs[e].instancePath.replace(/^\./, "").replace(/[\.\[\]']+/g, " > ").replace(/ >[ \t]*$/, "");

				error += " instancePath: " + instancePath+ "\r\n";
				var selectedObject = selectObjectFromJson(json, instancePath);
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
				error += " version: " + version + "\r\n";
				console.log(error);
			}
		} else {
			console.log('Successful', file);
		}
	    } catch (e) {
		console.log('Failed validation', file, e);
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
