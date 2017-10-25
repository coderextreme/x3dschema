var fs = require('fs');
var Ajv = require('ajv');
// var jjv = require('jjv');
var imjv = require('is-my-json-valid');
var selectObjectFromJson = require('./selectObjectFromJson');

var XMLSchemaJsonSchema = JSON.parse(fs.readFileSync('./schemas/XMLSchema.jsonschema').toString());
// var JsonixJsonSchema = JSON.parse(fs.readFileSync('./schemas/Jsonix.jsonschema').toString());
var schemas = {};
var ajv = new Ajv({ allErrors:true, verbose:true});
ajv.addFormat("uri", /^(?:[a-z][a-z0-9+\-.]*:)?(?:\/?\/(?:(?:[a-z0-9\-._~!$&'()*+,;=:]|%[0-9a-f]{2})*@)?(?:\[(?:(?:(?:(?:[0-9a-f]{1,4}:){6}|::(?:[0-9a-f]{1,4}:){5}|(?:[0-9a-f]{1,4})?::(?:[0-9a-f]{1,4}:){4}|(?:(?:[0-9a-f]{1,4}:){0,1}[0-9a-f]{1,4})?::(?:[0-9a-f]{1,4}:){3}|(?:(?:[0-9a-f]{1,4}:){0,2}[0-9a-f]{1,4})?::(?:[0-9a-f]{1,4}:){2}|(?:(?:[0-9a-f]{1,4}:){0,3}[0-9a-f]{1,4})?::[0-9a-f]{1,4}:|(?:(?:[0-9a-f]{1,4}:){0,4}[0-9a-f]{1,4})?::)(?:[0-9a-f]{1,4}:[0-9a-f]{1,4}|(?:(?:25[0-5]|2[0-4]\d|[01]?\d\d?)\.){3}(?:25[0-5]|2[0-4]\d|[01]?\d\d?))|(?:(?:[0-9a-f]{1,4}:){0,5}[0-9a-f]{1,4})?::[0-9a-f]{1,4}|(?:(?:[0-9a-f]{1,4}:){0,6}[0-9a-f]{1,4})?::)|[Vv][0-9a-f]+\.[a-z0-9\-._~!$&'()*+,;=:]+)\]|(?:(?:25[0-5]|2[0-4]\d|[01]?\d\d?)\.){3}(?:25[0-5]|2[0-4]\d|[01]?\d\d?)|(?:[a-z0-9\-._~!$&'()*+,;=]|%[0-9a-f]{2})*)(?::\d*)?(?:\/(?:[a-z0-9\-._~!$&'()*+,;=:@]|%[0-9a-f]{2})*)*|\/(?:(?:[a-z0-9\-._~!$&'()*+,;=:@]|%[0-9a-f]{2})+(?:\/(?:[a-z0-9\-._~!$&'()*+,;=:@]|%[0-9a-f]{2})*)*)?|(?:[a-z0-9\-._~!$&'()*+,;=:@]|%[0-9a-f]{2})+(?:\/(?:[a-z0-9\-._~!$&'()*+,;=:@]|%[0-9a-f]{2})*)*)?(?:\?(?:[a-z0-9\-._~!$&'()*+,;=:@\/?]|%[0-9a-f]{2})*)?(?:\#(?:[a-z0-9\-._~!$&'()*+,;=:@\/?]|%[0-9a-f]{2})*)?$/i);
try {
    var metaschema = fs.readFileSync('json-schema-draft-04.json');
    var metaschemajson = JSON.parse(metaschema.toString());
    ajv.addMetaSchema(metaschemajson);
} catch (e) {
}
var ajvValidate = {};
var imjvValidate = {};
// var jjvEnv = {};

