var fs = require('fs');
var Ajv = require('ajv');
var jjv = require('jjv');
var validator = require('is-my-json-valid');

var XMLSchemaJsonSchema = JSON.parse(fs.readFileSync('./schemas/XMLSchema.jsonschema').toString());
var JsonixJsonSchema = JSON.parse(fs.readFileSync('./schemas/Jsonix.jsonschema').toString());
var X3dJsonSchema = JSON.parse(fs.readFileSync('./RoysSchema.json').toString());

////////////////////////////////
var ajv = new Ajv();
ajv.addSchema(XMLSchemaJsonSchema, 'http://www.jsonix.org/jsonschemas/w3c/2001/XMLSchema.jsonschema');
ajv.addSchema(JsonixJsonSchema, 'http://www.jsonix.org/jsonschemas/jsonix/Jsonix.jsonschema');


var validate = ajv.compile(X3dJsonSchema);

var env = jjv();
env.addSchema('x3djson', X3dJsonSchema);

var validate2 = validator(X3dJsonSchema);


function validateFile(file) {
	try {
		var hello = JSON.parse(fs.readFileSync(file).toString());

		console.error('BEGIN', file);
		console.log('BEGIN', file);
		var valid = validate(hello);
		if (!valid) {
		    console.log('Ajv Validation failed.');
		    console.log('Ajv Validation errors:');
		    console.log(validate.errors);
		} else {
		    console.log('Ajv Valid');
		}
		/////////////////////////////

		var errors = env.validate('x3djson', hello);

		if (!errors) {
			console.log('jjv Valid');
		} else {
			console.log('jjv Validation failed.');
			console.log('jjv Validation errors:');
			console.log(errors);
		}
		//////////////////////////////

		var valid2 = validate2(hello);

		if (valid2) {
		    console.log('is-my-json-valid Valid');
		} else {
		    console.log('is-my-json-valid Validation failed.');
		    console.log('is-my-json-valid Validation errors:');
		    console.log(validate.errors);
		}

		console.error('END', file);
		console.log('END', file);
	} catch (e) {
		console.error('Failed JSON parse', file);
	}
}

for (i in process.argv) {
	if (i < 2) {
		continue;
	}
	var file = process.argv[i];
	validateFile(file);
}
