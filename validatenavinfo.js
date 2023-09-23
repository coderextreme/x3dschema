var fs = require('fs');
var jjv = require('jjv');

var X3dJsonSchema = {
        "$schema": "https://json-schema.org/draft/2020-12/schema",
        "title": "JSON Schema X3D V3.3",
        "description": "Development JSON Schema for X3D V3.3 ",
        "type": "object",
        "properties": {
		 "NavigationInfo": {
                        "type": "object",
                        "properties": {
                                "@type": {
                                        "type": "array",
                                        "minItems": 1,
                                        "items": [
                                                {
                                                        "type": "string",
                                                        "default": "EXAMINE"
                                                },
                                                {
                                                        "type": "string",
                                                        "default": "ANY"
						 }
                                        ],
                                        "additionalItems": {
                                                "type": "string"
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
		var hello = {
			"NavigationInfo": {
				"@type": ["EXAMINE"]
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
}

validateJSON();