var overallversions = ["3.0", "3.1", "3.2", "3.3", "3.4", "6.0"];
for (var sversion in overallversions) {
	schemas[overallversions[sversion]] = JSON.parse(fs.readFileSync('x3d-'+overallversions[sversion]+'-JSONSchema.json').toString());
	ajvValidate[overallversions[sversion]] = ajv.compile(schemas[overallversions[sversion]]);
	/*
	jjvEnv[overallversions[sversion]] = jjv();
	jjvEnv[overallversions[sversion]].addSchema('x3djson', schemas[overallversions[sversion]]);
	jjvEnv[overallversions[sversion]].addFormat('uri', function (v) {
	    return (/^(?:[a-z][a-z0-9+\-.]*:)?(?:\/?\/(?:(?:[a-z0-9\-._~!$&'()*+,;=:]|%[0-9a-f]{2})*@)?(?:\[(?:(?:(?:(?:[0-9a-f]{1,4}:){6}|::(?:[0-9a-f]{1,4}:){5}|(?:[0-9a-f]{1,4})?::(?:[0-9a-f]{1,4}:){4}|(?:(?:[0-9a-f]{1,4}:){0,1}[0-9a-f]{1,4})?::(?:[0-9a-f]{1,4}:){3}|(?:(?:[0-9a-f]{1,4}:){0,2}[0-9a-f]{1,4})?::(?:[0-9a-f]{1,4}:){2}|(?:(?:[0-9a-f]{1,4}:){0,3}[0-9a-f]{1,4})?::[0-9a-f]{1,4}:|(?:(?:[0-9a-f]{1,4}:){0,4}[0-9a-f]{1,4})?::)(?:[0-9a-f]{1,4}:[0-9a-f]{1,4}|(?:(?:25[0-5]|2[0-4]\d|[01]?\d\d?)\.){3}(?:25[0-5]|2[0-4]\d|[01]?\d\d?))|(?:(?:[0-9a-f]{1,4}:){0,5}[0-9a-f]{1,4})?::[0-9a-f]{1,4}|(?:(?:[0-9a-f]{1,4}:){0,6}[0-9a-f]{1,4})?::)|[Vv][0-9a-f]+\.[a-z0-9\-._~!$&'()*+,;=:]+)\]|(?:(?:25[0-5]|2[0-4]\d|[01]?\d\d?)\.){3}(?:25[0-5]|2[0-4]\d|[01]?\d\d?)|(?:[a-z0-9\-._~!$&'()*+,;=]|%[0-9a-f]{2})*)(?::\d*)?(?:\/(?:[a-z0-9\-._~!$&'()*+,;=:@]|%[0-9a-f]{2})*)*|\/(?:(?:[a-z0-9\-._~!$&'()*+,;=:@]|%[0-9a-f]{2})+(?:\/(?:[a-z0-9\-._~!$&'()*+,;=:@]|%[0-9a-f]{2})*)*)?|(?:[a-z0-9\-._~!$&'()*+,;=:@]|%[0-9a-f]{2})+(?:\/(?:[a-z0-9\-._~!$&'()*+,;=:@]|%[0-9a-f]{2})*)*)?(?:\?(?:[a-z0-9\-._~!$&'()*+,;=:@\/?]|%[0-9a-f]{2})*)?(?:\#(?:[a-z0-9\-._~!$&'()*+,;=:@\/?]|%[0-9a-f]{2})*)?$/i).test(v);
	});
	*/
	imjvValidate[overallversions[sversion]] = imjv(schemas[overallversions[sversion]], {
	  formats: {
	    'uri': /^(?:[a-z][a-z0-9+\-.]*:)?(?:\/?\/(?:(?:[a-z0-9\-._~!$&'()*+,;=:]|%[0-9a-f]{2})*@)?(?:\[(?:(?:(?:(?:[0-9a-f]{1,4}:){6}|::(?:[0-9a-f]{1,4}:){5}|(?:[0-9a-f]{1,4})?::(?:[0-9a-f]{1,4}:){4}|(?:(?:[0-9a-f]{1,4}:){0,1}[0-9a-f]{1,4})?::(?:[0-9a-f]{1,4}:){3}|(?:(?:[0-9a-f]{1,4}:){0,2}[0-9a-f]{1,4})?::(?:[0-9a-f]{1,4}:){2}|(?:(?:[0-9a-f]{1,4}:){0,3}[0-9a-f]{1,4})?::[0-9a-f]{1,4}:|(?:(?:[0-9a-f]{1,4}:){0,4}[0-9a-f]{1,4})?::)(?:[0-9a-f]{1,4}:[0-9a-f]{1,4}|(?:(?:25[0-5]|2[0-4]\d|[01]?\d\d?)\.){3}(?:25[0-5]|2[0-4]\d|[01]?\d\d?))|(?:(?:[0-9a-f]{1,4}:){0,5}[0-9a-f]{1,4})?::[0-9a-f]{1,4}|(?:(?:[0-9a-f]{1,4}:){0,6}[0-9a-f]{1,4})?::)|[Vv][0-9a-f]+\.[a-z0-9\-._~!$&'()*+,;=:]+)\]|(?:(?:25[0-5]|2[0-4]\d|[01]?\d\d?)\.){3}(?:25[0-5]|2[0-4]\d|[01]?\d\d?)|(?:[a-z0-9\-._~!$&'()*+,;=]|%[0-9a-f]{2})*)(?::\d*)?(?:\/(?:[a-z0-9\-._~!$&'()*+,;=:@]|%[0-9a-f]{2})*)*|\/(?:(?:[a-z0-9\-._~!$&'()*+,;=:@]|%[0-9a-f]{2})+(?:\/(?:[a-z0-9\-._~!$&'()*+,;=:@]|%[0-9a-f]{2})*)*)?|(?:[a-z0-9\-._~!$&'()*+,;=:@]|%[0-9a-f]{2})+(?:\/(?:[a-z0-9\-._~!$&'()*+,;=:@]|%[0-9a-f]{2})*)*)?(?:\?(?:[a-z0-9\-._~!$&'()*+,;=:@\/?]|%[0-9a-f]{2})*)?(?:\#(?:[a-z0-9\-._~!$&'()*+,;=:@\/?]|%[0-9a-f]{2})*)?$/i
	  }
	});
}

