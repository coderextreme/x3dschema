var fs = require('fs');
var jjv = require('jjv');

var X3dJsonSchema = {
        "$schema": "https://json-schema.org/draft/2020-12/schema",
        "title": "JSON Schema X3D V3.3",
        "description": "Development JSON Schema for X3D V3.3 ",
        "type": "object",
        "properties": {
		"Extrusion": {
			"type": "object",
			"properties": {
				"@crossSection": {
					"type": "array",
					"minItems": 4,
					"items": [
						{
							"type": "number",
							"default": 1
						},
						{
							"type": "number",
							"default": 1
						},
						{
							"type": "number",
							"default": 1
						},
						{
							"type": "number",
							"default": -1
						},
						{
							"type": "number",
							"default": -1
						},
						{
							"type": "number",
							"default": -1
						},
						{
							"type": "number",
							"default": -1
						},
						{
							"type": "number",
							"default": 1
						},
						{
							"type": "number",
							"default": 1
						}
					],
					"additionalItems": {
						"type": "number",
						"default": 1
					}
				}
			},
			"additionalProperties": false
		}
	}
};

var env = jjv();
env.addSchema('x3djson', X3dJsonSchema);

function validateJSON() {
	try {
		var hello = { "Extrusion":
			{
			  "@crossSection":[0,0,0.3,0,0.3,0.3,0,0.3]
			}
		      };

		var errors = env.validate('x3djson', hello);

		if (!errors) {
			console.log('jjv Valid');
		} else {
			console.log('jjv Validation failed.');
			console.log('jjv Validation errors:');
			console.log(JSON.stringify(errors));
		}
	} catch (e) {
		console.log('Failed JSON parse');
	}
}

validateJSON();
