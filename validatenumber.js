var fs = require('fs');
var jjv = require('jjv');

var X3dJsonSchema = {
    "$schema": "https://json-schema.org/draft/2020-12/schema",
    "title": "JSON Schema X3D V3.3",
    "description": "Experimental JSON Schema for X3D V3.3 ",
    "type": "object",
    "properties": {
        "PixelTexture": {
            "type": "object",
            "properties": {
                "@image": {
                    "type": "array",
                    "minItems": 3,
                    "items": [
                        {
                            "type": "number",
                            "default": 0
                        },
                        {
                            "type": "number",
                            "default": 0
                        },
                        {
                            "type": "number",
                            "default": 0
                        }
                    ],
                    "additionalItems": {
                        "type": "number"
                    }
                }
            }
        }
    }
};

var env = jjv();
env.addSchema('x3djson', X3dJsonSchema);

function validateJSON() {
	try {
		var hello = 
                            { "PixelTexture":
                              {
                                "@image":[99999999999999999999999999999999999999999999999999999999999999999999999999999999]
                              }
                            }
		;

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