function testValid(versions, valids, v, file, ajvValidate, hello) {
	var error = ""
	// console.log(valids[0], valids[1], v);
	if (valids[0] !== valids[1] && v == 0) {
	    console.log('Ajv '+versions[v]+' Validation failed on', file);
	    var errs = ajvValidate[versions[v]].errors;
	    console.log('Ajv '+versions[v]+' Validation errors:');
	    for (var e in errs) {

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
	    }
            console.log(error, "------------------------");
		/*
	} else {
		console.log('Ajv '+versions[v]+' Valid');
		*/
	}
}

function validateFile(file) {
	try {
		var hello = JSON.parse(fs.readFileSync(file).toString());
		var version = hello["X3D"]["@version"];

		var versions = [ version, "6.0" ];
		var valids = ["NOT YOU", "NOT ME"]
		for (var v in versions) {
    			var valid = ajvValidate[versions[v]](hello);
			valids[v] = valid;
		}
		for (var v in versions) {
			testValid(versions, valids, v, file, ajvValidate, hello);
			/////////////////////////////

			/*
			var errors = jjvEnv[versions[v]].validate('x3djson', hello);

			if (!errors) {
				// console.log('jjv '+versions[v]+' Valid');
			} else {
				console.log('jjv '+versions[v]+' Validation failed on', file);
				console.log('jjv '+versions[v]+' Validation errors:');
				console.log(JSON.stringify(errors));
			}
			*/
			//////////////////////////////

			var valid2 = imjvValidate[versions[v]](hello);

			if (valid2) {
			    // console.log('is-my-json-valid '+versions[v]+' Valid');
			} else {
			    console.log('is-my-json-valid '+versions[v]+' Validation failed on', file);
			    console.log('is-my-json-valid '+versions[v]+' Validation errors:');
			    console.log(JSON.stringify(imjvValidate[versions[v]].errors, null, 1));
			}
		   }
        	   console.log("========================");
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
